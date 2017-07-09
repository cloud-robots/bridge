package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.jpa.entity.Message
import cloud.robots.bridge.server.jpa.entity.Subscriber

interface SubscriberService {
  fun create(topics: List<String>): Subscriber
  fun get(id: String): Subscriber
  fun delete(id: String)
  fun findByTopic(id: String) : List<Subscriber>
  fun deleteAll()
  fun message(from : String, topic: String, text: String) : Message
}
