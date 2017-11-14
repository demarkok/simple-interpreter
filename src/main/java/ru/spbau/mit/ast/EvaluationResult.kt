/*package ru.spbau.mit.ast


interface EvaluationResult {
    fun isPresent(): Boolean
}

abstract class AbstractEvaluationResult(private val isPresent: Boolean): EvaluationResult {
    override fun isPresent(): Boolean {
        return isPresent
    }
}

class Value(val value: Int): AbstractEvaluationResult(true)

object None: AbstractEvaluationResult(false)

*/

package ru.spbau.mit.ast


interface EvaluationResult {
    fun isPresent(): Boolean {
        return value != null
    }

    val value: Int?
}

class Value(override val value: Int) : EvaluationResult

object None : EvaluationResult {
    override val value: Int? = null
}
