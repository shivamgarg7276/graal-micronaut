package com.example.graal.restserver.filters;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Filter
public class RateLimitHeaderFilter implements HttpClientFilter {

  Logger log = LoggerFactory.getLogger(RateLimitHeaderFilter.class);

  @Override
  public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
    return Flowable.fromPublisher(chain.proceed(request)).flatMap(response -> {
      final String remainingRateLimit = response.getHeaders().get("X-RateLimit-Remaining");
      assert remainingRateLimit != null;
      if (Integer.parseInt(remainingRateLimit) > 0) {
        log.info("Remaining Rate Limit: - {}", remainingRateLimit);
      }
      else {
        log.warn("API Rate limit has exceeded");
      }
      return Flowable.just(response);
    });
  }
}
