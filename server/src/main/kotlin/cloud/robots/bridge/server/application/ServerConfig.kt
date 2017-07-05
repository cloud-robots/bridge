package cloud.robots.bridge.server.application

import cloud.robots.bridge.server.controller.ServerController
import cloud.robots.bridge.server.exceptions.ServerControllerAdvice
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Suppress("unused")
@Configuration
class ServerConfig {

    @Bean
    fun serverController() = ServerController()

    @Bean
    fun serverControllerAdvice() = ServerControllerAdvice()
}
