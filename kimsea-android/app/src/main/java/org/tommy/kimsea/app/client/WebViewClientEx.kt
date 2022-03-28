package org.tommy.kimsea.app.client

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.tommy.kimsea.app.NetworkNotConnectionActivity
import org.tommy.kimsea.app.PopupActivity
import org.tommy.kimsea.app.configs.Constants
import org.tommy.kimsea.app.configs.Constants.Companion.WEB_URL
import org.tommy.kimsea.app.utils.CustomAnimationDialog
import java.net.URISyntaxException


class WebViewClientEx(val m_oInstance: AppCompatActivity, val isMain: Boolean? = false) : WebViewClient() {

    var customAnimationDialog: CustomAnimationDialog

    init {
        customAnimationDialog = CustomAnimationDialog(m_oInstance)
    }

    fun overrideUrlLoading(view: WebView, url:String) :Boolean {
        if (url != null && url.startsWith("intent://")) {
            try {
                var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                var existPackage = m_oInstance.getPackageManager().getLaunchIntentForPackage(intent!!.`package`!!)
                if (existPackage != null) {
                    m_oInstance.startActivity(intent);
                } else {
                    var marketIntent = Intent(Intent.ACTION_VIEW);
                    marketIntent.setData(Uri.parse("market://details?id="+intent.getPackage()))
                    m_oInstance.startActivity(marketIntent)
                }
                return true
            }catch (e:Exception) {
            }
        } else if (url != null && url.startsWith("market://")) {
            try {
                var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent != null) {
                    m_oInstance.startActivity(intent);
                }
                return true
            } catch (e: URISyntaxException) {

            }
        } else if (url != null && url.startsWith("mailto:")) {
            var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            if (intent != null) {
                m_oInstance.startActivity(intent)
            }
            return true
        } else if (url != null && url.startsWith("tel://")) {
            var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            if (intent != null) {
                m_oInstance.startActivity(intent)
            }
            return true
        } else if (url != null && url.startsWith("kakaotalk://inappbrowser?url=")) {
            var intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            if (intent != null) {
                m_oInstance.startActivity(intent)
                //m_oInstance.overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
            return true
        } else if (url != null && url.startsWith("hohocamping://back?url=")) {
            m_oInstance.finish()
            return true
        } else if (url != null && url.startsWith("openurl://new?url=")) {
            var intent = Intent(m_oInstance, PopupActivity::class.java)
            intent.putExtra("url", url.replace("openurl://new?url=", ""))
            m_oInstance.startActivityForResult(intent, 1)
            return true
        }
        return false
    }

    override fun shouldOverrideUrlLoading(view: WebView, url:String) : Boolean {

        if (overrideUrlLoading(view, url)) {
            return true
        }
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) : Boolean {
        if (overrideUrlLoading(view, view.url!!)) {
            return true
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        if (isMain == true) {
            //view!!.visibility = INVISIBLE
        }
        customAnimationDialog.show()
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        if (isMain == true) {
            //view!!.visibility = VISIBLE
        }

        view?.let {
            it.clearHistory()
        }

        if (WEB_URL + "/" == url) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                token?.let {
                    Log.d("MainActivity Token", it)
                    Constants.PUTH_TOKEN = it
                    val handler = Handler()
                    handler.post(Runnable {
                        view?.loadUrl("javascript:regAndroidPushToken('" + Constants.PUTH_TOKEN + "')")
                    })
                }
            })
        }

        customAnimationDialog.hide()
    }

    override fun onReceivedError(
        view: WebView,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)

        customAnimationDialog.hide()

        view.destroy()
        val intent = Intent(
            m_oInstance,
            NetworkNotConnectionActivity::class.java
        )
        m_oInstance.startActivity(intent)
    }
}