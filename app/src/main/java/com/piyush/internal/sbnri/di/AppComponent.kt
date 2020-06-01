package com.piyush.internal.sbnri.di

import android.app.Application
import com.piyush.internal.sbnri.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, StorageModule::class, ViewModelModule::class, CoroutineModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application, @BindsInstance activity: MainActivity): AppComponent
    }

}