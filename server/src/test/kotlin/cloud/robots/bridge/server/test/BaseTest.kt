package cloud.robots.bridge.server.test

import cloud.robots.bridge.server.controller.ServerControllerTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

abstract class BaseTest {

    abstract var mockMvc: MockMvc

    fun <Type : Any> Type.put(path: String) =
            mockMvc.perform(MockMvcRequestBuilders.put(path)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .content(ServerControllerTest.objectMapper.writeValueAsString(this)))

    inline fun <reified Type : Any> ResultActions.body(): Type =
            ServerControllerTest.objectMapper.readValue(this.andReturn().response.contentAsString, Type::class.java)
}
