package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.test.BaseSpringBootTest
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.shouldNotBeBlank
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class SubscriberServiceTest : BaseSpringBootTest() {

  @Autowired
  lateinit var subscriberService : SubscriberService

  @Test
  fun `we could create a subscriber`() {
    val subscriber = subscriberService.create()
    subscriber.id.shouldNotBeBlank()
  }

  @Test
  fun `we could get a subscriber`() {
    val subscriber = subscriberService.create()
    val another = subscriberService.get(subscriber.id)

    another.id `should equal to` subscriber.id
  }

}