package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.jpa.entity.Subscriber

interface SubscriberService {
  fun create(topics : List<String>) : Subscriber
  fun get(id : String) : Subscriber
}