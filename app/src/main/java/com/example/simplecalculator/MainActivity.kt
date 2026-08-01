package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val calculatorEngine = CalculatorEngine()

    private lateinit var tvDisplay: TextView
    private lateinit var tvHistory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHistory = findViewById(R.id.tvHistory)
        tvDisplay = findViewById(R.id.tvDisplay)

        setNumberButtonListeners()
        setOperationButtonListeners()

        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { appendDecimal() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { appendPercent() }

        updateDisplay()
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
        findViewById<Button>(R.id.btnModulus).setOnClickListener { setOperation("mod") }
    }

    private fun updateDisplay() {
        tvDisplay.text = calculatorEngine.displayText()
        tvHistory.text = calculatorEngine.historyText()
    }

    private fun appendNumber(number: String) {
        calculatorEngine.inputDigit(number)
        updateDisplay()
    }

    private fun appendDecimal() {
        calculatorEngine.inputDecimal()
        updateDisplay()
    }

    private fun setOperation(op: String) {
        calculatorEngine.inputOperation(op)
        updateDisplay()
    }

    private fun calculate() {
        calculatorEngine.calculateResult()
        updateDisplay()
    }

    private fun backspace() {
        calculatorEngine.backspace()
        updateDisplay()
    }

    private fun appendPercent() {
        calculatorEngine.inputPercent()
        updateDisplay()
    }

    private fun clear() {
        calculatorEngine.clear()
        updateDisplay()
    }
}
