package cloud.robots.bridge.client.model;

import java.util.List;

public class MessagesResponse {
  private String subscriber;
  private List<Message> messages;

  public String getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(String subscriber) {
    this.subscriber = subscriber;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }
}
