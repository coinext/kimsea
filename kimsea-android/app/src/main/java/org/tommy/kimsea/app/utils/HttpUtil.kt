package org.tommy.kimsea.app.utils

import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object HttpUtil {
    @Throws(java.lang.Exception::class)
    fun convertToQueryStringToHashMap(
        source: String
    ): HashMap<String, String>? {
        val data = HashMap<String, String>()
        val arrParameters = source.split("&").toTypedArray()
        for (tempParameterString in arrParameters) {
            val arrTempParameter = tempParameterString
                .split("=").toTypedArray()
            if (arrTempParameter.size >= 2) {
                val parameterKey = arrTempParameter[0]
                val parameterValue = arrTempParameter[1]
                data[parameterKey] = parameterValue
            } else {
                val parameterKey = arrTempParameter[0]
                data[parameterKey] = ""
            }
        }
        return data
    }

    fun getUnsafeOkHttpClient(
        connectionPoolSize: Int?,
        connectTimeout: Long?,
        readTimeout: Long?,
        writeTimeout: Long?
    ): OkHttpClient {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }
                }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (trustAllCerts[0] as X509TrustManager))
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String?, session: SSLSession?): Boolean {
                        return true
                    }
                })
            builder.connectionPool(
                ConnectionPool(connectionPoolSize!!, 60000, TimeUnit.MILLISECONDS)
            )
            builder.connectTimeout(connectTimeout!!, TimeUnit.SECONDS)
            builder.readTimeout(readTimeout!!, TimeUnit.SECONDS)
            builder.writeTimeout(writeTimeout!!, TimeUnit.SECONDS)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun getUnsafeOkHttpClientByBlogApi(
        connectionPoolSize: Int?,
        connectTimeout: Long?,
        readTimeout: Long?,
        writeTimeout: Long?
    ): OkHttpClient {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }
                }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (trustAllCerts[0] as X509TrustManager))
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String?, session: SSLSession?): Boolean {
                        return true
                    }
                })
            builder.connectionPool(
                ConnectionPool(connectionPoolSize!!, 60000, TimeUnit.MILLISECONDS)
            )
            builder.connectTimeout(connectTimeout!!, TimeUnit.SECONDS)
            builder.readTimeout(readTimeout!!, TimeUnit.SECONDS)
            builder.writeTimeout(writeTimeout!!, TimeUnit.SECONDS)
            builder.followRedirects(true)
            builder.followSslRedirects(true)
            builder.addInterceptor (object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    println(chain.request().toString())
                    return chain.proceed(chain.request());
                }
            })
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}