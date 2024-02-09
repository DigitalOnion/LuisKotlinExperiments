package com.outerspace.kotlinexperiments

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.outerspace.kotlinexperiments.experiments.FirstExperiment
import com.outerspace.kotlinexperiments.experiments.FourthExperiment
import com.outerspace.kotlinexperiments.experiments.SecondExperiment
import com.outerspace.kotlinexperiments.experiments.ThirdExperiment

class ExperimentViewModel: ViewModel() {

    val mutableExperimentResult = MutableLiveData<String>()

    private val experiments: List<ExperimentInterface> = listOf(
        FirstExperiment(),
        SecondExperiment(),
        ThirdExperiment(),
        FourthExperiment(),
    )

    fun runExperiment(expIdx: Int = 0, example: String = "", activity: FragmentActivity) {
        val exp = experiments[expIdx]
        exp.executeExperiment(example, mutableExperimentResult, activity)
    }
}

