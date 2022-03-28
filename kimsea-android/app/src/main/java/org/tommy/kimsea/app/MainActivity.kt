package org.tommy.kimsea.app

import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tommy.kimsea.app.client.WebChromeClientEx
import org.tommy.kimsea.app.client.WebViewClientEx
import org.tommy.kimsea.app.configs.Constants

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.homeBtn).setTextColor(Color.WHITE)
        findViewById<Button>(R.id.tradeBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.myBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.walletBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.notiBtn).setTextColor(Color.GRAY)

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

        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        webView.settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        webView.settings.setEnableSmoothTransition(true)
        webView.settings.setAppCacheEnabled(true)
        try {
            webView.settings.userAgentString = webView.settings.userAgentString.replace("; wv", "").replace("Version/", "") + " APP_WEBVIEW"
        } catch (ex:Exception) {
            webView.settings.userAgentString = webView.settings.userAgentString + " APP_WEBVIEW"
        }
        //webView.settings.userAgentString ="Mozilla/5.0 (Linux; Android 10; SM-G973N Build/QP1A.190711.020) AppleWebKit/537.36 (KHTML, like Gecko) 4.0 Chrome/81.0.4044.138 Mobile Safari/537.36" + " HOHOCAMPING_WEBVIEW"
        //Mozilla/5.0 (Linux; Android 10; SM-G973N Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36
        //"Mozilla/5.0 (Linux; Android 10; SM-G973N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.127 Mobile Safari/537.36"

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        webView.loadUrl(Constants.WEB_URL)

        webView.webViewClient = WebViewClientEx(this, true)
        webView.webChromeClient = WebChromeClientEx(this)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        (webView.webChromeClient as WebChromeClientEx).onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onKeyDown(keyCode:Int, event: KeyEvent) : Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            } else {
                var _this = this
                AlertDialog.Builder(this).setTitle("KIMSEA").setMessage("종료하시겠습니까?").setPositiveButton("종료") { _: DialogInterface, _: Int ->
                    CookieManager.getInstance().flush()
                    _this.moveTaskToBack(true)
                    _this.finishAndRemoveTask()
                    android.os.Process.killProcess(android.os.Process.myPid())
                }.setNegativeButton("취소", null).show()
                return false
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    fun onNavGoClick(view: View) {
        findViewById<Button>(R.id.homeBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.tradeBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.myBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.walletBtn).setTextColor(Color.GRAY)
        findViewById<Button>(R.id.notiBtn).setTextColor(Color.GRAY)

        when(view.id) {
            R.id.homeBtn -> {
                findViewById<Button>(R.id.homeBtn).setTextColor(Color.WHITE)
                webView.loadUrl(Constants.WEB_URL)
            }
            R.id.tradeBtn -> {
                findViewById<Button>(R.id.tradeBtn).setTextColor(Color.WHITE)
                webView.loadUrl(Constants.WEB_URL + "/home#/assets/ALL")
            }
            R.id.myBtn -> {
                findViewById<Button>(R.id.myBtn).setTextColor(Color.WHITE)
                webView.loadUrl(Constants.WEB_URL + "/home#/myassets/ALL")
            }
            R.id.walletBtn -> {
                findViewById<Button>(R.id.walletBtn).setTextColor(Color.WHITE)
                webView.loadUrl(Constants.WEB_URL + "/home#/mywallets")
            }
            R.id.notiBtn -> {
                findViewById<Button>(R.id.notiBtn).setTextColor(Color.WHITE)
                webView.loadUrl(Constants.WEB_URL + "/home#/notices")
            }
            else -> webView.loadUrl(Constants.WEB_URL)
        }
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
}