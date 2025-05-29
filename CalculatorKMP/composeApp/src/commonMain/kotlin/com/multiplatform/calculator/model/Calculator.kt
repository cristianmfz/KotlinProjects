package com.multiplatform.calculator.model

sealed class Calculator(
    private val actual: Float,
    private val number: Float
) {
    protected abstract fun performCalculation(actual: Float, currentNumber: Float): Float

    operator fun invoke(): Float {
        return performCalculation(actual, number)
    }

    class Addition(actual: Float, plus: Float) : Calculator(actual, plus) {
        override fun performCalculation(actual: Float, currentNumber: Float): Float {
            return actual + currentNumber
        }
    }

    class Subtraction(actual: Float, subtraction: Float) : Calculator(actual, subtraction) {
        override fun performCalculation(actual: Float, currentNumber: Float): Float {
            return actual - currentNumber
        }
    }

    class Multiplication(actual: Float, multiplication: Float) : Calculator(actual, multiplication) {
        override fun performCalculation(actual: Float, currentNumber: Float): Float {
            return actual * currentNumber
        }
    }

    class Division(actual: Float, division: Float) : Calculator(actual, division) {
        override fun performCalculation(actual: Float, currentNumber: Float): Float {
            return if (currentNumber != 0f) {
                actual / currentNumber
            } else {
                Float.NaN
            }
        }
    }
}