package cloud.robots.bridge.server.jpa.repository

import cloud.robots.bridge.server.jpa.entity.Subscriber
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscribersRepository : CrudRepository<Subscriber, String>