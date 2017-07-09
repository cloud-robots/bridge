package cloud.robots.bridge.server.jpa.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Message(@Id
                   val id: String = "",
                   val fromSubscriber: String = "",
                   val text: String = "",
                   val topic: String = "",
                   var created : Date = Date.from(Instant.now()))
