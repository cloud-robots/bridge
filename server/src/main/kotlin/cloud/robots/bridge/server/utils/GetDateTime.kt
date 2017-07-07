package cloud.robots.bridge.server.utils

import java.text.SimpleDateFormat
import java.util.*

class GetDateTime {
  companion object {
    val UTC: String
      get() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(Date())
      }
  }
}
