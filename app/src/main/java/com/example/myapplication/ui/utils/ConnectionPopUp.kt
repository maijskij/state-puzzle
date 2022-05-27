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

object ConnectionPopUp {

    private fun showToast(
        layoutInflater: LayoutInflater,
        activity: Activity,
        @ColorRes color: Int,
        @StringRes message: Int,
        showDuration: Int = Toast.LENGTH_LONG
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
            duration = showDuration
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
                Toast.LENGTH_SHORT
            )
            ConnectionState.CONNECTING -> showToast(
                layoutInflater,
                this,
                R.color.connecting,
                R.string.connecting,
                Toast.LENGTH_SHORT
            )
            ConnectionState.CONNECTION_ESTABLISHED -> showToast(
                layoutInflater,
                this,
                R.color.connected,
                R.string.connection_established,
                Toast.LENGTH_SHORT
            )
        }
    }

}