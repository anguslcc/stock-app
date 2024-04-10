package org.finance.config.twelve;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twelve-config")
public class TwelveConfigData {

  private String scheme;
  private String host;
  private String path;
  private List<String> symbols;
  private String interval;
  private int outputSize;

  public String getScheme() {
    return scheme;
  }

  public void setScheme(String scheme) {
    this.scheme = scheme;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public String getInterval() {
    return interval;
  }

  public void setInterval(String interval) {
    this.interval = interval;
  }

  public int getOutputSize() {
    return outputSize;
  }

  public void setOutputSize(int outputSize) {
    this.outputSize = outputSize;
  }

  @Override
  public String toString() {
    return "TwelveConfigData{" +
        "scheme='" + scheme + '\'' +
        ", host='" + host + '\'' +
        ", path='" + path + '\'' +
        ", symbols=" + symbols +
        ", interval='" + interval + '\'' +
        ", outputSize=" + outputSize +
        '}';
  }
}
