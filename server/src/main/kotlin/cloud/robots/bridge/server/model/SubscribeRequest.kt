package cloud.robots.bridge.server.model


data class SubscribeRequest( var topics : Array<String>){
    constructor() : this(arrayOf<String>())
}