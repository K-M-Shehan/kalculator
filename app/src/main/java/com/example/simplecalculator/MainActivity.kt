package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val calculatorEngine = CalculatorEngine()

    private lateinit var tvDisplay: TextView
    private lateinit var tvHistory: TextView
    private lateinit var btnToggleHistory: Button
    private var historyExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHistory = findViewById(R.id.tvHistory)
        tvDisplay = findViewById(R.id.tvDisplay)
        btnToggleHistory = findViewById(R.id.btnToggleHistory)

        setNumberButtonListeners()
        setOperationButtonListeners()

        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { appendDecimal() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { appendPercent() }
        btnToggleHistory.setOnClickListener { toggleHistory() }

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
        val historyLimit = if (historyExpanded) 10 else 3
        tvHistory.text = calculatorEngine.historyText(historyLimit)
        tvHistory.maxLines = if (historyExpanded) 10 else 4
        btnToggleHistory.text = if (historyExpanded) getString(R.string.show_less_history) else getString(R.string.show_more_history)
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

    private fun toggleHistory() {
        historyExpanded = !historyExpanded
        updateDisplay()
    }
}
