package com.company

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Day14 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Day14().start()
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
        //        List<String> input = getInput("input_day14.txt");
        val input = getInput("input_day14_example.txt")
        println("++++++++++++++++++ $result +++++++++++++++++++")
    }
}