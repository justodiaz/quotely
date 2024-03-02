package org.example;

import java.net.http.HttpRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForismaticAPITest {
  private ForismaticAPI forismaticAPI;

  public ForismaticAPITest() {
    var mockRequester = new HTTPJsonRequestClient() {
      @Override
      public <T> Optional<T> send(HttpRequest httpRequest, Class<T> type) {
        if (type.equals(Quote.class)) {
          return Optional.<T>of((T) new Quote("Java 21 is great", "Java Beans"));
        }
        return Optional.empty();
      }
    };
    forismaticAPI = new ForismaticAPI(mockRequester);
  }
  @BeforeEach
  void setUp() {
  }

  @Test
  void getQuote() {
    var actual = forismaticAPI.getQuote(Language.EN);
    assertTrue(actual.isPresent());
    assertEquals("Java 21 is great", actual.get().quoteText());
    assertEquals("Java Beans", actual.get().quoteAuthor());
  }
}
