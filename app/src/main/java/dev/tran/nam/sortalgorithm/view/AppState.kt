package dev.tran.nam.sortalgorithm.view


import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dev.tran.nam.sort.algorithm.BuildConfig
import nam.tran.architechture.di.component.AppComponent
import nam.tran.architechture.di.component.DaggerAppComponent
import tran.nam.Logger
import javax.inject.Inject
import androidx.multidex.MultiDex

class AppState : Application(), Application.ActivityLifecycleCallbacks,
        HasActivityInjector {

    companion object {
        val isAdmod = true
        private var TIME = 0
        fun isShowAd(): Boolean {
            TIME++
            return TIME % 5 == 0 && isAdmod
        }
    }

    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null
        @Inject set

    private var appComponent: AppComponent? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
        }
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent!!.inject(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        Logger.enter("onActivityCreated : " + activity.componentName)
    }

    override fun onActivityStarted(activity: Activity) {
        Logger.enter("onActivityStarted : " + activity.componentName)
    }

    override fun onActivityResumed(activity: Activity) {
        Logger.enter("onActivityResumed : " + activity.componentName)
    }

    override fun onActivityPaused(activity: Activity) {
        Logger.enter("onActivityPaused : " + activity.componentName)
    }

    override fun onActivityStopped(activity: Activity) {
        Logger.enter("onActivityStopped : " + activity.componentName)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
        Logger.enter("onActivitySaveInstanceState : " + activity.componentName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Logger.enter("onActivityDestroyed : " + activity.componentName)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }
}