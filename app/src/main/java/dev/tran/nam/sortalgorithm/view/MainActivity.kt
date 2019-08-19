package dev.tran.nam.sortalgorithm.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd
import dev.tran.nam.sort.algorithm.BuildConfig
import dev.tran.nam.sort.algorithm.R
import dev.tran.nam.sortalgorithm.view.AppState.Companion.isShowAd
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import tran.nam.Logger
import tran.nam.core.view.BaseActivityInjection
import tran.nam.util.StatusBarUtil

class MainActivity : BaseActivityInjection() {

    private var mPublisherInterstitialAd: PublisherInterstitialAd? = null

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this,ContextCompat.getColor(this,R.color.lico_rice))
    }

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        val core = CrashlyticsCore.Builder().build()
        val kit = Crashlytics.Builder().core(core).build()
        Fabric.with(this, kit)

        val adRequest = PublisherAdRequest.Builder().addTestDevice("69A1FD045C96DBB40D272F2DDCB5E6B0").build()
        adView.loadAd(adRequest)

        mPublisherInterstitialAd = PublisherInterstitialAd(this)
        mPublisherInterstitialAd?.adUnitId = BuildConfig.InterstitialAd
        mPublisherInterstitialAd?.loadAd(PublisherAdRequest.Builder().build())

        mPublisherInterstitialAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {

            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Logger.debug(errorCode)

            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        }
    }

    fun showAdInterstitial() {
        if (mPublisherInterstitialAd?.isLoaded == true && isShowAd()) {
            mPublisherInterstitialAd?.show()
        }
    }

    fun showAdBanner(){
        adView.visibility = View.VISIBLE
    }
}
