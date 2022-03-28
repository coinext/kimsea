package org.tommy.kimsea.app.configs

import android.net.Uri

class Constants {
    companion object {
        var PUTH_TOKEN = ""
        val FILECHOOSER_NORMAL_REQ_CODE = 2001
        val FILECHOOSER_LOLLIPOP_REQ_CODE = 2002
        var mCapturedImageURI: Uri? = null

        //val WEB_URL = "file:///android_asset/view/index.html"
        val WEB_URL = "https://kimsea.me"
        //val WEB_URL = "http://192.168.0.37:8080"
    }
}