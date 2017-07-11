package cloud.robots.bridge.client.internal.exceptions;

import cloud.robots.bridge.client.core.exceptions.BridgeException;

public class BridgeJsonException extends BridgeException {
  public BridgeJsonException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
