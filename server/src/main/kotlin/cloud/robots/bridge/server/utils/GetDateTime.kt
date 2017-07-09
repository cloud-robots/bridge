package cloud.robots.bridge.server.utils

import java.text.SimpleDateFormat
import java.util.*

class GetDateTime {
  companion object {
    fun getUTCDate(date : Date) : String {
      val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
      dateFormat.timeZone = TimeZone.getTimeZone("UTC")
      return dateFormat.format(date)
    }

    val UTC: String
      get() = getUTCDate(Date())
  }
}
