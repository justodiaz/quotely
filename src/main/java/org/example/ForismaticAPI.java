package org.example;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Optional;

public class ForismaticAPI {
  private final static URI URI;
  private final static String REQUEST_BODY_TEMPLATE = "method=getQuote&format=json&lang=%s";
  private final static String HEADER_CONTENT_TYPE = "content-type";
  private final static String HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

  static {
    try {
      URI = new URI("http://forismatic.com/api/1.0/");
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private final HTTPJsonRequestClient httpJsonRequestClient;

  public ForismaticAPI(HTTPJsonRequestClient httpJsonRequestClient) {
    this.httpJsonRequestClient = httpJsonRequestClient;
  }

  public Optional<Quote> getQuote(Language language) {
    if (language == Language.UNKNOWN) {
      return Optional.empty();
    }
    var httpRequest = HttpRequest.newBuilder(URI)
            .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
            .POST(HttpRequest.BodyPublishers.ofString(REQUEST_BODY_TEMPLATE.formatted(language.getCode())))
            .build();
    return httpJsonRequestClient.send(httpRequest, Quote.class);
  }
}

