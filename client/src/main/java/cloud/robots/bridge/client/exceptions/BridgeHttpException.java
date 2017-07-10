package cloud.robots.bridge.client.exceptions;

public class BridgeHttpException extends BridgeException {
  public BridgeHttpException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public BridgeHttpException(String message) {
    super(message, null);
  }
}
