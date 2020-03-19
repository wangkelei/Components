package com.wkl.components.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wkl.components.R
import com.wkl.components.utils.StatusBarUtil

/**
 * Created by wangkelei on 2018/11/14.
 */
class StackActivity : EActivity() {
    private var mFragment: Fragment? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_stack
    }

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setColorWithStatusTextColor(this@StackActivity, Color.WHITE, 0, true)
        try {
            mFragment =
                    Class.forName(intent.getStringExtra(FRAGMENT_NAME)).newInstance() as Fragment
            if (intent.extras != null)
                mFragment!!.arguments = intent.extras
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, mFragment!!)
                    .commit()

        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }

    companion object {
        private val FRAGMENT_NAME = "fragment_name"

        fun start(context: Context, cls: Class<out Fragment>, bundle: Bundle?) {
            val starter = Intent(context, StackActivity::class.java)
            if (bundle != null)
                starter.putExtras(bundle)
            starter.putExtra(FRAGMENT_NAME, cls.name)
            context.startActivity(starter)
        }

        fun start(
                fragment: Fragment,
                cls: Class<out Fragment>,
                bundle: Bundle?,
                requestCode: Int
        ) {
            val starter = Intent(fragment.activity, StackActivity::class.java)
            if (bundle != null)
                starter.putExtras(bundle)
            starter.putExtra(FRAGMENT_NAME, cls.name)
            fragment.startActivityForResult(starter, requestCode)
        }
    }
}