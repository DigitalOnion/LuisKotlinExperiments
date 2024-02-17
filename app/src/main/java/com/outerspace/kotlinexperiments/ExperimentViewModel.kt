package com.outerspace.kotlinexperiments

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.outerspace.kotlinexperiments.experiments.LexicographicAnalysis
import com.outerspace.kotlinexperiments.experiments.FraudulentActivityHackerrank
import com.outerspace.kotlinexperiments.experiments.FlattenMultilevelArray
import com.outerspace.kotlinexperiments.experiments.ArrayManipulationHackerrank
import com.outerspace.kotlinexperiments.experiments.PlayingWithTreesAndStacks

class ExperimentViewModel: ViewModel() {

    val mutableExperimentResult = MutableLiveData<String>()

    private val experiments: List<ExperimentInterface> = listOf(
        LexicographicAnalysis(),
        FlattenMultilevelArray(),
        ArrayManipulationHackerrank(),
        FraudulentActivityHackerrank(),
        PlayingWithTreesAndStacks()
    )

    fun runExperiment(expIdx: Int = 0, example: String = "", activity: FragmentActivity) {
        val exp = experiments[expIdx]
        exp.executeExperiment(example, mutableExperimentResult, activity)
    }
}

