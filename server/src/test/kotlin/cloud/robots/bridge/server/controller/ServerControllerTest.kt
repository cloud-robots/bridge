package cloud.robots.bridge.server.controller

import cloud.robots.bridge.server.exceptions.SubscriberNotFoundException
import cloud.robots.bridge.server.model.ErrorResponse
import cloud.robots.bridge.server.model.SubscribeRequest
import cloud.robots.bridge.server.model.SubscribeResponse
import cloud.robots.bridge.server.model.SubscriptionsResponse
import cloud.robots.bridge.server.service.SubscriberService
import cloud.robots.bridge.server.test.BaseSpringBootTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not be blank`
import org.amshove.kluent.`should throw`
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ServerControllerTest : BaseSpringBootTest() {

  companion object {
    val objectMapper = ObjectMapper()

    const val SUBSCRIBE_PATH = "/subscribe/"

    const val NEWS_TOPIC = "news"
    const val HELLO_TOPIC = "hello"
    val SINGLE_TOPIC = arrayOf(NEWS_TOPIC)
    val MULTIPLE_TOPICS = arrayOf(NEWS_TOPIC, HELLO_TOPIC)

    const val MISSING_PARAMETERS = "missing parameters"
    const val TOPICS_MUST_BE_PROVIDED = "topics must be provided"

    const val INVALID_REQUEST = "invalid request"
    const val REQUEST_CANNOT_BE_INTERPRETED = "the request to the server can not be interpreted"

    const val INVALID_SUBSCRIBER = "invalid"
    const val SUBSCRIBER_NOT_FOUND = "subscriber not found"
    const val NOT_FOUND_MESSAGE = "subscriber '$INVALID_SUBSCRIBER' not found"

  }

  @Autowired
  lateinit var subscriberService: SubscriberService

  @Test
  fun `put empty topics in subscribe should error`() {
    val errorResponse = SubscribeRequest().put(SUBSCRIBE_PATH)
        .andExpect(status().isBadRequest)
        .andDo(MockMvcResultHandlers.print())
        .body<ErrorResponse>()

    errorResponse.error `should equal to` MISSING_PARAMETERS
    errorResponse.message `should equal to` TOPICS_MUST_BE_PROVIDED
    errorResponse.timestamp.`should not be blank`()
  }

  @Test
  fun `put not valid json in subscribe should error`() {
    val errorResponse = "".put(SUBSCRIBE_PATH)
        .andExpect(status().isBadRequest)
        .andDo(MockMvcResultHandlers.print())
        .body<ErrorResponse>()

    errorResponse.error `should equal to` INVALID_REQUEST
    errorResponse.message `should equal to` REQUEST_CANNOT_BE_INTERPRETED
    errorResponse.timestamp.`should not be blank`()
  }

  @Test
  fun `put a single topic should work`() {
    val subscribeResponse = SubscribeRequest(SINGLE_TOPIC).put(SUBSCRIBE_PATH)
        .andExpect(status().isCreated)
        .andDo(MockMvcResultHandlers.print())
        .body<SubscribeResponse>()

    subscribeResponse.subscriber.`should not be blank`()

    val subscriber = subscriberService.get(subscribeResponse.subscriber)

    subscriber.id `should equal to` subscribeResponse.subscriber
    subscriber.topics.size `should equal to` 1
    subscriber.topics[0].id `should equal` NEWS_TOPIC
  }

  @Test
  fun `put multiple topics should work`() {
    val subscribeResponse = SubscribeRequest(MULTIPLE_TOPICS).put(SUBSCRIBE_PATH)
        .andExpect(status().isCreated)
        .andDo(MockMvcResultHandlers.print())
        .body<SubscribeResponse>()

    subscribeResponse.subscriber.`should not be blank`()

    val subscriber = subscriberService.get(subscribeResponse.subscriber)

    subscriber.id `should equal to` subscribeResponse.subscriber
    subscriber.topics.size `should equal to` 2
    subscriber.topics[0].id `should equal` NEWS_TOPIC
    subscriber.topics[1].id `should equal` HELLO_TOPIC
  }

  @Test
  fun `get a valid subscriber should work`() {

    val subscriber = subscriberService.create(MULTIPLE_TOPICS.toList())

    val subscriptionsResponse = SubscribeRequest().get(SUBSCRIBE_PATH + subscriber.id)
        .andExpect(status().isOk)
        .andDo(MockMvcResultHandlers.print())
        .body<SubscriptionsResponse>()

    subscriptionsResponse.subscriber `should equal to` subscriber.id
    subscriptionsResponse.topics.size `should equal to` 2
    subscriptionsResponse.topics[0] `should equal` NEWS_TOPIC
    subscriptionsResponse.topics[1] `should equal` HELLO_TOPIC
  }

  @Test
  fun `get a not found subscriber should error`() {
    val errorResponse = SubscribeRequest().get(SUBSCRIBE_PATH + INVALID_SUBSCRIBER)
        .andExpect(status().isNotFound)
        .andDo(MockMvcResultHandlers.print())
        .body<ErrorResponse>()

    errorResponse.error `should equal to` SUBSCRIBER_NOT_FOUND
    errorResponse.message `should equal to` NOT_FOUND_MESSAGE
    errorResponse.timestamp.`should not be blank`()
  }

  @Test
  fun `delete a valid subscriber should work`() {

    val subscriber = subscriberService.create(MULTIPLE_TOPICS.toList())

    val deleteResponse = SubscribeRequest().delete(SUBSCRIBE_PATH + subscriber.id)
        .andExpect(status().isOk)
        .andDo(MockMvcResultHandlers.print())
        .body<SubscribeResponse>()

    deleteResponse.subscriber `should equal to` subscriber.id

    { subscriberService.get(subscriber.id) } `should throw` SubscriberNotFoundException::class

  }

  @Test
  fun `delete a not found subscriber should error`() {
    val errorResponse = SubscribeRequest().delete(SUBSCRIBE_PATH + INVALID_SUBSCRIBER)
        .andExpect(status().isNotFound)
        .andDo(MockMvcResultHandlers.print())
        .body<ErrorResponse>()

    errorResponse.error `should equal to` SUBSCRIBER_NOT_FOUND
    errorResponse.message `should equal to` NOT_FOUND_MESSAGE
    errorResponse.timestamp.`should not be blank`()
  }
}
