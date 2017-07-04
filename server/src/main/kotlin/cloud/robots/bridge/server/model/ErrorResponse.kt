package cloud.robots.bridge.server.model

import cloud.robots.bridge.server.exceptions.ServerException
import cloud.robots.bridge.server.utils.GetDateTime


data class ErrorResponse(val status: Int = 0,
                         val error: String = "",
                         val message: String = "") {

    constructor(serverException: ServerException) : this(serverException.status.value(),
            serverException.error, serverException.message)

    val timestamp = GetDateTime.UTC
}
