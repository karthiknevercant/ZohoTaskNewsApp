package com.karthik.zohotasknewsapp.views

import android.R
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karthik.zohotasknewsapp.viewmodels.BaseViewModel

abstract class BaseActivity : AppCompatActivity() {
    lateinit var rl: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel().showLoader.observe(this){
            if (it)
                showLoader()
            else
                hideLoader()
        }

        getViewModel().showToastMsg.observe(this){
            it.let {
                this.showMessage(it)
            }
        }
    }

    fun showLoader() {
        if (!this::rl.isInitialized) {
            rl = RelativeLayout(this)
            rl.gravity = Gravity.CENTER

            val params = RelativeLayout.LayoutParams(100, 100)
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)

            val progressBar =
                ProgressBar(this, null, R.attr.progressBarStyleLargeInverse)

            rl.addView(progressBar, params)

            getRootView().addView(rl)

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    fun hideLoader() {
        if (this::rl.isInitialized) {
            getRootView().removeView(rl)

            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun getRootView(): ViewGroup {
        return window.decorView.findViewById(R.id.content)
    }

    fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun getViewModel() : BaseViewModel
}