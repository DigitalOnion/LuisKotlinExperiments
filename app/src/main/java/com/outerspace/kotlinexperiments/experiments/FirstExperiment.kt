package com.outerspace.kotlinexperiments.experiments

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.outerspace.kotlinexperiments.ExperimentInterface

typealias Stack<T> = MutableList<T>
fun <T> Stack<T>.push(item: T) = add(item)
fun <T> Stack<T>.pop(): T? = removeLastOrNull()

class FirstExperiment: ExperimentInterface {
    override fun executeExperiment(example: String, liveResult: MutableLiveData<String>, activity: FragmentActivity) {
        val seq = example.iterator()

        val stack: Stack<MutableList<Node>> = mutableListOf()
        var head: MutableList<Node> = mutableListOf()

        val otherCh = listOf(' ', ',')
        val sb = StringBuilder()
        var prevCh = ','

        val sbResult = StringBuilder()

        fun numberEnds() {
            if (prevCh in '0'..'9') {
                head.add(Node(sb.toString().toInt()))
            }
            sb.clear()
        }

        if (!seq.hasNext()) {
            liveResult.value = "Error: empty example"
            return
        }
        do {
            val ch = seq.next()

            when {
                ch == '[' -> {
                    stack.push(head)
                    head = mutableListOf()
                }
                ch == ']' -> {
                    numberEnds()
                    val prevHead = stack.pop()
                    prevHead?.add(Node(head))
                    if (prevHead == null) {
                        liveResult.value = "Error: expression is unbalanced"
                        return
                    }
                    head = prevHead
                }
                ch in '0'..'9' -> {
                    sb.append(ch)
                }
                otherCh.contains(ch) -> {
                    numberEnds()
                }
                else -> {
                    liveResult.value = "ERROR"
                    return
                }
            }
            prevCh = ch
        } while (seq.hasNext())

        printNodes(sbResult, head[0].list)
        liveResult.value = sbResult.toString()
    }

    private fun printNodes(sb: StringBuilder, root: MutableList<Node>) {
        root.forEach {node ->
            if (node.isValue) {
                sb.append("${node.value}, ")
            } else {
                sb.append('[')
                printNodes(sb, node.list)
                sb.append("], ")
            }
        }
    }
}

class Node () {
    var isValue: Boolean = false
    var value: Int = 0
    lateinit var list: MutableList<Node>

    constructor (value: Int): this() {
        this.isValue = true
        this.value = value
        this.list = mutableListOf()
    }

    constructor (list: MutableList<Node>): this() {
        this.isValue = false
        this.value = 0
        this.list = list
    }
}

