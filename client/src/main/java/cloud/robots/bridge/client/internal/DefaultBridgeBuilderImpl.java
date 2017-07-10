package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.Bridge;
import cloud.robots.bridge.client.BridgeBuilder;
import cloud.robots.bridge.client.exceptions.BridgeException;
import cloud.robots.bridge.client.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

class DefaultBridgeBuilderImpl implements BridgeBuilder {

  private String url = "http://localhost:8080";
  private int timeout = 25000;
  private int refresh = 15000;
  private List<String> topics = new ArrayList<>();
  private HashMap<String, Consumer<Message>> hooks = new HashMap<>();

  DefaultBridgeBuilderImpl() {

  }

  private static DefaultBridgeBuilderImpl copy(final DefaultBridgeBuilderImpl other) {
    DefaultBridgeBuilderImpl newObject = new DefaultBridgeBuilderImpl();
    newObject.url = other.url;
    newObject.timeout = other.timeout;
    newObject.refresh = other.refresh;
    newObject.topics = new ArrayList<>(other.topics);
    newObject.hooks = new HashMap<>(other.hooks);
    return newObject;
  }

  @Override
  public BridgeBuilder connect(final String serverUrl) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.url = serverUrl;
    return newObject;
  }

  @Override
  public BridgeBuilder subscribe(final String topic, Consumer<Message> callback) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.topics.add(topic);
    newObject.hooks.put(topic, callback);
    return newObject;
  }

  @Override
  public BridgeBuilder timeout(final int milliseconds) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.timeout = milliseconds;
    return newObject;
  }

  @Override
  public BridgeBuilder refresh(final int milliseconds) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.refresh = milliseconds;
    return newObject;
  }

  @Override
  public Bridge build() throws BridgeException {
    DefaultBridgeImpl bridge = new DefaultBridgeImpl(url, timeout);
    bridge.subscribe(topics);
    hooks.forEach(bridge::addHook);
    bridge.autoRefresh(refresh);
    return bridge;
  }
}
