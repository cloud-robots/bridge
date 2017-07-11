package cloud.robots.bridge.client.core;

import cloud.robots.bridge.client.core.exceptions.BridgeException;

/**
 * A generic Interface that represent a bridge client
 *
 * @since 0.1.0-SNAPSHOT
 * @see cloud.robots.bridge.client.core.BridgeBuilder
 */
public interface Bridge {

  /**
   * Get the current subscriber id
   * @return the current subscriber id
   */
  String getSubscriber();

  /**
   * Start the bridge
   * @throws BridgeException if any error occurs
   */
  void start() throws BridgeException;

  /**
   * Stop the bridge
   * @throws BridgeException if any error occurs
   */
  void stop() throws BridgeException;

  /**
   * Send a message to a topic
   * @param topic topic to send the message
   * @param text text of the message
   * @return id of the message
   * @throws BridgeException if any error occurs
   */
  String send(final String topic, final String text) throws BridgeException;
}
