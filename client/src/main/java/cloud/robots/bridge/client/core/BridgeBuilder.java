package cloud.robots.bridge.client.core;

import cloud.robots.bridge.client.core.exceptions.BridgeException;
import cloud.robots.bridge.client.core.model.Message;

import java.util.function.Consumer;

public interface BridgeBuilder {
  BridgeBuilder connect(String serverUrl);
  BridgeBuilder subscribe(String topic, Consumer<Message> callback);
  BridgeBuilder timeout(final int milliseconds);
  BridgeBuilder refresh(final int  milliseconds);
  Bridge build() throws BridgeException;
}
