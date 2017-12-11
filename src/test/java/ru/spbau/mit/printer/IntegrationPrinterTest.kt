package ru.spbau.mit.printer

import org.junit.Test
import ru.spbau.mit.ast.printer.printFileAST
import java.io.ByteArrayOutputStream
import java.nio.file.Paths
import kotlin.test.assertEquals

class IntegrationPrinterTest {


    @Test
    fun gcdTest() {
        val outputStream = ByteArrayOutputStream()
        printFileAST(Paths.get("testSources/gcd.la"), outputStream)

        val output = outputStream.toString()
        assertEquals("""
            |File (1:0)
            |  Block (1:0)
            |    Function declaration (1:0), value = gcd
            |      Block (2:4)
            |        If (2:4)
            |          Binary expression (2:8), value = EQ
            |            Variable identifier (2:8), value = a
            |            Literal (2:13), value = 0
            |          Block (3:8)
            |            Return (3:8)
            |              Variable identifier (3:15), value = b
            |          Block (5:8)
            |            Return (5:8)
            |              Function call (5:15), value = gcd
            |                Binary expression (5:19), value = MOD
            |                  Variable identifier (5:19), value = b
            |                  Variable identifier (5:23), value = a
            |                Variable identifier (5:26), value = a
            |    Println (9:0)
            |      Function call (9:8), value = gcd
            |        Literal (9:12), value = 12
            |        Literal (9:16), value = 18
            |    Println (10:0)
            |      Function call (10:8), value = gcd
            |        Literal (10:12), value = 1
            |        Literal (10:15), value = 60
            |
        """.trimMargin(), output)
    }

}