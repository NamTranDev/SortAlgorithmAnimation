package dev.tran.nam.sortalgorithm.di.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import dev.tran.nam.sortalgorithm.view.AppState
import dev.tran.nam.sortalgorithm.view.MainActivity
import dev.tran.nam.sortalgorithm.view.MainActivityModule
import tran.nam.core.di.inject.PerActivity
import javax.inject.Singleton

/**
 * Provides application-wide dependencies.
 */
@Module(includes = [AndroidSupportInjectionModule::class, ViewModelModule::class])
abstract class AppModule {

    @Binds
    @Singleton
    /*
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    internal abstract fun application(app: AppState): Application

    /**
     * Provides the injector for the [MainActivityModule], which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @dagger.android.ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun injectorMainActivity(): MainActivity
}
