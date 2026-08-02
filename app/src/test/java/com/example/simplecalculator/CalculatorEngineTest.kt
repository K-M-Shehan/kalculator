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

    @Test
    fun multipliesDecimalValuesAccurately() {
        val engine = CalculatorEngine()

        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("1")
        engine.inputOperation("*")
        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("2")
        engine.calculateResult()

        assertEquals("0.02", engine.displayText())
    }

    @Test
    fun addsDecimalValuesAccurately() {
        val engine = CalculatorEngine()

        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("1")
        engine.inputOperation("+")
        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("2")
        engine.calculateResult()

        assertEquals("0.3", engine.displayText())
    }

    @Test
    fun subtractsDecimalValuesAccurately() {
        val engine = CalculatorEngine()

        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("3")
        engine.inputOperation("-")
        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("1")
        engine.calculateResult()

        assertEquals("0.2", engine.displayText())
    }

    @Test
    fun dividesDecimalValuesAccurately() {
        val engine = CalculatorEngine()

        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("3")
        engine.inputOperation("/")
        engine.inputDigit("0")
        engine.inputDecimal()
        engine.inputDigit("1")
        engine.calculateResult()

        assertEquals("3", engine.displayText())
    }

    @Test
    fun calculatesDecimalModulusAccurately() {
        val engine = CalculatorEngine()

        engine.inputDigit("5")
        engine.inputDecimal()
        engine.inputDigit("5")
        engine.inputOperation("mod")
        engine.inputDigit("2")
        engine.calculateResult()

        assertEquals("1.5", engine.displayText())
    }
}