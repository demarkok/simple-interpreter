package ru.spbau.mit.printer

import org.antlr.v4.runtime.misc.ParseCancellationException
import org.junit.Test
import ru.spbau.mit.parse
import ru.spbau.mit.ast.printer.toPrettyString
import kotlin.test.assertEquals

class PrinterTest {

    @Test(expected = ParseCancellationException::class)
    fun parsingExceptionTest() {
        val s = """
            |if(1):
            |   return 4
            |}
            |
            |
            |""".trimMargin()

        val result = parse(s)
        print(result)
    }

    @Test
    fun emptyPrintlnTest() {
        val s = "println()"
        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Println (1:0)
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun functionWithoutParametersTest() {
        val s = "fun run() { }"
        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Function declaration (1:0), value = run
            |      Block (1:12)
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun functionCallWithoutParametersTest() {
        val s = "run()"
        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Function call (1:0), value = run
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun variableDeclarationTest() {
        val s = "var a = 10"
        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Variable declaration (1:0), value = a
            |      Literal (1:8), value = 10
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun functionDeclarationTest() {
        val s = """
            |fun f(x) {
            |   return 4
            |}
            |
            |
            |""".trimMargin()

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Function declaration (1:0), value = f
            |      Block (2:3)
            |        Return (2:3)
            |          Literal (2:10), value = 4
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun whileTest() {
        val s = """
            |while(1) {
            |   return 4
            |}
            |
            |""".trimMargin()

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    While (1:0)
            |      Literal (1:6), value = 1
            |      Block (2:3)
            |        Return (2:3)
            |          Literal (2:10), value = 4
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun ifWithoutElseTest() {
        val s = """
            |if(1) {
            |   return 4
            |}
            |
            |
            |""".trimMargin()

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    If (1:0)
            |      Literal (1:3), value = 1
            |      Block (2:3)
            |        Return (2:3)
            |          Literal (2:10), value = 4
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun ifWithElseTest() {
        val s = """
            |if(1) {
            |   return 4
            |} else {
            |   return 5
            |}
            |
            |""".trimMargin()

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    If (1:0)
            |      Literal (1:3), value = 1
            |      Block (2:3)
            |        Return (2:3)
            |          Literal (2:10), value = 4
            |      Block (4:3)
            |        Return (4:3)
            |          Literal (4:10), value = 5
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun variableAssignmentTest() {
        val s = "a = 10"

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Variable assignment (1:0), value = a
            |      Literal (1:4), value = 10
            |
        """.trimMargin()
        assertEquals(expected, result)
    }

    @Test
    fun functionCallTest() {
        val s = "f(239, x)"

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Function call (1:0), value = f
            |      Literal (1:2), value = 239
            |      Variable identifier (1:7), value = x
            |
        """.trimMargin()
        assertEquals(expected, result)
    }



    @Test
    fun printlnTest() {
        val s = "println(239, x)"

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Println (1:0)
            |      Literal (1:8), value = 239
            |      Variable identifier (1:13), value = x
            |
        """.trimMargin()
        assertEquals(expected, result)
    }



    @Test
    fun binaryExpressionTest() {
        val s = """
            |1 > 0
            |1 < 0
            |1 == 0
            |1 <= 0
            |1 >= 0
            |1 != 0
            |1 && 0
            |1 || 0
            |1 + 0
            |1 - 0
            |1 * 0
            |1 / 0
            |1 % 0
            |
            |""".trimMargin()

        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Binary expression (1:0), value = GT
            |      Literal (1:0), value = 1
            |      Literal (1:4), value = 0
            |    Binary expression (2:0), value = LT
            |      Literal (2:0), value = 1
            |      Literal (2:4), value = 0
            |    Binary expression (3:0), value = EQ
            |      Literal (3:0), value = 1
            |      Literal (3:5), value = 0
            |    Binary expression (4:0), value = LE
            |      Literal (4:0), value = 1
            |      Literal (4:5), value = 0
            |    Binary expression (5:0), value = GE
            |      Literal (5:0), value = 1
            |      Literal (5:5), value = 0
            |    Binary expression (6:0), value = NEQ
            |      Literal (6:0), value = 1
            |      Literal (6:5), value = 0
            |    Binary expression (7:0), value = AND
            |      Literal (7:0), value = 1
            |      Literal (7:5), value = 0
            |    Binary expression (8:0), value = OR
            |      Literal (8:0), value = 1
            |      Literal (8:5), value = 0
            |    Binary expression (9:0), value = ADD
            |      Literal (9:0), value = 1
            |      Literal (9:4), value = 0
            |    Binary expression (10:0), value = SUB
            |      Literal (10:0), value = 1
            |      Literal (10:4), value = 0
            |    Binary expression (11:0), value = MUL
            |      Literal (11:0), value = 1
            |      Literal (11:4), value = 0
            |    Binary expression (12:0), value = DIV
            |      Literal (12:0), value = 1
            |      Literal (12:4), value = 0
            |    Binary expression (13:0), value = MOD
            |      Literal (13:0), value = 1
            |      Literal (13:4), value = 0
            |
        """.trimMargin()
        assertEquals(expected, result)
    }






    @Test
    fun complexTest() {

        val s = """
            |fun fib(n) {
            |   if (n <= 1) {
            |       return 1
            |   }
            |   return fib(n - 1) + fib(n - 2)
            |}
            |
            |""".trimMargin()


        val result = parse(s).toPrettyString()
        val expected = """
            |File (1:0)
            |  Block (1:0)
            |    Function declaration (1:0), value = fib
            |      Block (2:3)
            |        If (2:3)
            |          Binary expression (2:7), value = LE
            |            Variable identifier (2:7), value = n
            |            Literal (2:12), value = 1
            |          Block (3:7)
            |            Return (3:7)
            |              Literal (3:14), value = 1
            |        Return (5:3)
            |          Binary expression (5:10), value = ADD
            |            Function call (5:10), value = fib
            |              Binary expression (5:14), value = SUB
            |                Variable identifier (5:14), value = n
            |                Literal (5:18), value = 1
            |            Function call (5:23), value = fib
            |              Binary expression (5:27), value = SUB
            |                Variable identifier (5:27), value = n
            |                Literal (5:31), value = 2
            |
        """.trimMargin()
        assertEquals(expected, result)
    }
}