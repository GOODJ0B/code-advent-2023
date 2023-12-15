package com.company

import java.nio.file.Files
import java.nio.file.Paths

class Day14 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day14().start()
        }

        fun getInput(filename: String?): MutableList<CharArray> {
            val path = Paths.get(filename)
            return Files.readAllLines(path).stream().map(String::toCharArray).toList()
        }
    }

    fun List<CharArray>.print() {
        for (chars in this) {
            println(chars)
        }
        println()
    }

    fun List<CharArray>.toStringForSet(): String {
        val builder = StringBuilder()
        for (line in this) {
            builder.append(line)
            builder.append("\n")
        }
        return builder.toString()
    }

    fun start() {
        val start = System.currentTimeMillis()
        var result: Long = 0
        var input = getInput("input_day14.txt");
//        var input = getInput("input_day14_example.txt")
//        input.print()

        val known = mutableMapOf(input.toStringForSet() to 1)
        val cycles: MutableList<MutableList<CharArray>> = mutableListOf(input.map { it.copyOf() }.toMutableList())
        var firstHit = -1;
        var lastHit = 0;

        for (cycle in 0 until 1000000000) {
            cycle(input)

//            println("CYCLE: " + (cycle))
//            input.print()
            cycles.add(input.map { it.copyOf() }.toMutableList())
            if (known.containsKey(input.toStringForSet())) {
                println("Cycle ${known.get(input.toStringForSet())} is the same as cycle $cycle")
                firstHit = known.get(input.toStringForSet())!!
                lastHit = cycle
                var loopLength = lastHit - firstHit
                input = cycles.get(((1000000000 - firstHit) % loopLength) + firstHit)
                break


            } else {
                known.put(input.toStringForSet(), cycle)
            }
//            println("----------------------------")
        }

//        input.print()
        // calculate load:
        input = rotateMatrixClockwise(input); // To point north
        for (line in input) {
            var lineTotal = 0
            for ((i, c) in line.withIndex()) {
                when (c) {
                    'O' -> {
                        lineTotal += i + 1
                    }
                }
            }
//            println(lineTotal)
            result += lineTotal
        }

        println("++++++++++++++++++ $result +++++++++++++++++++")
        println("Done in ${System.currentTimeMillis() - start}")
    }

    private fun cycle(input: MutableList<CharArray>) {
        // To North
        for (i in 0 until input[0].size) {
            var rollingRocks = 0
            for (lineIndex in (input.size - 1) downTo 0) {
                when (input[lineIndex][i]) {
                    '#' -> {
                        for (rock in 1..rollingRocks) {
                            input[lineIndex + rock][i] = 'O'
                        }
                        rollingRocks = 0
                    }
                    'O' -> {
                        input[lineIndex][i] = '.'
                        rollingRocks += 1
                    }
                }
            }
            for (rock in 0 until rollingRocks) {
                input[rock][i] = 'O'
            }
        }
        // To West
        for (line in input) {
            var rollingRocks = 0
            for ((i, c) in line.withIndex().reversed()) {
                when (c) {
                    '#' -> {
                        for (rock in 1..rollingRocks) {
                            line[i + rock] = 'O'
                        }
                        rollingRocks = 0
                    }
                    'O' -> {
                        line[i] = '.'
                        rollingRocks += 1
                    }
                }
            }
            for (rock in 0 until rollingRocks) {
                line[rock] = 'O'
            }
        }
        // To South
        for (i in 0 until input[0].size) {
            var rollingRocks = 0
            for (lineIndex in 0 until input.size) {
                val c = input[lineIndex][i]
                when (c) {
                    '#' -> {
                        for (rock in 1..rollingRocks) {
                            input[lineIndex - rock][i] = 'O'
                        }
                        rollingRocks = 0
                    }
                    'O' -> {
                        input[lineIndex][i] = '.'
                        rollingRocks += 1
                    }
                }
            }
            for (rock in 1..rollingRocks) {
                input[input.size - rock][i] = 'O'
            }
        }
        // To East
        for (line in input) {
            var rollingRocks = 0
            for ((i, c) in line.withIndex()) {
                when (c) {
                    '#' -> {
                        for (rock in 1..rollingRocks) {
                            line[i - rock] = 'O'
                        }
                        rollingRocks = 0
                    }
                    'O' -> {
                        line[i] = '.'
                        rollingRocks += 1
                    }
                }
            }
            for (rock in 1..rollingRocks) {
                line[line.size - rock] = 'O'
            }
        }
    }

    fun rotateMatrixClockwise(originalMatrix: List<CharArray>): MutableList<CharArray> {
        val numRows = originalMatrix.size
        val numCols = originalMatrix[0].size

        // Create a new matrix with swapped rows and columns
        val rotatedMatrix = MutableList(numCols) { CharArray(numRows) }

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                rotatedMatrix[j][numRows - 1 - i] = originalMatrix[i][j]
            }
        }

        return rotatedMatrix
    }

    fun rotateMatrixCounterclockwise(originalMatrix: List<CharArray>): MutableList<CharArray> {
        val numRows = originalMatrix.size
        val numCols = originalMatrix[0].size

        // Create a new matrix with swapped rows and columns
        val rotatedMatrix = MutableList(numCols) { CharArray(numRows) }

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                rotatedMatrix[numCols - 1 - j][i] = originalMatrix[i][j]
            }
        }

        return rotatedMatrix
    }

}


