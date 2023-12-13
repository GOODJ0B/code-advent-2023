package com.company

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Day13 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day13().start()
        }

        fun getInput(filename: String?): List<String> {
            val path = Paths.get(filename)
            return try {
                Files.readAllLines(path)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }

    fun start() {
        val result: Long = 0
        val input = getInput("input_day13.txt");
//        val input = getInput("input_day13_example.txt")
        3
        val grids: MutableList<MutableList<String>> = mutableListOf(mutableListOf())
        for (s in input) {
            if (s.isBlank()) {
                grids.add(mutableListOf())
                continue
            }
            grids.last().add(s);
        }

        var horizontals = 0;
        var verticals = 0;

        for (grid in grids) {
            println(grid)
            val horizontalLines = getHorizontalLines(grid)
            println("horizontal: $horizontalLines")
            val verticalColumns = getVerticalColumns(grid)
            println("vertical: $verticalColumns")
            horizontals += horizontalLines
            verticals += verticalColumns
        }

        println("++++++++++++++++++ ${horizontals * 100 + verticals} +++++++++++++++++++")
    }

    private fun getVerticalColumns(grid: List<String>): Int {
        return getHorizontalLines(rotateMatrixClockwise(grid))
    }

    private fun getHorizontalLines(grid: List<String>): Int {
        val pairs = mutableListOf<Int>()
        for ((i, s) in grid.subList(0, grid.count() - 1).withIndex()) {
            if (s.equals(grid[i + 1]) || s.equalsWithOneDifference(grid[i + 1])) {
                pairs.add(i)
            }
        }

        loop@ for (pairIndex in pairs.reversed()) {
            var smudgeFixed = false;
            for (i in 0..pairIndex) {
                val left = pairIndex - i
                val right = pairIndex + 1 + i

                if (grid.count() == right) {
                    if (smudgeFixed) {
                        return pairIndex + 1
                    } else {
                        continue@loop;
                    }
                }
                val l = grid[left]
                val r = grid[right]
                if (l != r) {
                    if (!smudgeFixed && l.equalsWithOneDifference(r)) {
                        smudgeFixed = true
                    } else {
                        continue@loop
                    }
                }
            }
            if (smudgeFixed) {
                return pairIndex + 1;
            }
        }

        return 0;
    }

    fun rotateMatrixClockwise(originalMatrix: List<String>): List<String> {
        val numRows = originalMatrix.size
        val numCols = originalMatrix[0].length

        // Create a new matrix with swapped rows and columns
        val rotatedMatrix = MutableList(numCols) { "" }

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                rotatedMatrix[j] = originalMatrix[numRows - 1 - i][j] + rotatedMatrix[j]
            }
        }

        return rotatedMatrix
    }

    fun String.equalsWithOneDifference(other: String): Boolean {
        if (this.length != other.length) {
            return false
        }

        var differences = 0

        for (i in this.indices) {
            if (this[i] != other[i]) {
                differences++
                if (differences > 1) {
                    return false
                }
            }
        }

        return differences == 1
    }
}