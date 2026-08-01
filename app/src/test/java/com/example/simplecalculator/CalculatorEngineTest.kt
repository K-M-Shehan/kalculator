package com.example.simplecalculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculatorEngineTest {

    @Test
    fun chainsOperationsLeftToRight() {
        val engine = CalculatorEngine()

        engine.inputDigit("2")
        engine.inputOperation("+")
        engine.inputDigit("3")
        engine.inputOperation("+")
        engine.inputDigit("4")
        engine.calculateResult()

        assertEquals("9", engine.displayText())
        assertTrue(engine.historyText().contains("2 + 3 = 5"))
    }

    @Test
    fun calculatesModulus() {
        val engine = CalculatorEngine()

        engine.inputDigit("1")
        engine.inputDigit("0")
        engine.inputOperation("mod")
        engine.inputDigit("3")
        engine.calculateResult()

        assertEquals("1", engine.displayText())
    }

    @Test
    fun appliesPercentageContextually() {
        val engine = CalculatorEngine()

        engine.inputDigit("2")
        engine.inputDigit("0")
        engine.inputDigit("0")
        engine.inputOperation("+")
        engine.inputDigit("1")
        engine.inputDigit("0")
        engine.inputPercent()
        engine.calculateResult()

        assertEquals("220", engine.displayText())
    }

    @Test
    fun divisionByZeroShowsHelpfulError() {
        val engine = CalculatorEngine()

        engine.inputDigit("0")
        engine.inputOperation("/")
        engine.inputDigit("0")
        engine.calculateResult()

        assertTrue(engine.displayText().contains("undefined"))
        assertTrue(engine.historyText().contains("undefined"))
    }

    @Test
    fun historyTextDefaultsToTenEntries() {
        val engine = CalculatorEngine()

        repeat(11) {
            engine.inputDigit("1")
            engine.inputOperation("+")
            engine.inputDigit("2")
            engine.calculateResult()
        }

        val lines = engine.historyText().split("\n")
        assertEquals(10, lines.size)
    }
}