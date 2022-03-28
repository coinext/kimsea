package org.tommy.kimsea.app.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import android.widget.ImageView
import org.tommy.kimsea.app.R


class CustomAnimationDialog(context: Context) : Dialog(context) {
    private val c: Context
    private var imgLogo: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
    }

    override fun show() {
        super.show()
    }

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //window!!.setDimAmount(0f)
        setCanceledOnTouchOutside(false)
        c = context
    }

    override fun onKeyDown(keyCode:Int, event: KeyEvent) : Boolean {
        return false
    }
}