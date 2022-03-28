package org.tommy.kimsea.app.client

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import org.tommy.kimsea.app.configs.Constants
import java.io.File


class WebChromeClientEx(val m_oInstance: AppCompatActivity) : WebChromeClient() {

    var filePathCallbackNormal: ValueCallback<Uri>? = null
    var filePathCallbackLollipop: ValueCallback<Array<Uri>>? = null
    var cameraImageUri: Uri? = null

    // For Android < 3.0
    fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
        Log.d("MainActivity", "3.0 <")
        openFileChooser(uploadMsg, "")
    }

    // For Android 3.0+
    fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType:String) {
        Log.d("MainActivity", "3.0+")
        filePathCallbackNormal = uploadMsg
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        m_oInstance.startActivityForResult(Intent.createChooser(i, "File Chooser"), Constants.FILECHOOSER_NORMAL_REQ_CODE)
    }

    // For Android 4.1+
    fun openFileChooser(uploadMsg:ValueCallback<Uri>, acceptType:String, capture:String) {
        Log.d("MainActivity", "4.1+")
        openFileChooser(uploadMsg, acceptType)
    }

    // For Android 5.0+
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
            webView: WebView, filePathCallback:ValueCallback<Array<Uri>> ,
            fileChooserParams:FileChooserParams) :Boolean {
        Log.d("MainActivity", "5.0+")

        // Callback 초기화 (중요!)
        if (filePathCallbackLollipop != null) {
            filePathCallbackLollipop!!.onReceiveValue(null)
            filePathCallbackLollipop = null
        }
        filePathCallbackLollipop = filePathCallback

        runCamera(fileChooserParams.isCaptureEnabled)
        return true
    }

    fun runCamera(_isCapture:Boolean) {
        /*if (!_isCapture) {// 갤러리 띄운다.
            val pickIntent = Intent(Intent.ACTION_PICK);
            pickIntent.type = MediaStore.Images.Media.CONTENT_TYPE
            pickIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val pickTitle = "사진 가져올 방법을 선택하세요."
            val chooserIntent = Intent.createChooser(pickIntent, pickTitle);

            m_oInstance.startActivityForResult(chooserIntent, Constants.FILECHOOSER_LOLLIPOP_REQ_CODE)
            return
        }*/

        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        val path = m_oInstance.getFilesDir()
        val file = File(path, System.currentTimeMillis().toString() + "fokCamera.png")
        // File 객체의 URI 를 얻는다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            val strpa = m_oInstance.applicationContext.packageName
            cameraImageUri = FileProvider.getUriForFile(m_oInstance, m_oInstance.applicationContext.packageName + ".fileprovider", file)
        }
        else
        {
            cameraImageUri = Uri.fromFile(file);
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);

        if (!_isCapture)
        { // 선택팝업 카메라, 갤러리 둘다 띄우고 싶을 때..
            val pickIntent = Intent(Intent.ACTION_PICK);
            pickIntent.type = MediaStore.Images.Media.CONTENT_TYPE
            pickIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val chooserIntent = Intent.createChooser(pickIntent, "사진 가져올 방법을 선택하세요.")

            // 카메라 intent 포함시키기..
            var parcelables = arrayOf<Parcelable>(intentCamera)
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, parcelables)

            m_oInstance.startActivityForResult(chooserIntent, Constants.FILECHOOSER_LOLLIPOP_REQ_CODE)
        }
        else
        {// 바로 카메라 실행..
            m_oInstance.startActivityForResult(intentCamera, Constants.FILECHOOSER_LOLLIPOP_REQ_CODE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode)
        {
            Constants.FILECHOOSER_NORMAL_REQ_CODE ->
                if (resultCode == AppCompatActivity.RESULT_OK)
                {
                    if (filePathCallbackNormal == null) return
                    val result = if (data == null || resultCode != AppCompatActivity.RESULT_OK) cameraImageUri else data.data
                    filePathCallbackNormal!!.onReceiveValue(result);
                    filePathCallbackNormal = null
                }

            Constants.FILECHOOSER_LOLLIPOP_REQ_CODE ->
                if (resultCode == AppCompatActivity.RESULT_OK)
                {
                    if (filePathCallbackLollipop == null) return
                    if (data == null) {
                        var _data = Intent()
                        _data.setData(cameraImageUri)
                        filePathCallbackLollipop!!.onReceiveValue(FileChooserParams.parseResult(resultCode, _data))
                    } else {
                        filePathCallbackLollipop!!.onReceiveValue(FileChooserParams.parseResult(resultCode, data))
                    }

                    filePathCallbackLollipop = null;
                }
                else
                {
                    if (filePathCallbackLollipop != null)
                    {
                        filePathCallbackLollipop!!.onReceiveValue(null);
                        filePathCallbackLollipop = null;
                    }

                    if (filePathCallbackNormal != null)
                    {
                        filePathCallbackNormal!!.onReceiveValue(null);
                        filePathCallbackNormal = null;
                    }
                }

            else -> {

            }
        }
    }

}