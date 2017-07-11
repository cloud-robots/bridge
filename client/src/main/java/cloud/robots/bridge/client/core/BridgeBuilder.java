package cloud.robots.bridge.client.core;

import cloud.robots.bridge.client.core.exceptions.BridgeException;
import cloud.robots.bridge.client.core.model.Message;

import java.util.function.Consumer;

/**
 * A generic builder for creating a Bridge
 *
 * @since 0.1.0-SNAPSHOT
 * @see cloud.robots.bridge.client.core.Bridge
 */
public interface BridgeBuilder {

  /**
   * Specify to which url the bridge will connect
   * @apiNote If not set the bridge will connect to http://localhost:8080
   * @param serverUrl url to the sever
   * @return a new instance of the builder
   */
  BridgeBuilder connect(String serverUrl);

  /**
   * Set a subscription to a topic
   * @param topic the desired topic to be subscribed
   * @apiNote We must always subscribe to at least one topic
   * @param callback callback function to be invoke with each message
   *                 on this topic, could be a lambda
   * @return a new instance of the builder
   */
  BridgeBuilder subscribe(String topic, Consumer<Message> callback);

  /**
   * Specify socket and connection timeouts
   * @apiNote By default this is 25000ms
   * @param milliseconds milliseconds to timeout
   * @return a new instance of the builder
   */
  BridgeBuilder timeout(final int milliseconds);

  /**
   * Specify the refresh intervals for fetching messages
   * @apiNote By default his is 15000ms
   * @param milliseconds interval in milliseconds
   * @return a new instance of the builder
   */
  BridgeBuilder refresh(final int  milliseconds);

  /**
   * Set to ignore our own messages to topics.
   * @apiNote By default {@code false}
   * @param ignoreSelf true if we ignore our messages
   * @return a new instance of the builder
   */
  BridgeBuilder ignoreSelfMessages(boolean ignoreSelf);

  /**
   * Create the bridge with the desired configuration
   * @return a new instance of the bridge
   * @throws BridgeException if any error occurs
   * @see cloud.robots.bridge.client.core.Bridge
   */
  Bridge build() throws BridgeException;
}
