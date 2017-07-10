package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.Bridge;
import cloud.robots.bridge.client.BridgeBuilder;
import cloud.robots.bridge.client.exceptions.BridgeException;

import java.util.ArrayList;
import java.util.List;

class DefaultBridgeBuilderImpl implements BridgeBuilder {

  private String url = "http://localhost:8080";
  private int timeout = 25000;
  private List<String> topics = new ArrayList<>();

  DefaultBridgeBuilderImpl() {

  }

  private static DefaultBridgeBuilderImpl copy(final DefaultBridgeBuilderImpl other) {
    DefaultBridgeBuilderImpl newObject = new DefaultBridgeBuilderImpl();
    newObject.url = other.url;
    newObject.timeout = other.timeout;
    newObject.topics = new ArrayList<>(other.topics);
    return newObject;
  }

  @Override
  public BridgeBuilder connect(final String serverUrl) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.url = serverUrl;
    return newObject;
  }

  @Override
  public BridgeBuilder subscribe(final String topic) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.topics.add(topic);
    return newObject;
  }

  @Override
  public BridgeBuilder timeout(final int milliseconds) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.timeout = milliseconds;
    return newObject;
  }

  @Override
  public Bridge build() throws BridgeException {
    Bridge bridge = new DefaultBridgeImpl(url, timeout);
    bridge.subscribe(topics);
    return bridge;
  }
}
