package ru.spbau.mit.ast

import ru.spbau.mit.ast.interpreter.ContextInterface
import ru.spbau.mit.exceptions.FunctionIsNotDefinedException
import ru.spbau.mit.exceptions.VariableIsNotDefinedException


fun ContextInterface.resolveVariableOrThrow(name: String) =
        this.resolveVariable(name) ?: throw VariableIsNotDefinedException()

fun ContextInterface.resolveFunctionOrThrow(name: String) =
        this.resolveFunction(name) ?: throw FunctionIsNotDefinedException()

interface ASTEntity {
    val line: Int
    val position: Int
    fun <T> accept(visitor: ASTVisitor<T>): T
}

data class File(val block: Block, override val line: Int, override val position: Int) : ASTEntity {
    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class Block(val statements: List<Statement>, override val line: Int, override val position: Int) : ASTEntity {
    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

interface Statement : ASTEntity

data class FunctionDeclaration(
        val name: String,
        val parameterNames: List<String>,
        val body: Block,
        override val line: Int,
        override val position: Int
) : Statement {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class VariableDeclaration(
        val name: String,
        val value: Expression? = null,
        override val line: Int,
        override val position: Int
) : Statement {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class While(
        val condition: Expression,
        val body: Block,
        override val line: Int,
        override val position: Int
) : Statement {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class If(
        val condition: Expression,
        val body: Block,
        val elseBody: Block?,
        override val line: Int,
        override val position: Int
) : Statement {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class VariableAssignment(
        val name: String,
        val value: Expression,
        override val line: Int,
        override val position: Int) : Statement {
    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class Return(val expression: Expression, override val line: Int, override val position: Int) : Statement {
    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class Println(val arguments: List<Expression>, override val line: Int, override val position: Int) : Statement {
    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

interface Expression : Statement

data class FunctionCall(val name: String,
                        val arguments: List<Expression>,
                        override val line: Int,
                        override val position: Int
) : Expression {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}


data class BinaryExpression(val leftOperand: Expression,
                            val operator: BinaryOperator,
                            val rightOperand: Expression,
                            override val line: Int,
                            override val position: Int
) : Expression {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class VariableIdentifier(val name: String, override val line: Int, override val position: Int) : Expression {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}

data class Literal(val value: Int, override val line: Int, override val position: Int) : Expression {

    override fun <T> accept(visitor: ASTVisitor<T>): T {
        return visitor.visit(this)
    }
}
