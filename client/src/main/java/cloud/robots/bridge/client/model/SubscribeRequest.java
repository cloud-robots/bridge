package cloud.robots.bridge.client.model;

import java.util.ArrayList;
import java.util.List;

public class SubscribeRequest {
  private List<String> topics = new ArrayList<String>();

  public List<String> getTopics() {
    return topics;
  }

  public void setTopics(List<String> topics) {
    this.topics = topics;
  }
}
