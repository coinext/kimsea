package org.tommy.kimsea.app

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Constants.MARKERS = FileUtil.readMarkers(this)


        checkPermissionsVerify()
    }

    fun start() {
        val intent = Intent(this, MainActivity::class.java)            // 실제 사용할 메인 액티비티
        startActivity(intent)
        finish()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissionsVerify() {
        if (    checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        )
        {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {

            }

            requestPermissions(
                arrayOf(
                    Manifest.permission.INTERNET, Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        else
        {
            start()
        }
    }

    override fun onRequestPermissionsResult(requestCode:Int, @NonNull permissions:Array<String>, @NonNull grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1)
        {
            /*grantResults.map {
                if (it == PackageManager.PERMISSION_DENIED)
                {

                    // 하나라도 거부한다면.
                    AlertDialog.Builder(this).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                        .setPositiveButton("종료"
                        ) { dialog, which ->
                            dialog.dismiss()
                            finish()
                        }.setNegativeButton("권한 설정"
                        ) { dialog, which ->
                            dialog.dismiss()
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.parse("package:" + applicationContext.packageName))
                            applicationContext.startActivity(intent)
                        }.setCancelable(false).show();

                    return;
                }
            }*/

            start()
        }
    }
}