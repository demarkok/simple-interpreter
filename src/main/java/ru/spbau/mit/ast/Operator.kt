package ru.spbau.mit.ast

typealias Op = Function2<Int, Int, Int>

interface Operator : Op

abstract class AbstractOperator(private val op: Op) : Operator {
    override fun invoke(p1: Int, p2: Int): Int = op(p1, p2)
}

fun Boolean.toInt(): Int = if (this) 1 else 0

fun Int.toBoolean(): Boolean = this != 0

object GT : AbstractOperator({ x, y -> (x > y).toInt() })
object LT : AbstractOperator({ x, y -> (x < y).toInt() })
object Equal : AbstractOperator({ x, y -> (x == y).toInt() })
object LE : AbstractOperator({ x, y -> (x <= y).toInt() })
object GE : AbstractOperator({ x, y -> (x >= y).toInt() })
object NotEqual : AbstractOperator({ x, y -> (x != y).toInt() })
object And : AbstractOperator({ x, y -> (x.toBoolean() && y.toBoolean()).toInt() })
object Or : AbstractOperator({ x, y -> (x.toBoolean() || y.toBoolean()).toInt() })

object Add : AbstractOperator({ x, y -> x + y })
object Sub : AbstractOperator({ x, y -> x - y })
object Mul : AbstractOperator({ x, y -> x * y })
object Div : AbstractOperator({ x, y -> x / y })
object Mod : AbstractOperator({ x, y -> x % y })

object OperatorFactory {
    fun createOperator(string: String): Operator {
        return when (string) {
            ">" -> GT
            "<" -> LT
            "==" -> Equal
            "<=" -> LE
            ">=" -> GE
            "!=" -> NotEqual
            "&&" -> And
            "||" -> Or
            "+" -> Add
            "-" -> Sub
            "*" -> Mul
            "/" -> Div
            "%" -> Mod
            else -> throw RuntimeException()
        }
    }
}
