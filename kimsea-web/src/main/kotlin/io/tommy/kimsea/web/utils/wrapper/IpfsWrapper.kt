package io.tommy.kimsea.web.utils.wrapper

import io.ipfs.api.IPFS
import java.util.concurrent.ConcurrentHashMap

class IpfsWrapper (
    private val urls:List<String>
) {
    private val mappingUrls = ConcurrentHashMap<String, String>()
    private val mappingIpfs = ConcurrentHashMap<String, IPFS>()

    fun attached() : IpfsWrapper {
        urls.forEachIndexed() {index, url ->
            val mappingKey = "NO_${index}"
            mappingUrls.put(mappingKey, url)
            if (url == "ipfs.infura.io") {
                mappingIpfs.put(mappingKey, IPFS(url, 5001, "/api/v0/", true))
            } else {
                mappingIpfs.put(mappingKey, IPFS(url, 5001, "/api/v0/", false))
            }
        }
        return this
    }

    fun ipfs() : IPFS {
        return mappingIpfs["NO_0"]!!
    }
}