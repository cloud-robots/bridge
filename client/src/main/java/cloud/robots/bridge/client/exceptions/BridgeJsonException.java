package cloud.robots.bridge.client.exceptions;

public class BridgeJsonException extends BridgeException {
  public BridgeJsonException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public BridgeJsonException(String message) {
    super(message, null);
  }
}
