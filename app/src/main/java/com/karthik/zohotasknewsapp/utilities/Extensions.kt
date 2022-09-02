package com.karthik.zohotasknewsapp.utilities

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper

fun Context.showMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun TextSwitcher.addAnimTextView(duartion: Long, textStyle: Int) {
    this.setFactory {
        TextView(
            ContextThemeWrapper(
                this.context,
                textStyle
            ), null, 0
        )
    }

    val inAnim = AnimationUtils.loadAnimation(
        this.context,
        android.R.anim.slide_in_left
    )
    val outAnim = AnimationUtils.loadAnimation(
        this.context,
        android.R.anim.slide_out_right
    )
    inAnim.duration = duartion
    outAnim.duration = duartion

    this.inAnimation = inAnim
    this.outAnimation = outAnim
}