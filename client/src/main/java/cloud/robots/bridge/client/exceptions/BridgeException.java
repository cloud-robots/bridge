package cloud.robots.bridge.client.exceptions;

public class BridgeException extends Exception {
  BridgeException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
