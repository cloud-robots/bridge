package cloud.robots.bridge.client.core.model;

/**
 * Class that represent a topic in a message
 *
 * @since 0.1.0-SNAPSHOT
 */
@SuppressWarnings("unused")
public class Message {

  private String subscriber;
  private String text;
  private String topic;
  private String time;

  /**
   * @return the subscriber id from whom send this message
   */
  public String getSubscriber() {
    return subscriber;
  }

  /**
   * @param subscriber the subscriber id who send the message
   */
  public void setSubscriber(String subscriber) {
    this.subscriber = subscriber;
  }

  /**
   * @return get the text for this message
   */
  public String getText() {
    return text;
  }

  /**
   * @param text set the text for this message
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return the topic where this message was sent
   */
  public String getTopic() {
    return topic;
  }

  /**
   * @param topic set the message topic
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * @return the time when the message was received in UTC format
   */
  public String getTime() {
    return time;
  }

  /**
   * @param time set the time for this message
   */
  public void setTime(String time) {
    this.time = time;
  }
}
