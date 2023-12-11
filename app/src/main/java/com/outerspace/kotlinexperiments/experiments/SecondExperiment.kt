package com.outerspace.kotlinexperiments.experiments

import android.util.Log
import com.outerspace.kotlinexperiments.ExperimentInterface

// simple example
//val s1 = Element(listOf(Element(5), Element(3)))
//val sample = Element(listOf(Element(1), s1, Element(4)))

// complex example
// [2, 4, 8, [3, 9], [1, 0, [15, 30]], 1, 7, [5, [9, 4, [15, 30], 2], 4], 8]
//
// [2, 4, 8, s1     , s3                , 1, 7, s6                      , 8]
// 		     [3, 9]   [1, 0, s2       ]         [5, s5 , 4]
// 		                     [15, 30]               [9, 4, s4      , 2]
// 		                                                   [15, 30]

val s1 = Element(listOf(Element(3), Element(9)))
val s2 = Element(listOf(Element(15), Element(30)))
val s3 = Element(listOf(Element(1), Element(0), s2))
val s4 = Element(listOf(Element(15), Element(30)))
val s5 = Element(listOf(Element(9), Element(4), s4, Element(2)))
val s6 = Element(listOf(Element(5), s5, Element(4)))
val sample = Element(listOf(Element(2), Element(4), Element(8), s1, s3, Element(1), Element(7), s6, Element(8)))

class SecondExperiment: ExperimentInterface {
    override fun executeExperiment(example: String): String {
        val sb = StringBuilder()

        sb.appendLine("------Recursive solution:")
        sb.appendLine(sample.flatIt())
        sb.appendLine()
        sb.appendLine()
        sb.appendLine("----Non Recursive solution:")
        sb.appendLine(flatElements(sample))
        return sb.toString()
    }
}

class Element (val hasValue: Boolean = true, val value: Int = 0, val list: List<Element> = listOf()) {
    constructor (value: Int): this(true, value)
    constructor (list: List<Element>): this(false, 0, list)

    fun flatIt(): List<Int> {
        if (hasValue) {
            return listOf(value)
        } else {
            val l:MutableList<Int> = mutableListOf()
            list.forEach {l.addAll(it.flatIt())}
            return l
        }
    }
}

fun flatElements(sample: Element): List<Int> {
    val stack: MutableList<MutableList<Element>> = mutableListOf()
    var rList: MutableList<Int> = mutableListOf()

    var sList = mutableListOf<Element>()
    sList.addAll(sample.list)
    do {
        val e = sList.removeFirst()
        if (e.hasValue) {
            rList.add(e.value)
        } else {
            if (sList.isNotEmpty()) stack.add(sList)
            sList = e.list.toMutableList()
        }
        if (sList.size == 0 && stack.size > 0) {
            sList = stack.removeLast()
            Log.d("LUIS", sList.toString())
        }
    } while (sList.size > 0 || stack.size > 0)
    return rList
}
