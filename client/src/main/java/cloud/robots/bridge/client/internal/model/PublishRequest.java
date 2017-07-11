package cloud.robots.bridge.client.internal.model;

@SuppressWarnings("unused")
public class PublishRequest {
  private String subscriber;
  private String text;

  public String getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(String subscriber) {
    this.subscriber = subscriber;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
