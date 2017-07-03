package ev3dev.bridge.server.exceptions

import ev3dev.bridge.server.model.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler

@org.springframework.web.bind.annotation.ControllerAdvice
class ServerControllerAdvice {

    companion object {
        private val logger = LoggerFactory.getLogger(ServerControllerAdvice::class.java)
        private const val SERVER_EXCEPTION = "server exception"
        private const val MESSAGE_NOT_READABLE = "message mot readable exception"
        private const val GENERIC_EXCEPTION = "generic exception"
    }

    @ExceptionHandler(value = ServerException::class)
    fun serverExceptionHandler(serverException: ServerException): ResponseEntity<ErrorResponse> {
        logger.error(SERVER_EXCEPTION, serverException)
        return ResponseEntity(ErrorResponse(serverException), serverException.status)
    }


    @ExceptionHandler(value = HttpMessageNotReadableException::class)
    fun messageNotReadableHandler(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        logger.error(MESSAGE_NOT_READABLE, exception)
        return ResponseEntity(ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "invalid request", "the request to the server can not be interpreted")
                , HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = Exception::class)
    fun genericExceptionHandler(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        logger.error(GENERIC_EXCEPTION, exception)
        return ResponseEntity(ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "server error", "the server has encounter an error")
                , HttpStatus.INTERNAL_SERVER_ERROR)

    }
}