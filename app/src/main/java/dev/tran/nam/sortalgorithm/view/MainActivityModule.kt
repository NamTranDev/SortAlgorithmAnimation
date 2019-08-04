package dev.tran.nam.sortalgorithm.view

import androidx.appcompat.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import tran.nam.core.di.inject.PerActivity

/**
 * Provides main activity dependencies.
 */
@Module
abstract class MainActivityModule {

    @Binds
    @PerActivity
    internal abstract fun activity(activity: MainActivity): AppCompatActivity
}