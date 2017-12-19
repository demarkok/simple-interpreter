package ru.spbau.mit.ast.printer

import ru.spbau.mit.ast.*

class PrinterVisitor(private val out: Appendable): ASTVisitor<Unit> {

    override fun visit(file: File) {
        appendEntity(file, "File")
        withIndent { file.block.accept(this) }
    }

    override fun visit(block: Block) {
        appendEntity(block, "Block")
        withIndent { block.statements.forEach({ it.accept(this) }) }
    }

    override fun visit(functionDeclaration: FunctionDeclaration) {
        appendEntityWithValue(functionDeclaration, "Function declaration", functionDeclaration.name)
        withIndent { functionDeclaration.body.accept(this) }
    }

    override fun visit(variableDeclaration: VariableDeclaration) {
        appendEntityWithValue(variableDeclaration, "Variable declaration", variableDeclaration.name)
        withIndent { variableDeclaration.value?.accept(this) }
    }

    override fun visit(whileStatement: While) {
        appendEntity(whileStatement, "While")
        withIndent {
            whileStatement.condition.accept(this)
            whileStatement.body.accept(this)
        }
    }

    override fun visit(ifStatement: If) {
        appendEntity(ifStatement, "If")
        withIndent {
            ifStatement.condition.accept(this)
            ifStatement.body.accept(this)
            ifStatement.elseBody?.accept(this)
        }
    }

    override fun visit(variableAssignment: VariableAssignment) {
        appendEntityWithValue(variableAssignment, "Variable assignment", variableAssignment.name)
        withIndent {
            variableAssignment.value.accept(this)
        }
    }

    override fun visit(returnStatement: Return) {
        appendEntity(returnStatement, "Return")
        withIndent {
            returnStatement.expression.accept(this)
        }
    }

    override fun visit(println: Println) {
        appendEntity(println, "Println")
        withIndent {
            println.arguments.forEach({ it.accept(this) })
        }
    }

    override fun visit(read: Read) {
        appendEntityWithValue(read, "Read", read.name)
    }

    override fun visit(binaryExpression: BinaryExpression) {
        appendEntityWithValue(binaryExpression, "Binary expression", binaryExpression.operator.name)
        withIndent {
            binaryExpression.leftOperand.accept(this)
            binaryExpression.rightOperand.accept(this)
        }
    }

    override fun visit(functionCall: FunctionCall) {
        appendEntityWithValue(functionCall, "Function call", functionCall.name)
        withIndent {
            functionCall.arguments.forEach({ it.accept(this) })
        }
    }

    override fun visit(variableIdentifier: VariableIdentifier) {
        appendEntityWithValue(variableIdentifier, "Variable identifier", variableIdentifier.name)
    }

    override fun visit(literal: Literal) {
        appendEntityWithValue(literal, "Literal", literal.value.toString())
    }

    private var indent: Int = 0

    private fun appendEntity(entity: ASTEntity, name: String) {
        out.append(" ".repeat(indent))
        out.appendln("$name (${entity.line}:${entity.position})")
    }

    private fun appendEntityWithValue(entity: ASTEntity, name: String, value: String) {
        out.append(" ".repeat(indent))
        out.appendln("$name (${entity.line}:${entity.position}), value = $value")
    }

    private fun withIndent(body: () -> Unit) {
        indent += INDENT_BASE
        body()
        indent -= INDENT_BASE
    }


    companion object {
        const val INDENT_BASE = 2
    }

}