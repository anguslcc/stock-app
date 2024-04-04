package org.finance.config.twelve;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twelve-config")
public class TwelveConfigData {

  private String url;
  private List<String> symbols;
  private String interval;
  private int outputSize;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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
        "url='" + url + '\'' +
        ", symbols=" + symbols +
        ", interval='" + interval + '\'' +
        ", outputSize=" + outputSize +
        '}';
  }
}
