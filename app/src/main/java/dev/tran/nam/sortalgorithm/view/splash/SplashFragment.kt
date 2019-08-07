package dev.tran.nam.sortalgorithm.view.splash

import android.os.Bundle
import android.os.Handler
import androidx.navigation.Navigation
import dev.tran.nam.sort.algorithm.R
import tran.nam.Logger
import tran.nam.core.view.BaseFragment

class SplashFragment : BaseFragment(){

    private var isGoToMain = false
    private var handler: Handler? = null
    private var runnable: Runnable? = Runnable {
        Logger.debug("gotoMain")
        isGoToMain = true
        gotoMain()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_splash
    }

    private fun gotoMain() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_splashFragment_to_mainFragment)
    }

    override fun onResume() {
        super.onResume()
        if (isGoToMain){
            gotoMain()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        handler = Handler()
        handler?.postDelayed(runnable!!, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable!!)
        handler = null
        runnable = null
    }

}