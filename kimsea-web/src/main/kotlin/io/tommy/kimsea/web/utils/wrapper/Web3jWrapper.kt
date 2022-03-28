package io.tommy.kimsea.web.utils.wrapper

import io.tommy.kimsea.web.utils.HttpUtil
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.util.concurrent.ConcurrentHashMap

class Web3jWrapper(
    private val urls:List<String>
) {

    private val mappingUrls = ConcurrentHashMap<String, String>()
    private val mappingWeb3j = ConcurrentHashMap<String, Web3j>()

    fun attached() : Web3jWrapper {
        urls.forEachIndexed() {index, url ->
            val mappingKey = "NO_${index}"
            mappingUrls.put(mappingKey, url)
            mappingWeb3j.put(mappingKey, Web3j.build(HttpService(url, HttpUtil.getUnsafeOkHttpClient(30, 10, 60, 60))))
        }
        return this
    }

    fun web3j() : Web3j {
        var index = "NO_" +  (urls.indices).random()
        return mappingWeb3j[index]!!
    }

    fun dynamicWeb3j() : Web3j {
        return Web3j.build(HttpService(mappingUrls["NO_" + (urls.indices).random()]))
    }
}