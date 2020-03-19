package com.wkl.components.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

object NavigationUtils {

    fun startActivity(activity: Activity, cls: Class<out Activity>) {
        startActivity(activity, cls, null)
    }

    fun startActivity(activity: Activity, cls: Class<out Activity>, bundle: Bundle?) {
        var intent = Intent(activity, cls::class.java)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    fun startActivityForResult(activity: Activity, cls: Class<out Activity>, requestCode: Int) {
        startActivityForResult(activity, cls, null, requestCode)
    }

    fun startActivityForResult(activity: Activity, cls: Class<out Activity>, bundle: Bundle?, requestCode: Int) {
        var intent = Intent(activity, cls::class.java)
        intent.putExtras(bundle)
        activity.startActivityForResult(intent, requestCode)
    }

    fun startActivity(fragment: Fragment, cls: Class<out Activity>, bundle: Bundle?) {
        var intent = Intent(fragment.requireContext(), cls::class.java)
        intent.putExtras(bundle)
        fragment.startActivity(intent)
    }

    fun startActivityForResult(fragment: Fragment, cls: Class<out Activity>, requestCode: Int) {
        startActivityForResult(fragment, cls, null, requestCode)
    }

    fun startActivityForResult(fragment: Fragment, cls: Class<out Activity>, bundle: Bundle?, requestCode: Int) {
        var intent = Intent(fragment.requireContext(), cls::class.java)
        intent.putExtras(bundle)
        fragment.startActivityForResult(intent, requestCode)
    }
}