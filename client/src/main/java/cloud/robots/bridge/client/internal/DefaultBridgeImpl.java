package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.Bridge;
import cloud.robots.bridge.client.exceptions.BridgeException;
import cloud.robots.bridge.client.model.SubscribeRequest;
import cloud.robots.bridge.client.model.SubscribeResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;

import java.util.List;

class DefaultBridgeImpl extends BasicRestClient implements Bridge {

  private static final String SUBSCRIBE_PATH = "/subscribe/";

  private String subscriber;

  DefaultBridgeImpl(final String baseUrl, final int timeout) {
    super(baseUrl, timeout);
  }

  @Override
  public String getSubscriber() {
    return subscriber;
  }

  @Override
  public void subscribe(final List<String> topics) throws BridgeException {
    SubscribeRequest request = new SubscribeRequest(topics);
    SubscribeResponse response = request(Request::Put, SUBSCRIBE_PATH, request,
        SubscribeResponse.class, HttpStatus.SC_CREATED);
    subscriber = response.getSubscriber();
  }
}
