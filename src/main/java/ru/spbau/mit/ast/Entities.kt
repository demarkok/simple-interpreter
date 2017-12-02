package ru.spbau.mit.ast

import ru.spbau.mit.exceptions.FunctionIsNotDefinedException
import ru.spbau.mit.exceptions.RedeclarationException
import ru.spbau.mit.exceptions.UnexpectedReturnException
import ru.spbau.mit.exceptions.VariableIsNotDefinedException


fun ContextInterface.resolveVariableOrThrow(name: String) =
        this.resolveVariable(name) ?: throw VariableIsNotDefinedException()

fun ContextInterface.resolveFunctionOrThrow(name: String) =
        this.resolveFunction(name) ?: throw FunctionIsNotDefinedException()

interface ASTEntity {
    fun evaluate(context: MutableContext): EvaluationResult
}

data class File(val block: Block) : ASTEntity {

    override fun evaluate(context: MutableContext): EvaluationResult {
        val result = block.evaluate(context)
        if (result.isPresent()) {
            throw UnexpectedReturnException()
        }
        return None
    }
}

data class Block(private val statements: List<Statement>) : ASTEntity {

    override fun evaluate(context: MutableContext): EvaluationResult {
        @Suppress("LoopToCallChain")
        for (statement in statements) {
            val result = statement.evaluate(context)
            if (result.isPresent()) {
                return result
            }
        }
        return None
    }
}

interface Statement : ASTEntity

data class FunctionDeclaration(
        private val name: String,
        private val parameterNames: List<String>,
        private val body: Block
) : Statement {

    override fun evaluate(context: MutableContext): EvaluationResult {
        context.addFunction(name, Function(body, parameterNames, context.toImmutable()))
        return None
    }
}

data class VariableDeclaration(private val name: String,
                               private val value: Expression? = null
) : Statement {

    override fun evaluate(context: MutableContext): EvaluationResult {
        if (context.resolveVariable(name) != null) {
            throw RedeclarationException()
        }
        context.addVariable(name, Variable(value?.evaluate(context)?.value ?: 0))
        return None
    }
}

data class While(private val condition: Expression,
                 private val body: Block
) : Statement {

    override fun evaluate(context: MutableContext): EvaluationResult {
        while (condition.evaluate(context).value != 0) {
            val result = body.evaluate(MutableContext(context))
            if (result.isPresent()) {
                return result
            }
        }
        return None
    }

}

data class If(private val condition: Expression,
              private val body: Block,
              private val elseBody: Block?
) : Statement {

    override fun evaluate(context: MutableContext): EvaluationResult {
        if (condition.evaluate(context).value != 0) {
            val result = body.evaluate(MutableContext(context))
            if (result.isPresent()) {
                return result
            }
        } else if (elseBody != null) {
            val result = elseBody.evaluate(MutableContext(context))
            if (result.isPresent()) {
                return result
            }
        }
        return None
    }
}

data class VariableAssignment(private val name: String, private val value: Expression) : Statement {

    override fun evaluate(context: MutableContext): EvaluationResult {
        val variable = context.resolveVariableOrThrow(name)
        variable.value = value.evaluate(context).value
        return None
    }
}

data class Return(val expression: Expression) : Statement {
    override fun evaluate(context: MutableContext): EvaluationResult = expression.evaluate(context)
}

data class Println(val arguments: List<Expression>) : Statement {

    override fun evaluate(context: MutableContext): EvaluationResult {
        val result = arguments.map { it.evaluate(context).value }
                .toIntArray()
                .joinToString(" ")
                .plus("\n")
        context.outputStream.write(result.toByteArray())
        return None
    }
}


interface Expression : Statement {
    override fun evaluate(context: MutableContext): Value
}

data class FunctionCall(private val name: String,
                        val arguments: List<Expression>
) : Expression {

    override fun evaluate(context: MutableContext): Value {
        val function = context.resolveFunctionOrThrow(name)
        val callContext = MutableContext(function.declarationContext, context.outputStream)
        callContext.addFunction(name, function)

        arguments.map { it.evaluate(context) }
                .zip(function.arguments)
                .forEach { callContext.addVariable(it.second, Variable(it.first.value)) }

        return function.functionBlock.evaluate(callContext) as? Value ?: Value(0)
    }
}


data class BinaryExpression(private val leftOperand: Expression,
                            private val operator: BinaryOperator,
                            private val rightOperand: Expression
) : Expression {

    override fun evaluate(context: MutableContext): Value {
        val leftValue = leftOperand.evaluate(context).value
        val rightValue = rightOperand.evaluate(context).value
        return Value(operator(leftValue, rightValue))
    }
}

data class VariableIdentifier(private val name: String) : Expression {

    override fun evaluate(context: MutableContext): Value {
        val variable = context.resolveVariableOrThrow(name)
        return Value(variable.value)
    }
}

data class Literal(private val value: Int) : Expression {
    override fun evaluate(context: MutableContext): Value = Value(value)

}





