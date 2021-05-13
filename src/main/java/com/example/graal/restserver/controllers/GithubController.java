package com.example.graal.restserver.controllers;

import com.example.graal.restserver.clients.GithubClient;
import com.example.graal.restserver.dto.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class GithubController {

  private final GithubClient githubClient;

  public GithubController(GithubClient githubClient) {
    this.githubClient = githubClient;
  }

  @Get("/users/{userName}")
  public HttpResponse<User> getUser(String userName) {
    User user = this.githubClient.fetchUser(userName);
    return HttpResponse.ok(user);
  }

  @Get("/contributors/{orgName}/{projectName}")
  public HttpResponse<User[]> getProjectContributors(String orgName,
                                                     String projectName) {
    User[] contributors = this.githubClient.fetchContributors(orgName, projectName);
    return HttpResponse.ok(contributors);
  }
}
