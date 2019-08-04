package tran.nam.core.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import tran.nam.core.R

abstract class BaseActivity : AppCompatActivity() {

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private var mLoadingDialog: LoadingDialog? = null

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun layoutId(): Int

    protected open fun setStatusBar() {}


    protected open fun initLayout() {}

    /*
     * Init injection for activity
     **/
    protected open fun inject() {}

    open fun init(savedInstanceState: Bundle?) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBar()
        inject()
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initLayout()
        mLoadingDialog = LoadingDialog(this)
        init(savedInstanceState)
    }

    fun showLoadingDialog() {
        mLoadingDialog?.run {
            if (!isShowing())
                showDialog()
        }
    }

    fun hideLoadingDialog() {
        mLoadingDialog?.run {
            if (isShowing())
                hideDialog()
        }
    }

    fun hideKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    fun showKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    open fun alert(message: String?, codeError: Int? = null, callbackAlertError: ((Int?) -> Unit?)? = null) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(message)
        alertDialog.setOnCancelListener {
            callbackAlertError?.invoke(codeError)
        }
        alertDialog.setPositiveButton(
                getString(R.string.text_ok)
        ) { dialog, _ ->
            run {
                callbackAlertError?.invoke(codeError)
                dialog.dismiss()
            }
        }

        alertDialog.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoadingDialog?.cancelDialog()
    }
}