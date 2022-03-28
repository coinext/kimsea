package io.tommy.kimsea.web.providers

import com.fasterxml.jackson.databind.ObjectMapper
import io.ipfs.api.NamedStreamable
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.wrapper.IpfsWrapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
class IpfsProvider(
    private val ipfsWapper: IpfsWrapper,
    private val okHttpClient: OkHttpClient,
    @Value("\${upload.path}")
    val UPLOAD_PATH:String
) {
    data class BodyToAdd (
        val Name:String,
        val Hash:String,
        val Size:String,
    )

    val log = logger()

    val objectMapper = ObjectMapper()

    fun addAll(user: User, multipartFiles:List<MultipartFile>, isDelete:List<Boolean>) : List<Pair<String, String>> {
        var res = mutableListOf<Pair<String, String>>()
        var fileNames = mutableListOf<String>()
        var files = mutableListOf<NamedStreamable.FileWrapper>()
        var _files = mutableListOf<File>()
        multipartFiles.mapIndexed { index, multipartFile ->
            log.info("add=== ")
            val fileName = user.id.toString() + "-" + DigestUtils.md5Hex(multipartFile.originalFilename + System.currentTimeMillis())
            log.info("=== $fileName")
            val updatePath = UPLOAD_PATH +  user.id + "/"
            val path = File(updatePath)
            if (!path.exists()) {
                log.info("updatePath mkdir=== $updatePath")
                path.mkdir()
            }

            val file = File(updatePath +  fileName)
            multipartFile.transferTo(file)
            log.info("transferTo mkdir=== $updatePath +  $fileName")
            _files.add(file)
            fileNames.add(fileName)
            files.add(NamedStreamable.FileWrapper(file))

        }

        val ipfs = ipfsWapper.ipfs()
        val addResults = ipfs.add(files as List<NamedStreamable>?,false,false)
        var i = 0
        addResults.mapIndexed { index, merkleNode ->
            ipfs.pin.add(merkleNode.hash)
            res.add(Pair(fileNames.get(index), merkleNode.hash.toString()))

            if (isDelete.get(index)) {
                _files[index].deleteOnExit()
            }
        }
        return res
    }

    fun add(user: User, multipartFile:MultipartFile, isDelete:Boolean = true) : Pair<String, String> {
        log.info("**** add=== ")
        val fileName = user.id.toString() + "-" + DigestUtils.md5Hex(multipartFile.originalFilename + System.currentTimeMillis())
        log.info("=== $fileName")
        val updatePath = UPLOAD_PATH +  user.id + "/"
        val path = File(updatePath)
        if (!path.exists()) {
            log.info("updatePath mkdir=== $updatePath")
            path.mkdir()
        }

        val file = File(updatePath +  fileName)
        multipartFile.transferTo(file)
        log.info("transferTo mkdir=== $updatePath +  $fileName")
        val hash = add(file)

        if (isDelete) {
            file.deleteOnExit()
        }
        return Pair(fileName, hash)
    }

    fun add(content:String) : String {
        val ipfs = ipfsWapper.ipfs()
        val addResult = ipfs.add(NamedStreamable.ByteArrayWrapper(content.toByteArray()))[0]
        ipfs.pin.add(addResult.hash)
        return addResult.hash.toString()
    }

    fun addRaw(file:File) : String {
        val builder = MultipartBody.Builder()
            .setType("multipart/form-data".toMediaTypeOrNull()!!)
            .addFormDataPart("file", file.name, RequestBody.create("image/png".toMediaTypeOrNull(),  file))
            .build()

        val request: Request = Request.Builder()
            .header("Content-Type", "multipart/form-data")
            .header("Expecte", "")
            .url("https://ipfs.infura.io:5001/api/v0/add")
            .post(builder)
            .build()

        val body = okHttpClient.newCall(request).execute().body!!.string()
        log.info("raw add2 ======= $body")

        val hash = body.split(",")[1].replace("\"Hash\":", "").replace("\"", "")
        log.info("raw hash ======= $hash")
        return hash
    }

    fun add(file:File) : String {
        val ipfs = ipfsWapper.ipfs()
        val addResult = ipfs.add(NamedStreamable.FileWrapper(file))[0]
        ipfs.pin.add(addResult.hash)
        return addResult.hash.toString()
    }

    fun getLocalFileByteArray(fileName:String) : ByteArray {
        val tokens = fileName.split("-")
        val file = UPLOAD_PATH + tokens[0]+ "/" + fileName
        return FileUtils.readFileToByteArray(File(file))
    }

    fun getLinkUrl(hash:String) : String {
        return "https://ipfs.infura.io/ipfs/${hash}"
    }
}