package cloud.robots.bridge.client.internal.exceptions;

import cloud.robots.bridge.client.core.exceptions.BridgeException;

public class BridgeStateException extends BridgeException {
  public BridgeStateException(String message) {
    super(message, null);
  }
}
