package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.jpa.entity.Subscriber
import cloud.robots.bridge.server.jpa.repository.SubscribersRepository
import cloud.robots.bridge.server.utils.UniqueUUIDGenerator

class SubscriberServiceImpl(val subscribersRepository: SubscribersRepository) : SubscriberService {
  override fun create() = subscribersRepository.save(Subscriber(UniqueUUIDGenerator.new))!!
  override fun get(id : String) = subscribersRepository.findOne(id)!!
}