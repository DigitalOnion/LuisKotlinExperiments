package com.outerspace.kotlinexperiments.experiments

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.outerspace.kotlinexperiments.ExperimentInterface
import com.outerspace.kotlinexperiments.MainInterface
import java.io.InputStream


const val EXPERIMENT_3_ASSET_NAME = "experiment3.txt"

class ArrayManipulationHackerrank: ExperimentInterface {
    override fun executeExperiment(example: String, liveResult: MutableLiveData<String>, activity: FragmentActivity) {

        val main = activity as MainInterface
        main.liveUri.observe(activity as LifecycleOwner) {
            uri ->
            if (uri == null) return@observe
            Log.d("Luis", uri.toString())
            val startAt = System.currentTimeMillis()

            // experiment starts
            val queries = getQueries(uri!!, activity.applicationContext)
            val result = arrayManipulation(queries.nZeroes, queries.data.toTypedArray())
            // experiment ends

            val endAt = System.currentTimeMillis()
            val sb = StringBuilder()
            sb.appendLine("URI:")
                .appendLine("    ${uri.toString()}")
                .appendLine("Result:")
                .appendLine("    $result")
                .appendLine("")
                .appendLine("Elapsed time: ${(endAt - startAt).toFloat()/1000F} seconds")
            liveResult.value = sb.toString()
        }
        main.resultReg.launch(arrayOf("text/plain"))
    }

//    private fun arrayManipulation(n: Int, queries: Array<Array<Int>>): Long {
//        val zeroArr = Array(n+1) { 0L }
//        for(q in queries) {
//            (q[0]..q[1]).forEach { zeroArr[it] = zeroArr[it] + q[2] }
//        }
//        val max = zeroArr.max()
//        return max
//    }

//    private fun arrayManipulation(n: Int, queries: Array<Array<Int>>): Long {
//        var max = Long.MIN_VALUE
//        for (idx in 1..n) {
//            val total = queries.fold(0L) { acc: Long, q: Array<Int> -> acc + if (q[0] <= idx && idx <= q[1]) q[2] else 0 }
//            if (total > max) max = total
//        }
//        return max
//    }

    private fun arrayManipulation(n: Int, queries: Array<Array<Int>>): Long {
        val udArray = Array(n+2) { 0 }
        for(q in queries) {
            udArray[q[0]] = udArray[q[0]] + q[2]
            udArray[q[1]+1] = udArray[q[1]+1] - q[2]
        }
        var running = 0L
        var max = Long.MIN_VALUE
        for(ud in udArray) {
            running += ud
            if (max < running) max = running
        }
        return max
    }

    private interface DataInterface {
        var nZeroes: Int
        var data: MutableList<Array<Int>>
    }

    private fun getQueries(uri: Uri, context: Context): DataInterface {
        val resultData = object:DataInterface {
            override var nZeroes = 0
            override var data: MutableList<Array<Int>> = MutableList(size = 0) { Array(size = 0) { 0 } }
        }
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        if (inputStream == null) return resultData
        val reader = inputStream.bufferedReader()
        val headLine: String = reader.readLine() // first line contains the number of zeroes and number of lines
        val head = headLine.split(" ").map { it.toInt()}
        resultData.nZeroes = head[0]

        var line = reader.readLine()
        while(line != null) {
            val dataArray = line.split(" ").map { it.toInt() }.toTypedArray()
            resultData.data.add(dataArray)
            line = reader.readLine()
        }
        return resultData
    }

}
