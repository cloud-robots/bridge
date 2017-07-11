package cloud.robots.bridge.client.core;

import cloud.robots.bridge.client.core.exceptions.BridgeException;
import cloud.robots.bridge.client.internal.exceptions.BridgeStateException;

public interface Bridge {
  String getSubscriber();
  void start() throws BridgeException;
  void stop() throws BridgeStateException;
  String send(final String topic, final String text) throws BridgeException;
}
