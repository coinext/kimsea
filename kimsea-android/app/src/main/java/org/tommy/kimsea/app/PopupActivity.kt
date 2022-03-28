package org.tommy.kimsea.app

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.tommy.kimsea.app.client.WebChromeClientEx
import org.tommy.kimsea.app.client.WebViewClientEx

class PopupActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.let{it.hide()}

        //항상 webview위에 먼저 선언해야함.
        if (android.os.Build.VERSION.SDK_INT < 21) {
            CookieManager.getInstance().setAcceptCookie(true)
        }

        webView = findViewById(R.id.webview)
        webView.settings.apply {
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.settings.setAppCacheEnabled(true)
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

        var intent = getIntent()
        var url = intent.getStringExtra("url");
        webView.loadUrl(url!!)

        webView.webViewClient = WebViewClientEx(this, true)
        webView.webChromeClient = WebChromeClientEx(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onKeyDown(keyCode:Int, event: KeyEvent) : Boolean {
        CookieManager.getInstance().flush()

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        //webView.destroy()
        CookieManager.getInstance().flush()
        webView.stopLoading();
        val webParent = webView.parent as ViewGroup
        webParent.removeView(webView)
        webView.destroy()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (webView.webChromeClient as WebChromeClientEx).onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data);
    }
}