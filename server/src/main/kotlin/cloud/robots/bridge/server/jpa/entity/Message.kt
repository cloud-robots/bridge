package cloud.robots.bridge.server.jpa.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Message(@Id
                   val id: String = "",
                   val fromSubscriber: String = "",
                   val text: String = "")
