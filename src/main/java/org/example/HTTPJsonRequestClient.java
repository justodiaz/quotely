package org.example;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                .build()
                .readValue(string, type);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    };
  }

}
