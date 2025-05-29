package com.multiplatform.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multiplatform.calculator.model.Calculator
import com.multiplatform.calculator.model.Operation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {
    private val _mutableEntryState = MutableStateFlow("0")
    private val _mutableMemoryState = MutableStateFlow(0f)
    private val _mutableButtonState = MutableStateFlow(Operation.EMPTY)
    private val _mutableEraserState = MutableStateFlow(false)

    val buttonState = _mutableButtonState.asStateFlow()
    val entryState = _mutableEntryState.asStateFlow()

    fun onEvent(calculatorEvent: CalculatorEvent) {
        viewModelScope.launch {
            when (calculatorEvent) {
                CalculatorEvent.AllClear -> allClear()
                CalculatorEvent.Equals -> equals()
                is CalculatorEvent.Calculation -> {
                    when (calculatorEvent.operation) {
                        Operation.MODE -> TODO()
                        Operation.AC -> allClear()
                        Operation.PLUS_MINUS -> toggleSign()
                        Operation.COMMA -> addDecimal()
                        Operation.PERCENTAGE -> percentage()
                        Operation.EQUALS -> equals()
                        else -> selectOperator(calculatorEvent.operation)
                    }
                }
                is CalculatorEvent.Number -> {
                    enterNumber(calculatorEvent.value)
                }
            }
        }
    }

    private fun enterNumber(entry: Int) {
        viewModelScope.launch {
            if (_mutableEraserState.value) {
                _mutableEntryState.value = entry.toString()
                _mutableEraserState.value = false
            } else {
                if (_mutableEntryState.value.length < 12) {
                    _mutableEntryState.value = when (_mutableEntryState.value) {
                        "0" -> entry.toString()
                        else -> _mutableEntryState.value + entry
                    }
                }
            }
        }
    }

    private fun allClear() {
        viewModelScope.launch {
            _mutableEntryState.value = "0"
            _mutableMemoryState.value = 0f
            _mutableButtonState.value = Operation.EMPTY
            _mutableEraserState.value = false
        }
    }

    private fun toggleSign() {
        viewModelScope.launch {
            val currentValue = _mutableEntryState.value.toFloatOrNull() ?: 0f
            val toggledValue = -currentValue
            _mutableEntryState.value = formatNumber(toggledValue)

            if (_mutableButtonState.value == Operation.EMPTY) {
                _mutableMemoryState.value = toggledValue
            }
            _mutableEraserState.value = true
        }
    }

    private fun addDecimal() {
        viewModelScope.launch {
            val currentValue = _mutableEntryState.value

            if (!currentValue.contains(".")) {
                if (currentValue == "0") {
                    _mutableEntryState.value = "0."
                } else {
                    _mutableEntryState.value = "$currentValue."
                }
                _mutableEraserState.value = false
            }
        }
    }

    private fun percentage() {
        viewModelScope.launch {
            val currentValue = entryState.value.toFloat()
            val percentageValue = currentValue / 100

            if (_mutableButtonState.value == Operation.EMPTY) {
                _mutableMemoryState.value = percentageValue
                _mutableEntryState.value = formatNumber(percentageValue)
            } else {
                _mutableEntryState.value = formatNumber(percentageValue)
            }
            _mutableEraserState.value = true
        }
    }

    private fun equals() {
        viewModelScope.launch {
            if (_mutableButtonState.value == Operation.EMPTY || _mutableMemoryState.value == 0f) {
                _mutableMemoryState.value = _mutableEntryState.value.toFloat()
                _mutableEntryState.value = formatNumber(_mutableMemoryState.value)
            } else {
                val total = calculation(
                    _mutableMemoryState.value,
                    _mutableEntryState.value.toFloat(),
                    _mutableButtonState.value
                )
                _mutableMemoryState.value = total
                _mutableEntryState.value = formatNumber(total)
            }
            _mutableEraserState.value = true
            _mutableButtonState.value = Operation.EMPTY
        }
    }

    private fun formatNumber(value: Float): String {
        val strValue = value.toString()
        return if (strValue.contains('.')) {
            strValue.trimEnd('0').trimEnd('.')
        } else {
            strValue
        }
    }

    private fun selectOperator(button: Operation) {
        viewModelScope.launch {
            if (!_mutableEraserState.value) {
                val currentEntry = _mutableEntryState.value.toFloatOrNull() ?: 0f

                if (_mutableMemoryState.value == 0f) {
                    _mutableMemoryState.value = currentEntry
                } else {
                    _mutableMemoryState.value = calculation(
                        actual = _mutableMemoryState.value,
                        entry = currentEntry,
                        currentOperation = _mutableButtonState.value
                    )
                    _mutableEntryState.value = formatNumber(_mutableMemoryState.value)
                }
            }
            _mutableButtonState.value = button
            _mutableEraserState.value = true
        }
    }

    private fun calculation(
        actual: Float,
        entry: Float,
        currentOperation: Operation
    ): Float {
        return when (currentOperation) {
            Operation.DIVISION -> Calculator.Division(actual, entry)()
            Operation.MULTIPLICATION -> Calculator.Multiplication(actual, entry)()
            Operation.SUBTRACTION -> Calculator.Subtraction(actual, entry)()
            Operation.ADDITION -> Calculator.Addition(actual, entry)()
            else -> 0f
        }
    }
}

sealed class CalculatorEvent{
    data class Number(val value: Int) : CalculatorEvent()
    data class Calculation(val operation: Operation): CalculatorEvent()
    data object Equals : CalculatorEvent()
    data object AllClear : CalculatorEvent()
}