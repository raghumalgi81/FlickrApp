package com.assignment.cardinalhealth.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assignment.cardinalhealth.di.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    // FOR DATA
    @Inject lateinit var viewModelFactory: ViewModelFactory

    // --- LIFECYCLE METHODS ---
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutById())
        configureDagger()
        configureDesign()
    }

    // --- DEPENDENCIES INJECTION ---
    private fun configureDagger() = (this.application as BaseApplication).appComponent.inject(this)

    // --- ABSTRACT METHODS ---
    abstract fun getLayoutById(): Int
    abstract fun configureDesign()
}