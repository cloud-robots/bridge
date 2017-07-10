package cloud.robots.bridge.client;

import cloud.robots.bridge.client.exceptions.BridgeException;

import java.util.ArrayList;
import java.util.List;

public class BridgeBuilder {

  private String url;

  public static BridgeBuilder DefaultBridgeBuilder(){
    return new BridgeBuilder();
  }

  public BridgeBuilder connect(final String url){
    this.url = url;
    return this;
  }

  private List<String> topics = new ArrayList<String>();

  public BridgeBuilder subscribe(final String topic){
    topics.add(topic);
    return this;
  }

  public Bridge build() throws BridgeException {

    Bridge bridge = new Bridge(this.url);
    bridge.subscribe(topics);

    return bridge;
  }
}
