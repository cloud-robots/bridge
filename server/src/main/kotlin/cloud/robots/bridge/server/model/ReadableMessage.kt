package cloud.robots.bridge.server.model


data class ReadableMessage(val subscriber: String = "",
                           val text: String = "",
                           val topic: String = "",
                           val time: String = "")
