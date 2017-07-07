package cloud.robots.bridge.server.test

import cloud.robots.bridge.server.application.ServerApplication
import cloud.robots.bridge.server.controller.ServerControllerTest
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(ServerApplication::class))
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class BaseSpringBootTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  fun <Type : Any> Type.put(path: String) =
      mockMvc.perform(MockMvcRequestBuilders.put(path)
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
          .content(ServerControllerTest.objectMapper.writeValueAsString(this)))

  inline fun <reified Type : Any> ResultActions.body(): Type =
      ServerControllerTest.objectMapper.readValue(this.andReturn().response.contentAsString, Type::class.java)
}
