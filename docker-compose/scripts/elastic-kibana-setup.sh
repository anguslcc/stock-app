#!/bin/bash

# Check for required environment variables
if [ -z "${ELASTIC_PASSWORD}" ]; then
  echo "Set the ELASTIC_PASSWORD environment variable in the .env file"
  exit 1
elif [ -z "${KIBANA_PASSWORD}" ]; then
  echo "Set the KIBANA_PASSWORD environment variable in the .env file"
  exit 1
fi

# Create CA if not exists
if [ ! -f config/certs/ca.zip ]; then
  echo "Creating CA"
  bin/elasticsearch-certutil ca --silent --pem -out config/certs/ca.zip
  unzip config/certs/ca.zip -d config/certs
fi

# Create certs if not exists
if [ ! -f config/certs/certs.zip ]; then
  echo "Creating certs"
  cat <<EOF > config/certs/instances.yml
instances:
  - name: es01
    dns:
      - es01
      - localhost
    ip:
      - 127.0.0.1
  - name: kibana
    dns:
      - kibana
      - localhost
    ip:
      - 127.0.0.1
EOF

  bin/elasticsearch-certutil cert --silent --pem -out config/certs/certs.zip --in config/certs/instances.yml --ca-cert config/certs/ca/ca.crt --ca-key config/certs/ca/ca.key
  unzip config/certs/certs.zip -d config/certs
fi

# Set file permissions
echo "Setting file permissions"
chown -R root:root config/certs
find config/certs -type d -exec chmod 750 {} \;
find config/certs -type f -exec chmod 640 {} \;

# Wait for Elasticsearch to start
echo "Waiting for Elasticsearch availability"
until curl -s --cacert config/certs/ca/ca.crt https://es01:9200 | grep -q "missing authentication credentials"; do
  sleep 30
done

# Set kibana_system user password
echo "Setting kibana_system password"
until curl -s -X POST --cacert config/certs/ca/ca.crt -u "elastic:${ELASTIC_PASSWORD}" -H "Content-Type: application/json" https://es01:9200/_security/user/kibana_system/_password -d "{\"password\":\"${KIBANA_PASSWORD}\"}" | grep -q "^{}"; do
  sleep 10
done

echo "All done!"
