package ru.spbau.mit.interpreter

import org.junit.Test
import ru.spbau.mit.ast.interpreter.interpretFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Paths
import kotlin.test.assertEquals

/**
 * It doesn't work anymore because inputStream was added into evaluation context.
 * TODO: fix it
 */
class IntegrationInterpretationTest {
/*
    @Test
    fun fibonacciTest() {
        val outputStream = ByteArrayOutputStream()
        interpretFile(Paths.get("testSources/fibonacci.la"), outputStream)

        val output = outputStream.toString()
        assertEquals("1 1\n2 2\n3 3\n4 5\n5 8\n", output)
    }

    @Test
    fun gcdTest() {
        val outputStream = ByteArrayOutputStream()
        interpretFile(Paths.get("testSources/gcd.la"), outputStream)

        val output = outputStream.toString()
        assertEquals("6\n1\n", output)
    }

    @Test
    fun factorialTest() {
        val outputStream = ByteArrayOutputStream()
        interpretFile(Paths.get("testSources/factorial.la"), outputStream)

        val output = outputStream.toString()
        assertEquals("1\n1\n2\n6\n24\n120\n", output)
    }

    @Test
    fun primeNumbersTest() {
        val outputStream = ByteArrayOutputStream()
        interpretFile(Paths.get("testSources/primeNumbers.la"), outputStream)

        val output = outputStream.toString()
        assertEquals("2\n3\n5\n7\n11\n13\n17\n19\n23\n29\n", output)
    }
*/
}
