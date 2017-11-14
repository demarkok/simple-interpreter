package ru.spbau.mit.evaluation


interface ContextInterface {
    fun resolveVariable(name: String): Variable?
    fun resolveFunction(name: String): Function?
    val parentContext: Context?
}

class Context(override val parentContext: Context?,
              private val variables: Map<String, Variable>,
              private val functions: Map<String, Function>) : ContextInterface {

    override fun resolveVariable(name: String): Variable? {
        return variables[name] ?: parentContext?.resolveVariable(name)
    }

    override fun resolveFunction(name: String): Function? {
        return functions[name] ?: parentContext?.resolveFunction(name)
    }
}

class MutableContext(override val parentContext: Context?, // TODO: refactor: hide the implementation details (Maps)
                     private val variables: MutableMap<String, Variable> = HashMap(),
                     private val functions: MutableMap<String, Function> = HashMap()) : ContextInterface {

    constructor(parent: MutableContext) : this(parent.toImmutable())

    override fun resolveFunction(name: String): Function? {
        return functions[name] ?: parentContext?.resolveFunction(name)
    }

    override fun resolveVariable(name: String): Variable? {
        return variables[name] ?: parentContext?.resolveVariable(name)
    }

    fun addFunction(name: String, function: Function) {
        functions[name] = function
    }

    fun addVariable(name: String, variable: Variable) {
        variables[name] = variable
    }

    fun toImmutable(): Context {
        val variableEntries = variables.entries.map { x -> x.toPair() }
        val functionEntries = functions.entries.map { x -> x.toPair() }

        val immutableVariables = mapOf<String, Variable>(*variables
                .entries
                .map { x -> x.toPair() }
                .toTypedArray())

        val immutableFunctions = mapOf<String, Function>(*functions
                .entries
                .map { x -> x.toPair() }
                .toTypedArray())

        return Context(parentContext, immutableVariables, immutableFunctions)
    }
}