package cloud.robots.bridge.client;

import cloud.robots.bridge.client.exceptions.BridgeException;
import cloud.robots.bridge.client.internal.BasicRestClient;
import cloud.robots.bridge.client.model.SubscribeRequest;
import cloud.robots.bridge.client.model.SubscribeResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;

import java.util.List;

public class Bridge extends BasicRestClient {

  private static final String SUBSCRIBE_PATH = "/subscribe/";

  private String subscriber;

  Bridge(String baseUrl) {
    super(baseUrl);
  }

  public String getSubscriber() {
    return subscriber;
  }

  void subscribe(List<String> topics) throws BridgeException {
    SubscribeRequest subscribeRequest = new SubscribeRequest();
    subscribeRequest.setTopics(topics);

    SubscribeResponse response = request(Request::Put, SUBSCRIBE_PATH, subscribeRequest,
        SubscribeResponse.class, HttpStatus.SC_CREATED);

    subscriber = response.getSubscriber();
  }

}
