package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.BridgeBuilder;

public class BridgeBuilderFactory {
  public static BridgeBuilder Default(){
    return new DefaultBridgeBuilderImpl();
  }
}
