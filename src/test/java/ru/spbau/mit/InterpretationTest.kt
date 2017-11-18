package ru.spbau.mit

import org.junit.Test
import ru.spbau.mit.ast.*
import ru.spbau.mit.exceptions.FunctionIsNotDefinedException
import ru.spbau.mit.exceptions.RedeclarationException
import ru.spbau.mit.exceptions.UnexpectedReturnException
import ru.spbau.mit.exceptions.VariableIsNotDefinedException
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class InterpretationTest {

    @Test
    fun emptyTest() {

        val root = File(Block(emptyList()))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(None, result)
        assertEquals("", output)
    }

    @Test
    fun variableDeclarationTest() {
        val root = VariableDeclaration("a", Literal(10))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(None, result)
        assertEquals("", output)
    }

    @Test
    fun defaultVariableValueTest() {
        val root = Block(listOf(
                VariableDeclaration("a"),
                Return(VariableIdentifier("a"))
        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(Value(0), result)
        assertEquals("", output)
    }


    @Test
    fun functionDeclarationTest() {
        val root = FunctionDeclaration("f", listOf("x"), Block(listOf(
                Println(listOf(Literal(4))),
                Return(Literal(2)))))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(None, result)
        assertEquals("", output)
    }

    @Test
    fun whileTest() {
        val root = File(Block(listOf(
                VariableDeclaration("i"),
                While(BinaryExpression(VariableIdentifier("i"), Lt, Literal(3)), Block(listOf(
                        Println(listOf(VariableIdentifier("i"))),
                        VariableAssignment("i", BinaryExpression(VariableIdentifier("i"), Add, Literal(1)))
                )))
        )))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(None, result)
        assertEquals("0\n1\n2\n", output)
    }

    @Test
    fun ifWithoutElseTest() {
        val root = Block(listOf(
                If(Literal(1), Block(listOf(Return(Literal(4)))), null),
                Return(Literal(0))

        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(Value(4), result)
        assertEquals("", output)
    }

    @Test
    fun ifWithElseTest() {
        val root = Block(listOf(
                If(Literal(0), Block(listOf(
                        Return(Literal(4))
                )), Block(listOf(
                        Return(Literal(5))
                )))
        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(Value(5), result)
        assertEquals("", output)
    }

    @Test
    fun variableAssignmentTest() {
        val root = Block(listOf(
                VariableDeclaration("a"),
                VariableAssignment("a", Literal(10)),
                Return(VariableIdentifier("a"))
        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(Value(10), result)
        assertEquals("", output)
    }

    @Test
    fun functionCallTest() {
        val root = Block(listOf(
                FunctionDeclaration("f", listOf("a"), Block((listOf(
                        Return(VariableIdentifier("a"))
                )))),
                Return(FunctionCall("f", listOf(Literal(239))))
        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(Value(239), result)
        assertEquals("", output)
    }

    @Test
    fun binaryExpressionTest() {
        val root = Block(listOf(
                Println(listOf(
                        BinaryExpression(Literal(10), Gt, Literal(20)),
                        BinaryExpression(Literal(10), Lt, Literal(20)),
                        BinaryExpression(Literal(10), Eq, Literal(20)),
                        BinaryExpression(Literal(10), Le, Literal(20)),
                        BinaryExpression(Literal(10), Ge, Literal(20)),
                        BinaryExpression(Literal(10), Neq, Literal(20)),
                        BinaryExpression(Literal(10), And, Literal(20)),
                        BinaryExpression(Literal(10), Or, Literal(20)),
                        BinaryExpression(Literal(10), Add, Literal(20)),
                        BinaryExpression(Literal(10), Sub, Literal(20)),
                        BinaryExpression(Literal(10), Mul, Literal(20)),
                        BinaryExpression(Literal(10), Div, Literal(20)),
                        BinaryExpression(Literal(10), Mod, Literal(20))
                ))
        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(None, result)
        assertEquals("0 1 0 1 0 1 1 1 30 -10 200 0 10\n", output)
    }


    @Test
    fun varAndFunWithSameName() {
        val root = Block(listOf(
                FunctionDeclaration("f", listOf("f"), Block(listOf(
                        Return(BinaryExpression(VariableIdentifier("f"), Add, Literal(1)))
                ))),
                VariableDeclaration("f", Literal(239)),
                Println(listOf(FunctionCall("f", listOf(VariableIdentifier("f"))),
                        VariableIdentifier("f")
                ))
        ))
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()

        assertEquals(None, result)
        assertEquals("240 239\n", output)
    }

    @Test
    fun emptyPrintlnTest() {
        val root = Println(emptyList())
        val stream = ByteArrayOutputStream()

        val result = root.evaluate(MutableContext(null, stream))
        val output = stream.toString()


        assertEquals(None, result)
        assertEquals("\n", output)
    }

    @Test(expected = UnexpectedReturnException::class)
    fun unexpectedReturnExceptionTest() {
        val root = File(Block(listOf(Return(Literal(1)))))
        val stream = ByteArrayOutputStream()

        root.evaluate(MutableContext(null, stream))
    }

    @Test(expected = RedeclarationException::class)
    fun redeclarationExceptionTest() {
        val root = File(Block(listOf(
                VariableDeclaration("a"),
                VariableDeclaration("a")
        )))
        val stream = ByteArrayOutputStream()

        root.evaluate(MutableContext(null, stream))
    }

    @Test(expected = VariableIsNotDefinedException::class)
    fun variableIsNotDefinedExceptionTest() {
        val root = File(Block(listOf(
                Println(listOf(VariableIdentifier("x")))
        )))
        val stream = ByteArrayOutputStream()

        root.evaluate(MutableContext(null, stream))
    }


    @Test(expected = FunctionIsNotDefinedException::class)
    fun functionIsNotDefinedExceptionTest() {
        val root = File(Block(listOf(
                FunctionCall("x", emptyList())
        )))
        val stream = ByteArrayOutputStream()

        root.evaluate(MutableContext(null, stream))
    }

}