package cloud.robots.bridge.server.utils

import java.util.*

class UniqueUUIDGenerator {
  companion object {
    val new: String
      get() = UUID.randomUUID().toString()
  }
}
