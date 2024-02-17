package com.outerspace.kotlinexperiments.experiments

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.outerspace.kotlinexperiments.ExperimentInterface

class PlayingWithTreesAndStacks: ExperimentInterface {
    override fun executeExperiment(
        example: String, liveResult: MutableLiveData<String>, activity: FragmentActivity
    ) {
        val sb = StringBuilder()

        data class Leaf(val element: Int, val left: Leaf? = null, val right: Leaf? = null) {
            fun mMax(): Int {
                val l = maxOf(left?.mMax() ?: element, element ) // if (left != null) maxOf(left.mMax(), element) else element
                val r = if (right != null) maxOf(right.mMax(), element) else element
                return maxOf(r, l)
            }

            fun rightMostAt(level: Int): Leaf? {
                return if (level == 0) this
                    else right?.rightMostAt(level - 1)
                        ?: (left?.rightMostAt(level - 1) ?: null)
            }

            fun rightMostMap(map: MutableMap<Int, Int>, level: Int = 0) {
                map[level] = element
                left?.rightMostMap(map, level -  1)
                right?.rightMostMap(map, level - 1)
            }

// this has a bug
//            fun highestRightMost(): Int? {
//                val b = left?.highestRightMost()
//                val c = right?.highestRightMost()
//                val hB = if (b != null && b > element) b else element
//                return if (c != null && c > hB) c else hB
//            }
        }

        val root = Leaf(3, Leaf(5, Leaf(15), Leaf(9, Leaf(7), Leaf(4))), Leaf(1))
        sb.appendLine("Max = ${root.mMax()}")
        sb.appendLine()

        var level = 0
        do {
            val rightMost = root.rightMostAt(level)?.element
            sb.appendLine("right Most at Level ${level++} : $rightMost")
        } while (rightMost != null)
        sb.appendLine()

        val map = mutableMapOf<Int, Int>()
        root.rightMostMap(map)

        sb.appendLine("Rightmost map values: ${map.values}")
        sb.appendLine()

//        sb.appendLine("highest: ${root.highestRightMost()}")
//        sb.appendLine(".")

        liveResult.value = sb.toString()
    }
}