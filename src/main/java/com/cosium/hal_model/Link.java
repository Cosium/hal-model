package com.cosium.hal_model;

import static java.util.Objects.requireNonNull;

import com.damnhandy.uri.template.UriTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Réda Housni Alaoui
 */
public class Link {

  private final String href;

  public Link(@JsonProperty("href") String href) {
    this.href = requireNonNull(href);
  }

  public UriTemplate href() {
    return UriTemplate.fromTemplate(href);
  }
}
