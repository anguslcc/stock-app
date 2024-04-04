package org.finance.config.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api-key-config")
public class ApiKeyConfigData {

  private String twelve;

  public String getTwelve() {
    return twelve;
  }

  public void setTwelve(String twelve) {
    this.twelve = twelve;
  }

  @Override
  public String toString() {
    return "ApiKeyConfigData{" +
        "twelve='" + twelve + '\'' +
        '}';
  }
}
