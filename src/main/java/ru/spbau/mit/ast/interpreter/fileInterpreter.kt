package ru.spbau.mit.ast.interpreter

import ru.spbau.mit.parse
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path

fun interpretFile(file: Path, output: OutputStream, input: InputStream) {
    val source = String(Files.readAllBytes(file))
    val root = parse(source)

    val visitor = EvaluationVisitor(MutableContext(null, output, input))
    root.accept(visitor)
}