package ru.spbau.mit.ast.printer

import ru.spbau.mit.ast.ASTEntity
import ru.spbau.mit.parse
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path

fun ASTEntity.toPrettyString(): String {
    val sb = StringBuilder()
    accept(PrinterVisitor(sb))
    return sb.toString()
}

fun printFileAST(file: Path, output: OutputStream) {
    val source = String(Files.readAllBytes(file))
    val writer = output.bufferedWriter()
    parse(source).accept(PrinterVisitor(writer))
    writer.flush()
}