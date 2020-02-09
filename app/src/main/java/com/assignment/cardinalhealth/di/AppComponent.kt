package com.assignment.cardinalhealth.di

import dagger.Component
import com.assignment.cardinalhealth.base.BaseActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, ViewModelModule::class, RxJavaModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}