package cloud.robots.bridge.client;

import cloud.robots.bridge.client.exceptions.BridgeException;

import java.util.List;

public interface Bridge {
  void subscribe(final List<String> topics) throws BridgeException;
  String getSubscriber();
}
