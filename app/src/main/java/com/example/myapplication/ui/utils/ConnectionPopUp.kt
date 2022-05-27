package com.example.myapplication.ui.utils

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.data.ConnectionState

/*
*   ConnectionPopUp Toast generates error logs during the runtime,
*   E/Toast: setGravity() shouldn't be called on text toasts, the values won't be used
*   Hence, this popup implementation is not good long term solution. However I find it
*   as a very quick and efficient way to implement colorful popup "at the top" of the screen
*   as it was described in the challenge description.
* */

object ConnectionPopUp {

    private fun showToast(
        layoutInflater: LayoutInflater,
        activity: Activity,
        @ColorRes color: Int,
        @StringRes message: Int
    ): Toast {
        val layout: View =
            layoutInflater.inflate(
                R.layout.toast,
                activity.findViewById(R.id.toast_layout_root) as ViewGroup?
            )
        (layout.findViewById<View>(R.id.text) as TextView).apply {

            setTextColor(ContextCompat.getColor(context, color))
            text = activity.getString(message)
        }
        return Toast(activity.applicationContext).apply {
            setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
            duration = Toast.LENGTH_SHORT
            view = layout
        }
    }

    fun Activity.createConnectionStatePopUp(state: ConnectionState): Toast {
        return when (state) {
            ConnectionState.CONNECTION_ERROR -> showToast(
                layoutInflater,
                this,
                R.color.error,
                R.string.connection_error,
            )
            ConnectionState.CONNECTING -> showToast(
                layoutInflater,
                this,
                R.color.connecting,
                R.string.connecting,
            )
            ConnectionState.CONNECTION_ESTABLISHED -> showToast(
                layoutInflater,
                this,
                R.color.connected,
                R.string.connection_established,
            )
        }
    }

}