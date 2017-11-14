package ru.spbau.mit

import ru.spbau.mit.evaluation.MutableContext
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val test1Path = "testSources/test2.la"

    val s = String(Files.readAllBytes(Paths.get(test1Path)))

    val interpreter: Interpreter = Interpreter()
    val root = interpreter.parse(s)
    root.evaluate(MutableContext(null))
}
