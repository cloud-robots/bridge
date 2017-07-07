package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.exceptions.SubscriberNotFoundException
import cloud.robots.bridge.server.jpa.entity.Subscriber
import cloud.robots.bridge.server.jpa.entity.Topic
import cloud.robots.bridge.server.jpa.repository.SubscribersRepository
import cloud.robots.bridge.server.utils.UniqueUUIDGenerator

class SubscriberServiceImpl(val subscribersRepository: SubscribersRepository) : SubscriberService {
  override fun create(topics: List<String>) = subscribersRepository.
      save(Subscriber(UniqueUUIDGenerator.new, topics.map { Topic(it) }))!!

  override fun get(id: String) = subscribersRepository.findOne(id) ?:
      throw SubscriberNotFoundException("subscriber '$id' not found")
}
