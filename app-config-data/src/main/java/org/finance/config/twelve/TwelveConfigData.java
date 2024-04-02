package org.finance.config.twelve;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twelve-config")
public class TwelveConfigData {

  private String apiKey;
  private String url;
  private List<String> symbols;
  private String interval;
  private int outputSize;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

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
        "apiKey='" + apiKey + '\'' +
        ", url='" + url + '\'' +
        ", symbols='" + symbols + '\'' +
        ", interval='" + interval + '\'' +
        ", outputSize=" + outputSize +
        '}';
  }
}
