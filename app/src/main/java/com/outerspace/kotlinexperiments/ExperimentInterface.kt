package com.outerspace.kotlinexperiments

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData

interface ExperimentInterface {
    fun executeExperiment(example: String, liveResult: MutableLiveData<String>, activity: FragmentActivity)
}