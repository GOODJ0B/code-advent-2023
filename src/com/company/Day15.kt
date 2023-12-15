package com.company

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Day15 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day15().start()
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
        var result: Int = 0
        var input = getInput("input_day15.txt")
//        var input = getInput("input_day15_example.txt")
            .flatMap { line -> line.split(",") }

        val boxes = mutableListOf<MutableList<Pair<String, Int>>>()
        for (i in 0..255) {
            boxes.add(mutableListOf())
        }

        for (item in input) {
            if (item.contains('-')) {
                val label = item.substring(0, item.length - 1)
                val index = calculateHash(label)
                boxes[index].removeIf { p -> p.first == label }
            } else {
                val split = item.split('=')
                val label = split[0]
                val focal = split[1].toInt()
                val index = calculateHash(label)
                val pair = label to focal
                boxes[index] = boxes[index].map { p ->
                    if (p.first == label) pair else p
                }.toMutableList()
                if (boxes[index].none() { p -> p == pair }) {
                    boxes[index].add(pair)
                }
            }
        }

        for (box in 0..255) {
            for ((i, lens) in boxes[box].withIndex()) {
                result += (box + 1) * (i + 1) * lens.second
            }
        }

        println("++++++++++++++++++ $result +++++++++++++++++++")
    }

    fun calculateHash(input: String): Int {
        var currentValue = 0

        for (char in input) {
            val asciiCode = char.toInt()
            currentValue += asciiCode
            currentValue = currentValue * 17 % 256
        }

        return currentValue
    }

}