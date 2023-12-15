package com.company

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Day16 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day16().start()
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
        var result: Long = 0
        //       var = getInput("input_day15.txt");
        var input = getInput("input_day15_example.txt")
        println("++++++++++++++++++ $result +++++++++++++++++++")
    }
}