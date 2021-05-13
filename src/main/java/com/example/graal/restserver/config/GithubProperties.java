package com.example.graal.restserver.config;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.validation.constraints.Pattern;

@ConfigurationProperties("github")
public class GithubProperties {

  /**
   * Github API token ("user:token")
   */
  @Pattern(regexp = "\\w+:\\w+")
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
