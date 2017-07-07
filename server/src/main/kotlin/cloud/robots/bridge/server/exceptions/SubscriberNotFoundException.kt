package cloud.robots.bridge.server.exceptions

import org.springframework.http.HttpStatus

class SubscriberNotFoundException(message: String) :
    ServerException(message, "subscriber not found", HttpStatus.NOT_FOUND)
