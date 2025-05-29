package com.multiplatform.calculator.model

enum class Operation(
    val symbol: String
) {
    EMPTY(""),
    MODE("☯"),
    AC("AC"),
    PLUS_MINUS("+/-"),
    COMMA(","),
    PERCENTAGE("%"),
    DIVISION("/"),
    MULTIPLICATION("x"),
    SUBTRACTION("—"),
    ADDITION("+"),
    EQUALS("=")
}