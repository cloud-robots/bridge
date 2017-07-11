package cloud.robots.bridge.client.core.exceptions;

/**
 * Abstract class that represent a error using the bridge
 *
 * @apiNote this class mean only to be inherited
 * @since 0.1.0-SNAPSHOT
 */
abstract public class BridgeException extends Exception {

  /**
   * Create a new bridge exception
   * @param message message of the exception
   * @param cause cause of this exception. Could be {@code null}
   */
  protected BridgeException(String message, Throwable cause) {
    super(message, cause);
  }
}
