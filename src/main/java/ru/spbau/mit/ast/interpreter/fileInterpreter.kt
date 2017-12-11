package ru.spbau.mit.ast.interpreter

import ru.spbau.mit.parse
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path

fun interpretFile(file: Path, output: OutputStream) {
    val source = String(Files.readAllBytes(file))
    val root = parse(source)

    val visitor = EvaluationVisitor(MutableContext(null, output))
    root.accept(visitor)
}