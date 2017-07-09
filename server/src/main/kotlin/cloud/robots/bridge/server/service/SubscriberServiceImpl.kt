package cloud.robots.bridge.server.service

import cloud.robots.bridge.server.exceptions.SubscriberNotFoundException
import cloud.robots.bridge.server.jpa.entity.Message
import cloud.robots.bridge.server.jpa.entity.Subscriber
import cloud.robots.bridge.server.jpa.entity.Topic
import cloud.robots.bridge.server.jpa.repository.SubscribersRepository
import cloud.robots.bridge.server.utils.UniqueUUIDGenerator

open class SubscriberServiceImpl(val subscribersRepository: SubscribersRepository) : SubscriberService {
  override fun create(topics: List<String>) = subscribersRepository
      .save(Subscriber(UniqueUUIDGenerator.new, topics.asSequence().map { Topic(it) }.toMutableSet()))!!

  override fun get(id: String) = subscribersRepository.findOne(id) ?:
      throw SubscriberNotFoundException("subscriber '$id' not found")

  fun delete(subscriber: Subscriber) {
    subscriber.topics = mutableSetOf()
    subscriber.messages = mutableSetOf()
    subscribersRepository.delete(subscriber)
  }

  override fun delete(id: String) {
    delete(get(id))
  }

  override fun findByTopic(id: String) = subscribersRepository.findByTopics_Id(id)

  override fun deleteAll() = subscribersRepository.findAll().forEach(this::delete)

  private fun addMessage(subscriber: Subscriber, message: Message) {
    subscriber.messages.add(message)
    subscribersRepository.save(subscriber)
  }

  private operator fun <T : Any, K : Any> Pair<K, T>.invoke(function: (K, T) -> Unit) = function(this.first, this.second)
  private fun <T : Any, K : Any> Iterable<Pair<K, T>>.forEach(function: (K, T) -> Unit) = this.forEach { it(function) }

  override fun message(from: String, topic: String, text: String): Message {
    val fromSubscriber = get(from)
    val message = Message(UniqueUUIDGenerator.new, fromSubscriber.id, text, topic)
    findByTopic(topic).map { it to message }.forEach(this::addMessage)
    return message
  }

  override fun readMessages(id: String): List<Message> {
    val subscriber = get(id)
    val messages = subscriber.messages.sortedBy { it.created }.toList()
    subscriber.messages.clear()
    subscribersRepository.save(subscriber)
    return messages
  }
}
