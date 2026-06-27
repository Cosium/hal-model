package com.cosium.hal_model;

import com.damnhandy.uri.template.UriTemplate;
import java.util.Map;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * @author Réda Housni Alaoui
 */
public class HalFormsBody<T> {
  private final T representation;
  private final Map<String, Link> linkByName;
  private final @Nullable String selfUri;
  private final Map<String, TemplateRepresentation> templateByKey;

  public HalFormsBody(
      T representation,
      @Nullable Map<String, Link> linkByName,
      @Nullable Map<String, TemplateRepresentation> templateByKey) {
    this.representation = representation;
    this.linkByName = Optional.ofNullable(linkByName).map(Map::copyOf).orElseGet(Map::of);
    selfUri =
        Optional.ofNullable(linkByName)
            .map(linkByNameMap -> linkByNameMap.get("self"))
            .map(Link::href)
            .map(UriTemplate::expand)
            .orElse(null);

    this.templateByKey = Optional.ofNullable(templateByKey).map(Map::copyOf).orElseGet(Map::of);
  }

  public T representation() {
    return representation;
  }

  public Optional<String> selfUri() {
    return Optional.ofNullable(selfUri);
  }

  public String requireSelfUri() {
    return selfUri().orElseThrow();
  }

  public Optional<Link> findLink(String name) {
    return Optional.ofNullable(linkByName.get(name));
  }

  public Link requireLink(String name) {
    return findLink(name).orElseThrow();
  }

  public Optional<TemplateRepresentation> findTemplate(String key) {
    return Optional.ofNullable(templateByKey.get(key));
  }

  public TemplateRepresentation requireTemplate(String key) {
    return findTemplate(key).orElseThrow();
  }
}
