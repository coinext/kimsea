package io.tommy.kimsea.web.controllers

import io.tommy.kimsea.web.providers.IpfsProvider
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/file")
class FileController(
    private val ipfsProvider: IpfsProvider
) {

    @GetMapping(value = ["/image/{fileName}"], produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseBody
    fun getImage(@PathVariable fileName:String) : ByteArray? {
        return ipfsProvider.getLocalFileByteArray(fileName)
    }

    @GetMapping(value = ["/video/{fileName}"], produces = ["video/mp4"])
    @ResponseBody
    fun getVideo(@PathVariable fileName:String) : ByteArray? {
        return ipfsProvider.getLocalFileByteArray(fileName)
    }

    @GetMapping(value = ["/audio/{fileName}"], produces = ["audio/mpeg"])
    @ResponseBody
    fun getAudio(@PathVariable fileName:String) : ByteArray? {
        return ipfsProvider.getLocalFileByteArray(fileName)
    }

    @GetMapping(value = ["/model/{fileName}"], produces = ["application/octet-stream"])
    @ResponseBody
    fun getModel(@PathVariable fileName:String) : ByteArray? {
        return ipfsProvider.getLocalFileByteArray(fileName)
    }
}