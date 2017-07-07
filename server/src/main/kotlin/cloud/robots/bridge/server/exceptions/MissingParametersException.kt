package cloud.robots.bridge.server.exceptions

import org.springframework.http.HttpStatus

class MissingParametersException(message: String) :
    ServerException(message, "missing parameters", HttpStatus.BAD_REQUEST)