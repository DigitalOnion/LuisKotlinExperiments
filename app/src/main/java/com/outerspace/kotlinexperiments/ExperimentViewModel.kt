package com.outerspace.kotlinexperiments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.outerspace.kotlinexperiments.experiments.FirstExperiment
import com.outerspace.kotlinexperiments.experiments.SecondExperiment

class ExperimentViewModel: ViewModel() {

    val mutableExperimentResult = MutableLiveData<String>()

    private val experiments: List<ExperimentInterface> = listOf(
        FirstExperiment(),
        SecondExperiment(),
    )

    fun runExperiment(expIdx: Int = 0, example: String = "") {
        val exp = experiments[expIdx]
        val res = exp.executeExperiment(example)
        mutableExperimentResult.value = res
    }
}

