package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

public class HTTPJsonRequestClient {
  public HTTPJsonRequestClient() {

  }

  public <T> Optional<T> send(HttpRequest httpRequest, Class<T> type) {
    try (HttpClient client = HttpClient.newHttpClient()) {
      var bodyHandler = (BodyHandler<T>) info -> BodySubscribers.mapping(
              BodySubscribers.ofString(StandardCharsets.UTF_8),
              mapJsonResponseToType(type));
      var response = client.send(httpRequest, bodyHandler);
      return Optional.ofNullable(response.body());
    } catch (Exception exception) {
      System.err.println(exception.getMessage());
    }
    return Optional.empty();
  }

  private <T> Function<String, T> mapJsonResponseToType(Class<T> type) {
    return (String string) -> {
      try {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(string, type);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    };
  }

}
