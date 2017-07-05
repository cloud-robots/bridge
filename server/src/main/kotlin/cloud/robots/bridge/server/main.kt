package cloud.robots.bridge.server


import cloud.robots.bridge.server.application.ServerApplication
import org.springframework.boot.SpringApplication

fun main(args: Array<String>) {
    SpringApplication.run(ServerApplication::class.java, *args)
}
