package org.example;

public class Quotely {
  private static final ForismaticAPI forismaticAPI = new ForismaticAPI(new HTTPJsonRequestClient());

  public static void main(String[] args) {
    var language = Language.UNKNOWN;
    if (args.length > 0) {
      language = Language.of(args[0]);
    }
    if (language == Language.UNKNOWN) {
      language = Language.EN;
    }
    forismaticAPI.getQuote(language).ifPresent(quote ->
            System.out.printf("\"%s\" -%s%n", quote.quoteText(), quote.quoteAuthor()));
  }
}
