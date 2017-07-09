package cloud.robots.bridge.server.model

data class SubscriptionsResponse(val topics: Array<String> = arrayOf<String>(), val subscriber: String = "")