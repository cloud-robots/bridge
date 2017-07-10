package cloud.robots.bridge.client;

import cloud.robots.bridge.client.exceptions.BridgeException;

public interface BridgeBuilder {
  BridgeBuilder connect(String serverUrl);
  BridgeBuilder subscribe(String topic);
  BridgeBuilder timeout(int milliseconds);
  Bridge build() throws BridgeException;
}
