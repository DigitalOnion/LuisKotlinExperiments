package com.outerspace.kotlinexperiments.experiments

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.outerspace.kotlinexperiments.ExperimentInterface
import com.outerspace.kotlinexperiments.MainInterface
import java.io.InputStream


const val EXPERIMENT_4_ASSET_NAME = "experiment4.txt"

class FraudulentActivityHackerrank: ExperimentInterface {
    override fun executeExperiment(example: String, liveResult: MutableLiveData<String>, activity: FragmentActivity) {

        val main = activity as MainInterface
        main.liveUri.observe(activity as LifecycleOwner) {
            uri ->
            if (uri == null) return@observe
            Log.d("Luis", uri.toString())
            var startAt = System.currentTimeMillis()

            var result: Int?

            // experiment starts
            val inputStream: InputStream = activity.
                applicationContext.contentResolver.openInputStream(uri) ?: throw(Exception("Invalid file"))

            startAt = System.currentTimeMillis()

            val reader = inputStream.bufferedReader()
            val headerLine: String = reader.readLine()          // The first line contains two space-separated integers n and d, the number of days of transaction data, and the number of trailing days' data used to calculate median spending respectively.
            val expenditureLine: String = reader.readLine()     // The second line contains n space-separated non-negative integers where each integer i denotes expenditure[i].

            val headers = headerLine.trimEnd().split(" ")
            val n = headers[0].toInt()
            val d = headers[1].toInt()
            val e1 = expenditureLine.trimEnd().split(" ")
            val e2 = mutableListOf<Int>()
            var idx = 0
            val chunkSize = 50
            while (idx < n) {
                val limitedIdx = if (idx+chunkSize >= e1.size) e1.size else idx + chunkSize
                val e3 = e1.subList(idx, limitedIdx).map {it.toInt()}
                e2.addAll(e3)
                Log.d("LUIS", "Chunk from $idx to ${idx+chunkSize-1}")
                idx += chunkSize
            }
            val expenditure = e2.toTypedArray()
            inputStream.close()

            result = activityNotifications(expenditure, d)

            // experiment ends
            val endAt = System.currentTimeMillis()

            val sb = StringBuilder()
            sb.appendLine("URI:")
                .appendLine("    ${uri.toString()}")
                .appendLine("Result:")
                .appendLine("    ${if (result == null) "Error while executing experiment 4"
                                            else "$result"}")
                .appendLine("")
                .appendLine("Elapsed time: ${(endAt - startAt).toFloat()/1000F} seconds")
            liveResult.value = sb.toString()
        }
        main.resultReg.launch(arrayOf("text/plain"))
    }

    fun Array<Int>.median(): Float {
        return if (this.size % 2 == 0) {
            val midIdx = this.size / 2
            (this[midIdx] + this[midIdx-1]).toFloat() / 2.0F
        } else {
            this[this.size/2].toFloat()
        }
    }

    fun activityNotifications(expenditure: Array<Int>, d: Int): Int {
        var count = 0
        val aQueue = Array(d) { expenditure[it] } .sortedArray()
        val dIsEven = d % 2 == 0
        val midIdx = d / 2
        for(idx in d..<expenditure.size) {
            val aMedian = if (dIsEven) {
                (aQueue[midIdx] + aQueue[midIdx-1]).toFloat() / 2.0F
            } else {
                aQueue[midIdx].toFloat()
            }

            val dayExpense = expenditure[idx].toFloat()
            if (dayExpense >= aMedian + aMedian) count ++

            var idxRemove = aQueue.indexOf(expenditure[idx - d])
            var idxRemoveP = idxRemove - 1
            var idxRemoveN = idxRemove + 1
            val aExpense = expenditure[idx]
            aQueue[idxRemove] = aExpense

            if (idxRemove > 0 && aQueue[idxRemoveP] > aQueue[idxRemove] ) {
                while (idxRemove > 0 && aQueue[idxRemoveP] > aExpense) {
                    aQueue[idxRemove] = aQueue[idxRemoveP]
                    idxRemove = idxRemoveP
                    idxRemoveP--
                }
                aQueue[idxRemove] = aExpense
            } else if(aExpense > aQueue[idxRemoveN]) {
                while (idxRemove < aQueue.size-1 && aExpense > aQueue[idxRemoveN]) {
                    aQueue[idxRemove] = aQueue[idxRemoveN]
                    idxRemove = idxRemoveN
                    idxRemoveN ++
                }
                aQueue[idxRemove] = aExpense
            }
//            Log.d("LUIS", "sorted aQueue = ${aQueue.fold("") {acc, it -> "$acc $it" }}")
        }

        return count
    }
}
