package cloud.robots.bridge.client.exceptions;


public class BridgeException extends Exception {
  public BridgeException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public BridgeException(String message) {
    super(message, null);
  }
}
