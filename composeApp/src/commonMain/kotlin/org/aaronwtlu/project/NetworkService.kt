
package org.aaronwtlu.project

interface NetworkService {
    fun name(): String
    fun loadConfig()
}

expect class NetworkServiceIMP() {
    fun name(): String
}

class NetworkServiceProvider(private val networkService: NetworkService): NetworkService {
    override fun loadConfig() {
        networkService.loadConfig()
    }
    override fun name(): String {
        return networkService.name()
    }
}
