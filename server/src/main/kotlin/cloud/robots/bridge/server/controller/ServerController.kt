package cloud.robots.bridge.server.controller

import cloud.robots.bridge.server.exceptions.MissingParametersException
import cloud.robots.bridge.server.model.SubscribeRequest
import cloud.robots.bridge.server.model.SubscribeResponse
import cloud.robots.bridge.server.model.SubscriptionsResponse
import cloud.robots.bridge.server.service.SubscriberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Suppress("unused")
@RestController()
class ServerController(val subscriberService: SubscriberService) {

  companion object {
    const val SUBSCRIBE_PATH = "/subscribe/"
    const val GET_SUBSCRIBER_PATH = SUBSCRIBE_PATH + "{id}"
  }

  @RequestMapping(value = SUBSCRIBE_PATH, method = arrayOf(RequestMethod.PUT))
  @ResponseBody()
  fun putSubscription(@RequestBody subscribeRequest: SubscribeRequest): ResponseEntity<SubscribeResponse> {
    if (subscribeRequest.topics.isEmpty()) {
      throw MissingParametersException("topics must be provided")
    }
    val subscriber = subscriberService.create(subscribeRequest.topics.toList())
    return ResponseEntity(SubscribeResponse(subscriber.id), HttpStatus.CREATED)
  }

  @RequestMapping(value = GET_SUBSCRIBER_PATH, method = arrayOf(RequestMethod.GET))
  @ResponseBody()
  fun getSubscription(@PathVariable id: String): SubscriptionsResponse {
    val subscriber = subscriberService.get(id)
    return SubscriptionsResponse(subscriber.topics.map { it.id }.toTypedArray(), subscriber.id)
  }

  @RequestMapping(value = GET_SUBSCRIBER_PATH, method = arrayOf(RequestMethod.DELETE))
  @ResponseBody()
  fun deleteSubscription(@PathVariable id: String): SubscribeResponse {
    subscriberService.delete(id)
    return SubscribeResponse(id)
  }
}
