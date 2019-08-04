package tran.nam.core.view

import tran.nam.Logger
import java.lang.ref.WeakReference

/**
 * Created by vophamtuananh on 1/7/18.
 */
class WaitThread(fragment: BaseFragment) : Thread() {

    private var fragmentWeak: WeakReference<BaseFragment>? = null
    private var isStopped: Boolean = false
    private val mObject = Object()

    init {
        fragmentWeak = WeakReference(fragment)
    }

    override fun run() {
        val fragment = fragmentWeak!!.get()
        if (fragment != null) {
//            Logger.debug("Animation Fragment - fragment : $fragment")
//            Logger.debug("Animation Fragment - fragment.isViewCreated() : " + fragment.isViewCreated())
//            Logger.debug("Animation Fragment - isStopped : $isStopped")
            while (!fragment.isViewCreated() && !isStopped) {
//                Logger.debug("Animation Fragment - fragment.isViewCreated() : " + fragment.isViewCreated())
//                Logger.debug("Animation Fragment - isStopped : $isStopped")
                try {
                    Logger.debug("Animation Fragment : " + "run()")
                    synchronized(mObject) {
                        mObject.wait()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }

            if (isStopped)
                return

            val finalFragment = fragmentWeak?.get()
            finalFragment?.activity?.runOnUiThread {
                //                Logger.debug("Animation Fragment : " + "finalFragment.onInitialized()")
                finalFragment.onInitialized()
            }
        }
    }

    fun continueProcessing() {
        synchronized(mObject) {
            mObject.notifyAll()
        }
    }

    fun stopProcessing() {
//        Logger.debug("Animation Fragment - stopProcessing()")
        isStopped = true
        synchronized(mObject) {
            mObject.notifyAll()
        }
    }
}

