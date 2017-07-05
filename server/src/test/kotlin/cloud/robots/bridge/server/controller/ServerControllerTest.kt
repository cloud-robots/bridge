package cloud.robots.bridge.server.controller

import cloud.robots.bridge.server.model.ErrorResponse
import cloud.robots.bridge.server.model.SubscribeRequest
import cloud.robots.bridge.server.model.SubscribeResponse
import cloud.robots.bridge.server.test.BaseSpringBootTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.shouldNotBeBlank
import org.junit.Test
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
    }

    @Test
    fun `put empty topics in subscribe should error`() {
        val errorResponse = SubscribeRequest().put(SUBSCRIBE_PATH)
                .andExpect(status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
                .body<ErrorResponse>()

        errorResponse.error `should equal to` MISSING_PARAMETERS
        errorResponse.message `should equal to` TOPICS_MUST_BE_PROVIDED
        errorResponse.timestamp.shouldNotBeBlank()
    }

    @Test
    fun `put not valid json in subscribe should error`() {
        val errorResponse = "".put(SUBSCRIBE_PATH)
                .andExpect(status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
                .body<ErrorResponse>()

        errorResponse.error `should equal to` INVALID_REQUEST
        errorResponse.message `should equal to` REQUEST_CANNOT_BE_INTERPRETED
        errorResponse.timestamp.shouldNotBeBlank()
    }

    @Test
    fun `put a single topic should work`() {
        val subscribeResponse = SubscribeRequest(SINGLE_TOPIC).put(SUBSCRIBE_PATH)
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .body<SubscribeResponse>()

        subscribeResponse.subscriber.shouldNotBeBlank()
    }

    @Test
    fun `put multiple topics should work`() {
        val subscribeResponse = SubscribeRequest(MULTIPLE_TOPICS).put(SUBSCRIBE_PATH)
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .body<SubscribeResponse>()

        subscribeResponse.subscriber.shouldNotBeBlank()
    }
}
