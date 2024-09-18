package org.aaronwtlu.project

class AndroidNetworkService: NetworkService {
    override fun name(): String {
        return "Android Network Service"
    }

    override fun loadConfig() {
    }
}

actual class NetworkServiceIMP actual constructor() {
    actual fun name(): String {
        return "Android IMP"
    }
}