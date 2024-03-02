package org.example;

public enum Language {
  EN("English", "en"), RU("Russian", "ru"), UNKNOWN("Unknown", "");

  private final String fullName;
  private final String code;

  Language(String fullName, String code) {
    this.fullName = fullName;
    this.code = code;
  }

  public static Language of(String language) {
    if ("English".equals(language)) {
      return EN;
    }
    if ("Russian".equals(language)) {
      return RU;
    }
    return UNKNOWN;
  }

  public String getFullName() {
    return fullName;
  }

  public String getCode() {
    return code;
  }
}
