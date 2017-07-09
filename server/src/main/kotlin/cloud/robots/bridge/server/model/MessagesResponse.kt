package cloud.robots.bridge.server.model

data class MessagesResponse(val subscriber : String = "", val messages : Array<ReadableMessage> = arrayOf<ReadableMessage>())
