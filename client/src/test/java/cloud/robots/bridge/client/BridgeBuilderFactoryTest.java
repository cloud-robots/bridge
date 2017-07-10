package cloud.robots.bridge.client;

import cloud.robots.bridge.client.internal.BridgeBuilderFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;


public class BridgeBuilderFactoryTest {
  private static final String TEST_URL = "http://localhost:8080";
  private static final int TIMEOUT = 1500;

  @Test
  public void connect() throws Exception {
    Bridge bridge = BridgeBuilderFactory.Default()
        .connect(TEST_URL)
        .timeout(TIMEOUT)
        .subscribe("hello")
        .subscribe("news")
        .build();

    assertThat(bridge.getSubscriber(), not(isEmptyOrNullString()));
  }
}
