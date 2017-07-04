package cloud.robots.bridge.server.controller

import cloud.robots.bridge.server.exceptions.MissingParametersException
import cloud.robots.bridge.server.model.SubscribeRequest
import cloud.robots.bridge.server.model.SubscribeResponse
import cloud.robots.bridge.server.utils.UniqueUUIDGenerator
import org.springframework.web.bind.annotation.*

@Suppress("unused")
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
        return SubscribeResponse(UniqueUUIDGenerator.new)
    }
}