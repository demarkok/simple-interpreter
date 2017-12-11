package ru.spbau.mit

import ru.spbau.mit.ast.printer.printFileAST
import java.nio.file.Paths

fun main(args: Array<String>) {
    printFileAST(Paths.get(args.first()), System.out)
}
