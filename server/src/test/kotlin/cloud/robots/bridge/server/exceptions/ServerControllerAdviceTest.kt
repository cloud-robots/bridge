package cloud.robots.bridge.server.exceptions

import cloud.robots.bridge.server.model.ErrorResponse
import cloud.robots.bridge.server.model.SubscribeRequest
import cloud.robots.bridge.server.service.SubscriberService
import cloud.robots.bridge.server.test.BaseSpringBootTest
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.reset
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should not be blank`
import org.junit.Test
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


class ServerControllerAdviceTest : BaseSpringBootTest(){

  companion object{
    const val SUBSCRIBE_PATH = "/subscribe/"

    const val NEWS_TOPIC = "news"
    val SINGLE_TOPIC = arrayOf(NEWS_TOPIC)

    const val SERVER_ERROR = "server error"
    const val SERVER_ENCOUNTER_ERROR = "the server has encounter an error"
  }

  @MockBean
  lateinit var subscriberService : SubscriberService

  @Test
  fun `we should handle generic exceptions`(){

    doThrow(RuntimeException::class).`when`(subscriberService).create(Mockito.anyListOf(String::class.java))

    val errorResponse = SubscribeRequest(SINGLE_TOPIC).put(SUBSCRIBE_PATH)
        .andExpect(MockMvcResultMatchers.status().isInternalServerError)
        .andDo(MockMvcResultHandlers.print())
        .body<ErrorResponse>()

    errorResponse.error `should equal to` SERVER_ERROR
    errorResponse.message `should equal to` SERVER_ENCOUNTER_ERROR
    errorResponse.timestamp.`should not be blank`()

    reset(subscriberService)
  }
}
