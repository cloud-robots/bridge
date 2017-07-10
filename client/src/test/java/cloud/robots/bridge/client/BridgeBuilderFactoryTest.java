package cloud.robots.bridge.client;

import cloud.robots.bridge.client.exceptions.BridgeException;
import cloud.robots.bridge.client.internal.BridgeBuilderFactory;
import cloud.robots.bridge.client.model.Message;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;


public class BridgeBuilderFactoryTest {
  private static final String TEST_URL = "http://localhost:8080";
  private static final int TIMEOUT = 1500;
  private static final int REFRESH = 1500;
  private static final int WAIT = 1000;

  private Bridge bridge;

  private boolean endTest = false;

  private void helloMessage(Message message){
    try {
      System.out.println("got a hello: '" + message.getText() + "', from: '" + message.getSubscriber() + "'");
      bridge.send("news", "the news is that we got a hello");
    } catch (BridgeException e) {
      e.printStackTrace();
    }
  }

  private void newsMessage(Message message) {
    System.out.println("got news: '" + message.getText() + "', from: '" + message.getSubscriber() + "'");
    endTest = true;
  }

  @Test
  public void connect() throws Exception {
    bridge = BridgeBuilderFactory.Default()
        .connect(TEST_URL)
        .timeout(TIMEOUT)
        .subscribe("hello", this::helloMessage)
        .subscribe("news", this::newsMessage)
        .refresh(REFRESH)
        .build();

    assertThat(bridge.getSubscriber(), not(isEmptyOrNullString()));

    bridge.send("hello", "hello world");

    while(!endTest){
      Thread.sleep(WAIT);
    }

    bridge.stop();
  }
}
