package tran.nam.core.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import tran.nam.Logger
import java.lang.ref.WeakReference

open class BaseViewModel : ViewModel(), LifecycleObserver {

    @Volatile
    var mViewLoadingWeakReference: WeakReference<IViewLifecycle>? = null

    protected inline fun <reified V : IViewLifecycle> view(): V? {
        if (mViewLoadingWeakReference == null || mViewLoadingWeakReference?.get() == null)
            return null
        return V::class.java.cast(mViewLoadingWeakReference?.get())
    }

    open fun onInitialized() {}

    fun onAttach(viewLoading: IViewLifecycle) {
        Logger.w("BaseViewModel : onAttach()")
        mViewLoadingWeakReference = WeakReference(viewLoading)
        viewLoading.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreated() {
        Logger.w("BaseViewModel : onCreated()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        Logger.w("BaseViewModel : onStart()")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        Logger.w("BaseViewModel : onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        Logger.w("BaseViewModel : onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        Logger.w("BaseViewModel : onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy() {
        Logger.w("BaseViewModel : onDestroy()")
        val viewWeakReference = this.mViewLoadingWeakReference
        if (viewWeakReference != null) {
            val view = viewWeakReference.get()
            view?.lifecycle?.removeObserver(this)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun onSaveState(state: Bundle) {}

    @Suppress("UNUSED_PARAMETER")
    fun onRestoreState(state: Bundle?) {}
}
