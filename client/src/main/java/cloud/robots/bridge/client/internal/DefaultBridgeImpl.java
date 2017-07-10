package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.Bridge;
import cloud.robots.bridge.client.exceptions.BridgeException;
import cloud.robots.bridge.client.model.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

class DefaultBridgeImpl extends BasicRestClient implements Bridge {

  private static final String SUBSCRIBE_PATH = "/subscribe/";
  private static final String MESSAGES_PATH = "/messages/";
  private static final String PUBLISH_PATH = "/publish/";

  private String subscriber;
  private final ScheduledExecutorService executor;
  private HashMap<String, Consumer<Message>> hooks = new HashMap<>();

  DefaultBridgeImpl(final String baseUrl, final int timeout) {
    super(baseUrl, timeout);
    executor = Executors.newScheduledThreadPool(1);
  }

  private void processMessage(final Message message) {
    if (hooks.containsKey(message.getTopic())) {
      hooks.get(message.getTopic()).accept(message);
    }
  }

  private void onRefresh() {
    try {
      MessagesResponse response = request(Request::Get, MESSAGES_PATH + subscriber, MessagesResponse.class,
          HttpStatus.SC_OK);

      response.getMessages().forEach(this::processMessage);

    } catch (BridgeException e) {
      e.printStackTrace();
    }
  }

  void autoRefresh(final int milliseconds) {
    executor.scheduleAtFixedRate(this::onRefresh, 0, milliseconds, TimeUnit.MILLISECONDS);
  }

  void addHook(final String topic, Consumer<Message> hook) {
    hooks.put(topic, hook);
  }

  @Override
  public String getSubscriber() {
    return subscriber;
  }

  @Override
  public void stop() {
    executor.shutdown();
  }

  @Override
  public void send(final String topic, final String text) throws BridgeException {
    PublishRequest request = new PublishRequest();
    request.setText(text);
    request.setSubscriber(subscriber);
    PublishResponse response = request(Request::Put, PUBLISH_PATH + topic, request, PublishResponse.class,
        HttpStatus.SC_OK);
  }

  @Override
  public void subscribe(final List<String> topics) throws BridgeException {
    SubscribeRequest request = new SubscribeRequest(topics);
    SubscribeResponse response = request(Request::Put, SUBSCRIBE_PATH, request,
        SubscribeResponse.class, HttpStatus.SC_CREATED);
    subscriber = response.getSubscriber();
  }
}
