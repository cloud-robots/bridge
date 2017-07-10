package cloud.robots.bridge.client;

import cloud.robots.bridge.client.internal.DefaultBridgeBuilderImpl;

public class BridgeBuilderFactory {
  public static BridgeBuilder Default(){
    return new DefaultBridgeBuilderImpl();
  }
}
