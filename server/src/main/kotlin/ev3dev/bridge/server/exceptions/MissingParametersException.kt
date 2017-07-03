package ev3dev.bridge.server.exceptions

import org.springframework.http.HttpStatus

class MissingParametersException(error: String) :
        ServerException("missing parameters", error, HttpStatus.BAD_REQUEST)