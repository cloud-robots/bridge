package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.core.Bridge;
import cloud.robots.bridge.client.core.BridgeBuilder;
import cloud.robots.bridge.client.core.exceptions.BridgeException;
import cloud.robots.bridge.client.core.model.Message;
import cloud.robots.bridge.client.internal.exceptions.BridgeBuilderException;

import java.util.HashMap;
import java.util.function.Consumer;

class DefaultBridgeBuilderImpl implements BridgeBuilder {

  private static final String NOT_SUBSCRIPTIONS = "not subscription to any topic provided. Use subscribe()";

  private String url = "http://localhost:8080";
  private int timeout = 25000;
  private int refresh = 15000;
  private boolean ignoreSelf = true;
  private HashMap<String, Consumer<Message>> subscriptions = new HashMap<>();

  DefaultBridgeBuilderImpl() {

  }

  private static DefaultBridgeBuilderImpl copy(final DefaultBridgeBuilderImpl other) {
    DefaultBridgeBuilderImpl newObject = new DefaultBridgeBuilderImpl();
    newObject.url = other.url;
    newObject.timeout = other.timeout;
    newObject.refresh = other.refresh;
    newObject.ignoreSelf = other.ignoreSelf;
    newObject.subscriptions = new HashMap<>(other.subscriptions);
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
    newObject.subscriptions.put(topic, callback);
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
  public BridgeBuilder ignoreSelfMessages(boolean ignoreSelf) {
    DefaultBridgeBuilderImpl newObject = copy(this);
    newObject.ignoreSelf = ignoreSelf;
    return newObject;
  }

  @Override
  public Bridge build() throws BridgeException {
    if (subscriptions.size() == 0) {
      throw new BridgeBuilderException(NOT_SUBSCRIPTIONS);
    }
    DefaultBridgeImpl bridge = new DefaultBridgeImpl(url, timeout, refresh, ignoreSelf);
    subscriptions.forEach(bridge::addSubscription);
    return bridge;
  }
}
