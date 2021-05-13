package com.example.graal.restserver.clients;

import com.example.graal.restserver.dto.User;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class GithubClient {

  private final RxHttpClient httpClient;

  public static final String GITHUB_API_URL = "https://api.github.com";
  public static final String USERS_URI = "/users/{userName}";
  public static final String CONTRIBUTORS_URI = "/repos/{owner}/{repo}/contributors";

  public GithubClient(@Client(GITHUB_API_URL) RxHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public User fetchUser(String userName) {
    String uri = UriBuilder.of(USERS_URI)
                           .expand(Collections.singletonMap("userName", userName))
                           .toString();
    HttpRequest<?> request = HttpRequest.GET(uri)
                                        .header(HttpHeaders.USER_AGENT, "Micronaut HTTP Client");
    return this.httpClient.toBlocking().retrieve(request, User.class);
  }

  public User[] fetchContributors(String orgName, String repoName) {
    Map<String, Object> paramsMap = new HashMap<>();
    paramsMap.put("owner", orgName);
    paramsMap.put("repo", repoName);
    String uri = UriBuilder.of(CONTRIBUTORS_URI)
                           .expand(paramsMap)
                           .toString();
    HttpRequest<?> request = HttpRequest.GET(uri)
                                        .header(HttpHeaders.USER_AGENT, "Micronaut HTTP Client");
    return this.httpClient.toBlocking().retrieve(request, User[].class);
  }
}
