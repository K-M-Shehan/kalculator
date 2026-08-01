package com.example.simplecalculator

import java.math.BigDecimal

class CalculatorEngine {

    private var accumulator: Double? = null
    private var pendingOperator: String? = null
    private var currentInput = ""
    private var justCalculated = false
    private var errorMessage: String? = null
    private val historyEntries = mutableListOf<String>()

    fun inputDigit(digit: String) {
        if (!digit.matches(Regex("\\d"))) {
            return
        }
        resetAfterErrorIfNeeded()
        if (justCalculated && pendingOperator == null) {
            currentInput = ""
            accumulator = null
        }
        justCalculated = false
        currentInput += digit
    }

    fun inputDecimal() {
        resetAfterErrorIfNeeded()
        if (justCalculated && pendingOperator == null) {
            currentInput = ""
            accumulator = null
        }
        justCalculated = false
        if (currentInput.isEmpty()) {
            currentInput = "0."
        } else if (!currentInput.contains('.')) {
            currentInput += '.'
        }
    }

    fun inputOperation(operation: String) {
        resetAfterErrorIfNeeded()
        if (currentInput.isEmpty()) {
            if (accumulator != null) {
                pendingOperator = operation
            }
            return
        }

        val inputValue = currentInput.toDoubleOrNull()
        if (inputValue == null) {
            showError("That number is not playing by the rules.")
            return
        }

        if (accumulator == null || pendingOperator == null) {
            accumulator = inputValue
            currentInput = ""
            pendingOperator = operation
            justCalculated = false
            return
        }

        val previousAccumulator = accumulator ?: 0.0
        val previousOperation = pendingOperator ?: operation
        val result = calculate(previousAccumulator, previousOperation, inputValue)
        if (result == null) {
            return
        }

        historyEntries.add(
            "${formatNumber(previousAccumulator)} ${operationSymbol(previousOperation)} ${formatNumber(inputValue)} = ${formatNumber(result)}"
        )
        accumulator = result
        currentInput = ""
        pendingOperator = operation
        justCalculated = false
    }

    fun inputPercent() {
        resetAfterErrorIfNeeded()
        val inputValue = currentInput.toDoubleOrNull() ?: return
        val percentValue = when (pendingOperator) {
            "+", "-" -> (accumulator ?: inputValue) * inputValue / 100.0
            else -> inputValue / 100.0
        }
        currentInput = formatNumber(percentValue)
        justCalculated = false
    }

    fun calculateResult() {
        resetAfterErrorIfNeeded()
        val leftValue = accumulator
        val operation = pendingOperator
        val rightValue = currentInput.toDoubleOrNull()

        if (leftValue == null || operation == null || rightValue == null) {
            return
        }

        val expression = "${formatNumber(leftValue)} ${operationSymbol(operation)} ${formatNumber(rightValue)}"
        val result = calculate(leftValue, operation, rightValue)
        if (result == null) {
            return
        }

        currentInput = formatNumber(result)
        accumulator = null
        pendingOperator = null
        justCalculated = true
        historyEntries.add("$expression = ${currentInput}")
    }

    fun backspace() {
        if (errorMessage != null) {
            clear()
            return
        }
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
        } else if (pendingOperator != null) {
            pendingOperator = null
        }
    }

    fun clear() {
        accumulator = null
        pendingOperator = null
        currentInput = ""
        justCalculated = false
        errorMessage = null
    }

    fun displayText(): String {
        errorMessage?.let { return it }

        return when {
            pendingOperator != null && currentInput.isNotEmpty() && accumulator != null -> {
                "${formatNumber(accumulator ?: 0.0)} ${operationSymbol(pendingOperator ?: "")} $currentInput"
            }
            pendingOperator != null && accumulator != null -> {
                "${formatNumber(accumulator ?: 0.0)} ${operationSymbol(pendingOperator ?: "")}"
            }
            currentInput.isNotEmpty() -> currentInput
            accumulator != null -> formatNumber(accumulator ?: 0.0)
            else -> "0"
        }
    }

    fun historyText(maxEntries: Int = 10): String {
        if (historyEntries.isEmpty()) {
            return "No calculations yet."
        }

        val visibleEntries = historyEntries.takeLast(maxEntries)
        return visibleEntries.asReversed().joinToString("\n")
    }

    private fun calculate(leftValue: Double, operation: String, rightValue: Double): Double? {
        return when (operation) {
            "+" -> leftValue + rightValue
            "-" -> leftValue - rightValue
            "*" -> leftValue * rightValue
            "/" -> {
                if (rightValue == 0.0) {
                    showError(
                        "${formatNumber(leftValue)} ÷ ${formatNumber(rightValue)} = undefined. The calculator is questioning reality."
                    )
                    null
                } else {
                    leftValue / rightValue
                }
            }
            "mod" -> {
                if (rightValue == 0.0) {
                    showError(
                        "${formatNumber(leftValue)} mod ${formatNumber(rightValue)} = undefined. Even math needs boundaries."
                    )
                    null
                } else {
                    leftValue % rightValue
                }
            }
            else -> {
                showError("That operation is not supported.")
                null
            }
        }
    }

    private fun showError(message: String) {
        errorMessage = message
        accumulator = null
        pendingOperator = null
        currentInput = ""
        justCalculated = false
        historyEntries.add(message)
    }

    private fun resetAfterErrorIfNeeded() {
        if (errorMessage != null) {
            clear()
        }
    }

    private fun formatNumber(value: Double): String {
        val plainText = BigDecimal.valueOf(value).stripTrailingZeros().toPlainString()
        return if (plainText == "-0") "0" else plainText
    }

    private fun operationSymbol(operation: String): String {
        return when (operation) {
            "+" -> "+"
            "-" -> "-"
            "*" -> "×"
            "/" -> "÷"
            "mod" -> "mod"
            else -> operation
        }
    }
}
