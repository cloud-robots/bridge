package cloud.robots.bridge.client;

import cloud.robots.bridge.client.core.Bridge;
import cloud.robots.bridge.client.core.BridgeBuilderFactory;
import cloud.robots.bridge.client.core.exceptions.BridgeException;
import cloud.robots.bridge.client.core.model.Message;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThan;

public class AllFeaturesTest {
  private static final String TEST_URL = "http://localhost:8080";
  private static final int TIMEOUT = 1500;
  private static final int REFRESH = 1500;
  private static final long MAX_LOOPS = 10_000_000_000L;

  private Bridge bridge;

  private AtomicBoolean endTest = new AtomicBoolean(false);

  private void helloMessage(Message message) {
    try {
      System.out.println("got a hello: '" + message.getText() + "', from: '" + message.getSubscriber() + "'");
      bridge.send("news", "the news is that we got a hello");
    } catch (BridgeException e) {
      e.printStackTrace();
    }
  }

  private void newsMessage(Message message) {
    System.out.println("got news: '" + message.getText() + "', from: '" + message.getSubscriber() + "'");
    endTest.set(true);
  }

  @Test
  public void everythingShouldWork() throws Exception {
    bridge = BridgeBuilderFactory.Default()
        .connect(TEST_URL)
        .timeout(TIMEOUT)
        .subscribe("hello", this::helloMessage)
        .subscribe("news", this::newsMessage)
        .ignoreSelfMessages(false)
        .refresh(REFRESH)
        .build();

    bridge.start();

    assertThat(bridge.getSubscriber(), not(isEmptyOrNullString()));

    long loops;
    for (loops = 0L; loops < MAX_LOOPS; loops++) {
      if (loops == 0L) {
        bridge.send("hello", "hello world");
      }
      if (endTest.get()) {
        break;
      }
    }

    assertThat(loops, greaterThan(0L));
    assertThat(loops, lessThan(MAX_LOOPS));

    bridge.stop();
  }
}
