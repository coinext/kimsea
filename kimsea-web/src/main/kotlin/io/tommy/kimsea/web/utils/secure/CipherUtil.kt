package io.tommy.kimsea.web.utils.secure

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@Component
class CipherUtil(
    @Value("\${aes.key}")
    val AES_KEY : String
) {
    private val keySize = 128
    private val iterationCount = 1000
    private val salt = "79752f1d3fd2432043c48e45b35b24645eb826a25c6f1804e9152665c345a552"
    private val iv = "2fad5a477d13ecda7f718fbd8a9f0443"
    private val passPhrase = AES_KEY
    private var cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

    @Throws(Exception::class)
    fun encrypt(plaintext: String): String {
        return encrypt(salt, iv, passPhrase, plaintext)
    }


    @Throws(Exception::class)
    fun decrypt(ciphertext: String): String {
        return decrypt(salt, iv, passPhrase, ciphertext)
    }


    @Throws(Exception::class)
    private fun encrypt(salt: String, iv: String, passPhrase: String, plaintext: String): String {
        val key = generateKey(salt, passPhrase)
        val encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext.toByteArray(Charsets.UTF_8))
        return encodeBase64(encrypted)
    }


    @Throws(Exception::class)
    private fun decrypt(salt: String, iv: String, passPhrase: String, ciphertext: String): String {
        val key = generateKey(salt, passPhrase)
        val decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, decodeBase64(ciphertext))
        return String(decrypted, Charsets.UTF_8)
    }


    @Throws(Exception::class)
    private fun doFinal(encryptMode: Int, key: SecretKey, iv: String, bytes: ByteArray): ByteArray {
        cipher!!.init(encryptMode, key, IvParameterSpec(decodeHex(iv)))
        return cipher!!.doFinal(bytes)
    }


    @Throws(Exception::class)
    private fun generateKey(salt: String, passPhrase: String): SecretKey {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec: KeySpec = PBEKeySpec(passPhrase.toCharArray(), decodeHex(salt), iterationCount, keySize)
        return SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
    }


    private fun encodeBase64(bytes: ByteArray): String {
        return Base64.encodeBase64String(bytes)
    }


    private fun decodeBase64(str: String): ByteArray {
        return Base64.decodeBase64(str)
    }


    private fun encodeHex(bytes: ByteArray): String? {
        return Hex.encodeHexString(bytes)
    }


    @Throws(Exception::class)
    private fun decodeHex(str: String): ByteArray {
        return Hex.decodeHex(str.toCharArray())
    }


    private fun getRandomHexString(length: Int): String? {
        val salt = ByteArray(length)
        SecureRandom().nextBytes(salt)
        return encodeHex(salt)
    }
}