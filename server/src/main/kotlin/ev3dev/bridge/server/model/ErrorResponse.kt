package ev3dev.bridge.server.model

import ev3dev.bridge.server.exceptions.ServerException
import java.text.SimpleDateFormat
import java.util.*


data class ErrorResponse(val status: Int = 0,
                         val error: String = "",
                         val message: String = "") {

    constructor(serverException: ServerException) : this(serverException.status.value(),
            serverException.error, serverException.message)

    lateinit var timestamp: String

    init {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        timestamp = dateFormat.format(Date())
    }

}