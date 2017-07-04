package ev3dev.bridge.server.controller

import com.fasterxml.jackson.databind.ObjectMapper
import ev3dev.bridge.server.model.ErrorResponse
import ev3dev.bridge.server.model.SubscribeRequest
import ev3dev.bridge.server.model.SubscribeResponse
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.shouldNotBeBlank
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ServerControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

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

    fun <Type : Any> Type.put(path: String) =
            mockMvc.perform(MockMvcRequestBuilders.put(path)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(this)))

    inline fun <reified Type : Any> ResultActions.body(): Type =
            objectMapper.readValue(this.andReturn().response.contentAsString, Type::class.java)

    @Test
    fun `put empty topics in subscribe should error`() {
        val errorResponse = SubscribeRequest().put(SUBSCRIBE_PATH)
                .andExpect(status().isBadRequest)
                .body<ErrorResponse>()

        errorResponse.error `should equal to` MISSING_PARAMETERS
        errorResponse.message `should equal to` TOPICS_MUST_BE_PROVIDED
    }

    @Test
    fun `put not valid json in subscribe should error`() {
        val errorResponse = "".put(SUBSCRIBE_PATH)
                .andExpect(status().isBadRequest)
                .body<ErrorResponse>()

        errorResponse.error `should equal to` INVALID_REQUEST
        errorResponse.message `should equal to` REQUEST_CANNOT_BE_INTERPRETED
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