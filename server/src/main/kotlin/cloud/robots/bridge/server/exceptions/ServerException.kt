package cloud.robots.bridge.server.exceptions

import org.springframework.http.HttpStatus

open class ServerException(override val message: String, val error: String, val status: HttpStatus) :
        Exception(message, null)