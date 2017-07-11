package cloud.robots.bridge.client.core;

import cloud.robots.bridge.client.internal.DefaultBridgeBuilderFactory;

public class BridgeBuilderFactory extends DefaultBridgeBuilderFactory {
  public static BridgeBuilder Default(){
    return DefaultBridgeBuilderFactory.Default();
  }
}
