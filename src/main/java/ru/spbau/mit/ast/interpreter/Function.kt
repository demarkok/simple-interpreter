package ru.spbau.mit.ast.interpreter

import ru.spbau.mit.ast.Block

data class Function(val functionBlock: Block,
                    val arguments: List<String>,
                    val declarationContext: Context)
