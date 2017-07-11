package cloud.robots.bridge.client.internal;

import cloud.robots.bridge.client.core.Bridge;
import cloud.robots.bridge.client.core.exceptions.BridgeException;
import cloud.robots.bridge.client.core.model.Message;
import cloud.robots.bridge.client.internal.exceptions.BridgeStateException;
import cloud.robots.bridge.client.internal.model.*;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

class DefaultBridgeImpl extends DefaultRestClient implements Bridge {

  private static final String SUBSCRIBE_PATH = "/subscribe/";
  private static final String MESSAGES_PATH = "/messages/";
  private static final String PUBLISH_PATH = "/publish/";
  private static final String BRIDGE_ALREADY_STARTED = "bridge already started";
  private static final String BRIDGE_NOT_STARTED = "bridge not started";
  private static final String NOT_VALID_SUBSCRIBER = "not valid subscriber";
  private static final String NO_SUBSCRIBER = "";

  private String subscriber = NO_SUBSCRIBER;
  private HashMap<String, Consumer<Message>> topicsSubscriptions = new HashMap<>();
  private AtomicBoolean started = new AtomicBoolean(false);

  private final ScheduledExecutorService executor;
  private final int refresh;
  private final boolean ignoreSelf;

  DefaultBridgeImpl(final String baseUrl, final int timeout, final int refresh, final boolean ignoreSelf) {
    super(baseUrl, timeout);
    this.refresh = refresh;
    this.ignoreSelf = ignoreSelf;
    executor = Executors.newScheduledThreadPool(1);
  }

  boolean shouldSkipMessage(final Message message){
    if(ignoreSelf){
      if(message.getSubscriber().equals(subscriber)){
        return true;
      }
    }
    return false;
  }
  private void processMessage(final Message message) {
    if(shouldSkipMessage(message)){
      return;
    }
    if (topicsSubscriptions.containsKey(message.getTopic())) {
      topicsSubscriptions.get(message.getTopic()).accept(message);
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

  void addSubscription(final String topic, Consumer<Message> callback) {
    topicsSubscriptions.put(topic, callback);
  }

  @Override
  public String getSubscriber() {
    return subscriber;
  }

  String subscribe(final List<String> topics) throws BridgeException {
    SubscribeRequest request = new SubscribeRequest(topics);
    SubscribeResponse response = request(Request::Put, SUBSCRIBE_PATH, request,
        SubscribeResponse.class, HttpStatus.SC_CREATED);
    return response.getSubscriber();
  }

  @Override
  public void start() throws BridgeException {
    if (started.get()) {
      throw new BridgeStateException(BRIDGE_ALREADY_STARTED);
    } else {
      subscriber = subscribe(new ArrayList<>(topicsSubscriptions.keySet()));
      executor.scheduleAtFixedRate(this::onRefresh, 0, refresh, TimeUnit.MILLISECONDS);
      started.set(true);
    }
  }

  @Override
  public void stop() throws BridgeStateException {
    if (!started.get()) {
      throw new BridgeStateException(BRIDGE_NOT_STARTED);
    }
    executor.shutdown();
    started.set(false);
    subscriber = NO_SUBSCRIBER;
  }

  @Override
  public String send(final String topic, final String text) throws BridgeException {
    if (!started.get()) {
      throw new BridgeStateException(BRIDGE_NOT_STARTED);
    }
    if (subscriber.equals("")) {
      throw new BridgeStateException(NOT_VALID_SUBSCRIBER);
    }
    PublishRequest request = new PublishRequest();
    request.setText(text);
    request.setSubscriber(subscriber);
    PublishResponse response = request(Request::Put, PUBLISH_PATH + topic, request, PublishResponse.class,
        HttpStatus.SC_OK);

    return response.getMessage();
  }
}
