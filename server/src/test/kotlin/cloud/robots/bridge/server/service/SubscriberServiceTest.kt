package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.test.BaseSpringBootTest
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not be blank`
import org.amshove.kluent.`should not equal to`
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class SubscriberServiceTest : BaseSpringBootTest() {

  companion object{
    const val NEWS_TOPIC = "news"
    const val HELLO_TOPIC = "hello"
    val SINGLE_TOPIC = listOf(NEWS_TOPIC)
    val MULTIPLE_TOPICS = listOf(NEWS_TOPIC, HELLO_TOPIC)
  }


  @Autowired
  lateinit var subscriberService : SubscriberService

  @Test
  fun `we could create a subscriber`() {
    val subscriber = subscriberService.create(SINGLE_TOPIC)
    subscriber.id.`should not be blank`()
    subscriber.topics.size `should equal to` 1
    subscriber.topics[0].id `should equal` NEWS_TOPIC
  }

  @Test
  fun `we could create multiple subscribers`() {
    val subscriber1 = subscriberService.create(SINGLE_TOPIC)
    val subscriber2 = subscriberService.create(MULTIPLE_TOPICS)

    subscriber1.id.`should not be blank`()
    subscriber1.topics.size `should equal to` 1
    subscriber1.topics[0].id `should equal` NEWS_TOPIC

    subscriber2.id.`should not be blank`()
    subscriber2.id `should not equal to` subscriber1.id
    subscriber2.topics.size `should equal to` 2
    subscriber2.topics[0].id `should equal` NEWS_TOPIC
    subscriber2.topics[1].id `should equal` HELLO_TOPIC
  }

  @Test
  fun `we could get a subscriber with multiple topics`() {
    val subscriber = subscriberService.create(MULTIPLE_TOPICS)
    val another = subscriberService.get(subscriber.id)

    another.id `should equal to` subscriber.id
    another.topics.size `should equal to` 2
    another.topics[0].id `should equal` NEWS_TOPIC
    another.topics[1].id `should equal` HELLO_TOPIC
  }

}