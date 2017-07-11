package cloud.robots.bridge.client.internal.exceptions;

import cloud.robots.bridge.client.core.exceptions.BridgeException;

public class BridgeHttpException extends BridgeException {
  public BridgeHttpException(String message, Throwable throwable) {
    super(message, throwable);
  }
  public BridgeHttpException(String message) {
    super(message, null);
  }
}
