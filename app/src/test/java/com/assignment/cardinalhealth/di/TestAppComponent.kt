package com.assignment.cardinalhealth.di

import dagger.Component
import com.assignment.cardinalhealth.base.BaseTest
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, ViewModelModule::class, TestRxJavaModule::class])
interface TestAppComponent {
    fun inject(baseTest: BaseTest)
}