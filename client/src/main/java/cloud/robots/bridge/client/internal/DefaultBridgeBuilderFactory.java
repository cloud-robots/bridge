package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.core.BridgeBuilder;

public abstract class DefaultBridgeBuilderFactory {
  protected static BridgeBuilder Default() {
    return new DefaultBridgeBuilderImpl();
  }
}
