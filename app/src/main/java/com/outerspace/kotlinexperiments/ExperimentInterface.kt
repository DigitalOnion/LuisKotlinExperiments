package com.outerspace.kotlinexperiments

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface ExperimentInterface {
    fun executeExperiment(example: String, liveResult: MutableLiveData<String>, activity: FragmentActivity)
}