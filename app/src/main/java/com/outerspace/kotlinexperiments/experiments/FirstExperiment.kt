package com.outerspace.kotlinexperiments.experiments

import com.outerspace.kotlinexperiments.ExperimentInterface

typealias Stack<T> = MutableList<T>
fun <T> Stack<T>.push(item: T) = add(item)
fun <T> Stack<T>.pop(): T? = removeLastOrNull()

class FirstExperiment: ExperimentInterface {
    override fun executeExperiment(example: String): String {
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

        if (!seq.hasNext()) return "Error: empty example"
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
                    head = prevHead ?: return "Error: expression is unbalanced"
                }
                ch in '0'..'9' -> {
                    sb.append(ch)
                }
                otherCh.contains(ch) -> {
                    numberEnds()
                }
                else -> {
                    return "ERROR"
                }
            }
            prevCh = ch
        } while (seq.hasNext())

        printNodes(sbResult, head[0].list)
        return sbResult.toString()
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

