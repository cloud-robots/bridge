package ev3dev.bridge.server.controller

import com.fasterxml.jackson.databind.ObjectMapper
import ev3dev.bridge.server.model.SubscribeRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
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
    }

    infix fun <Type : Any> Type.put(path: String) =
            mockMvc.perform(MockMvcRequestBuilders.put(path)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(this)))

    @Test
    fun `put empty topics in subscribe should error`() {
        SubscribeRequest().put(SUBSCRIBE_PATH)
                .andExpect(status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `put not valid json in subscribe should error`() {
        "".put(SUBSCRIBE_PATH)
                .andExpect(status().isBadRequest)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `put a topic should work`() {
        val subscribeRequest = SubscribeRequest()

        subscribeRequest.topics += "aa"

        subscribeRequest.put(SUBSCRIBE_PATH)
                .andExpect(status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }
}