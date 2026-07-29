package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView

    private var currentInput = ""
    private var operator = ""
    private var firstValue = 0.0
    private var justCalculated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        setNumberButtonListeners()
        setOperationButtonListeners()

        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { appendDecimal() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }
    }

    private fun setNumberButtonListeners() {
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { view ->
                val button = view as Button
                appendNumber(button.text.toString())
            }
        }
    }

    private fun setOperationButtonListeners() {
        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperation("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { setOperation("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperation("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperation("/") }
    }

    private fun updateDisplay() {
        val displayText = when {
            operator.isNotEmpty() && currentInput.isNotEmpty() -> {
                "${formatNumber(firstValue)} $operator $currentInput"
            }
            operator.isNotEmpty() -> {
                "${formatNumber(firstValue)} $operator"
            }
            currentInput.isNotEmpty() -> currentInput
            else -> "0"
        }
        tvDisplay.text = displayText
    }

    private fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()
    }

    private fun appendNumber(number: String) {
        if (justCalculated) {
            currentInput = ""
            operator = ""
            justCalculated = false
        }
        currentInput += number
        updateDisplay()
    }

    private fun appendDecimal() {
        if (justCalculated) {
            currentInput = ""
            operator = ""
            justCalculated = false
        }
        if (currentInput.isEmpty()) {
            currentInput = "0."
        } else if (!currentInput.contains(".")) {
            currentInput += "."
        }
        updateDisplay()
    }

    private fun setOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            firstValue = currentInput.toDouble()
            operator = op
            currentInput = ""
            justCalculated = false
            updateDisplay()
        } else if (operator.isNotEmpty()) {
            // Allow changing the operator
            operator = op
            updateDisplay()
        }
    }

    private fun calculate() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondValue = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstValue + secondValue
                "-" -> firstValue - secondValue
                "*" -> firstValue * secondValue
                "/" -> if (secondValue != 0.0) firstValue / secondValue else 0.0
                else -> 0.0
            }

            tvDisplay.text = formatNumber(result)
            currentInput = result.toString()
            operator = ""
            justCalculated = true
        }
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            updateDisplay()
        }
    }

    private fun clear() {
        currentInput = ""
        operator = ""
        firstValue = 0.0
        justCalculated = false
        tvDisplay.text = "0"
    }
}
