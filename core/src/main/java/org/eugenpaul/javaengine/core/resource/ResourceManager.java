package org.eugenpaul.javaengine.core.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Manager of AResources.
 * 
 * @author Eugen Paul
 *
 */
public class ResourceManager<T extends IResource> {

  private final Map<String, T> container;
  private final Map<String, T> enabledElements;

  /**
   * C'tor
   */
  public ResourceManager() {
    container = new HashMap<>();
    enabledElements = new HashMap<>();
  }

  /**
   * Add a new resource to Manager if new.
   * 
   * @param key      - key of resource
   * @param resource - resource
   * @return true - OK<br>
   *         false - the key is not new.
   */
  public boolean add(String key, T resource) {
    if (null != container.get(key)) {
      return false;
    }

    container.put(key, resource);
    return true;
  }

  /**
   * remove key from Manager
   * 
   * @param key - key of resource
   * @return resource - deleted resource<br>
   *         null - key is not in Manager
   */
  public T remove(String key) {
    return container.remove(key);
  }

  /**
   * Get resource
   * 
   * @param key - key of the resource
   * @return resource or null
   */
  public T getResource(String key) {
    return container.get(key);
  }

  /**
   * Enable (load and show) resource
   * 
   * @param key
   * @return
   */
  public boolean enable(String key) {
    T resource = container.get(key);
    if (null == resource) {
      return false;
    }

    resource.load();

    resource.firstShow();

    enabledElements.put(key, resource);

    return true;
  }

  /**
   * Disable (unload) resource
   * 
   * @param key
   * @return
   */
  public boolean disable(String key) {
    T resource = container.get(key);
    if (null == resource) {
      return false;
    }

    resource.unload();

    enabledElements.remove(key);

    return true;
  }

  public void updateEnabled(float tpf) {
    for (Entry<String, T> elem : enabledElements.entrySet()) {
      elem.getValue().update(tpf);
    }
  }

}
