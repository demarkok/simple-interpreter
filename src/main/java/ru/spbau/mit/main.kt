package ru.spbau.mit

import org.antlr.v4.runtime.misc.ParseCancellationException
import ru.spbau.mit.ast.printer.printFileAST
import java.nio.file.Paths

fun main(args: Array<String>) {

    try {
        printFileAST(Paths.get(args.first()), System.out)
    } catch (e: ParseCancellationException) {
        print(e.message)
    }
}
