package com.example.graal.restserver.filters;

import com.example.graal.restserver.config.GithubProperties;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import org.reactivestreams.Publisher;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Filter(value = {"/users/**", "/repos/**"})
public class GithubAppTokenFilter implements HttpClientFilter {

  private final GithubProperties githubProperties;

  public GithubAppTokenFilter(@Valid GithubProperties githubProperties) {
    this.githubProperties = githubProperties;
  }

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
    String token = this.githubProperties.getToken();
    if (StringUtils.hasText(token)) {
      byte[] basicAuthValue = token.getBytes(StandardCharsets.UTF_8);
      request.getHeaders().set(HttpHeaders.AUTHORIZATION,
                               "Basic " + Base64.getEncoder().encodeToString(basicAuthValue));
    }
    return chain.proceed(request);
  }
}
