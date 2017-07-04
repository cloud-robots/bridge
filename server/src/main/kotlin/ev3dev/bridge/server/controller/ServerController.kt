package ev3dev.bridge.server.controller

import ev3dev.bridge.server.exceptions.MissingParametersException
import ev3dev.bridge.server.model.SubscribeRequest
import ev3dev.bridge.server.model.SubscribeResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController()
class ServerController {

    companion object {
        const val SUBSCRIBE_PATH = "/subscribe/"
    }

    @RequestMapping(value = SUBSCRIBE_PATH, method = arrayOf(RequestMethod.PUT))
    @ResponseBody()
    fun putSubscription(@RequestBody subscribeRequest: SubscribeRequest): SubscribeResponse {
        if (subscribeRequest.topics.isEmpty()) {
            throw MissingParametersException("topics must be provided")
        }
        return SubscribeResponse(UUID.randomUUID().toString())
    }
}