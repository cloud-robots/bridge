package cloud.robots.bridge.server.jpa.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Topic(@Id val id : String = "")
