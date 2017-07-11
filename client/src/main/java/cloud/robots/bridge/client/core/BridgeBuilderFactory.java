package cloud.robots.bridge.client.core;

import cloud.robots.bridge.client.internal.DefaultBridgeBuilderFactory;

/**
 * Factory to create bridge builders
 * @since 0.1.0-SNAPSHOT
 * @see cloud.robots.bridge.client.core.BridgeBuilder
 */
public class BridgeBuilderFactory extends DefaultBridgeBuilderFactory {

  /**
   * Creaste a new instance of a bridge builder
   * @return a new instance of the builder
   * @see cloud.robots.bridge.client.core.BridgeBuilder
   */
  public static BridgeBuilder Default(){
    return DefaultBridgeBuilderFactory.Default();
  }
}
