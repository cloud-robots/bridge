package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.exceptions.SubscriberNotFoundException
import cloud.robots.bridge.server.test.BaseSpringBootTest
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class SubscriberServiceTest : BaseSpringBootTest() {

  companion object {
    const val NOT_TOPIC = "no topic"
    const val NEWS_TOPIC = "news"
    const val HELLO_TOPIC = "hello"
    val SINGLE_TOPIC = listOf(NEWS_TOPIC)
    val MULTIPLE_TOPICS = listOf(NEWS_TOPIC, HELLO_TOPIC)
    const val INVALID_SUBSCRIBER = "invalid"
    const val TEXT_MESSAGE_1 = "text message 1"
    const val TEXT_MESSAGE_2 = "text message 2"

  }

  @Autowired
  lateinit var subscriberService: SubscriberService

  @Before
  fun setup(){
    subscriberService.deleteAll()
  }

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

  @Test
  fun `we should get an exception getting a no existing subscriber`() {
    { subscriberService.get(INVALID_SUBSCRIBER) } `should throw` SubscriberNotFoundException::class
  }

  @Test
  fun `we should get an exception deleting a no existing subscriber`() {
    { subscriberService.delete(INVALID_SUBSCRIBER) } `should throw` SubscriberNotFoundException::class
  }

  @Test
  fun `we could delete a subscriber`() {
    val subscriber = subscriberService.create(MULTIPLE_TOPICS)

    subscriberService.delete(subscriber.id)

    val search = { subscriberService.get(subscriber.id) }

    search.`should throw`(SubscriberNotFoundException::class)
  }

  @Test
  fun `we could find subscribers by topic`(){
    subscriberService.create(SINGLE_TOPIC)
    subscriberService.create(MULTIPLE_TOPICS)

    val subscribers = subscriberService.findByTopic(NEWS_TOPIC)

    subscribers.size `should equal to` 2

    val subscribers2 = subscriberService.findByTopic(HELLO_TOPIC)

    subscribers2.size `should equal to` 1
  }

  @Test
  fun `find subscribers with a not subscribed topic should return 0 subscribers`(){
    val subscribers = subscriberService.findByTopic(NOT_TOPIC)

    subscribers.size `should equal to` 0
  }

  @Test
  fun `we could add messages`(){
    val subscriber1 = subscriberService.create(SINGLE_TOPIC)
    val subscriber2 = subscriberService.create(MULTIPLE_TOPICS)

    subscriberService.message(subscriber1.id, NEWS_TOPIC, TEXT_MESSAGE_1)
    subscriberService.message(subscriber1.id, NEWS_TOPIC, TEXT_MESSAGE_1)

    val subscribers = subscriberService.findByTopic(NEWS_TOPIC)

    subscribers.size `should equal to` 2
    subscribers.forEach {
      it.messages.size `should equal to` 2
      for( message in it.messages){
        message.text `should equal to` TEXT_MESSAGE_1
      }
    }

    subscriberService.message(subscriber2.id, HELLO_TOPIC, TEXT_MESSAGE_2)

    val subscribers2 = subscriberService.findByTopic(HELLO_TOPIC)

    subscribers2.size `should equal to` 1
    subscribers2.forEach {
      it.messages.size `should equal to` 3
      for( message in it.messages){
        (message.text == TEXT_MESSAGE_1).or(message.text == TEXT_MESSAGE_2).`should be true`()
      }
    }
  }

  @Test
  fun `adding messages for an invalid subscriber will fail`(){
    {
      subscriberService.message(INVALID_SUBSCRIBER, NEWS_TOPIC, TEXT_MESSAGE_1)
    } `should throw` SubscriberNotFoundException::class
  }
}
