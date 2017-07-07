package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.jpa.entity.Subscriber

interface SubscriberService {
  fun create() : Subscriber
  fun get(id : String) : Subscriber
}