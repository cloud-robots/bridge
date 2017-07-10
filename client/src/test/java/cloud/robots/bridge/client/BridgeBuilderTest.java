package cloud.robots.bridge.client;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;


public class BridgeBuilderTest {
  private static final String TEST_URL = "http://localhost:8080";

  @Test
  public void connect() throws Exception {
    Bridge bridge = BridgeBuilder.DefaultBridgeBuilder()
        .connect(TEST_URL)
        .subscribe("hello")
        .subscribe("news")
        .build();

    assertThat(bridge.getSubscriber(), not(isEmptyOrNullString()));
  }
}
