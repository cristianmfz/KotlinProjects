package com.multiplatform.calculator.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multiplatform.calculator.model.Operation
import com.multiplatform.calculator.ui.theme.DarkGray
import com.multiplatform.calculator.ui.theme.DarkOrange
import com.multiplatform.calculator.ui.theme.LightGray
import com.multiplatform.calculator.ui.theme.LightOrange
import com.multiplatform.calculator.ui.theme.White

@Composable
fun InputUIComponent(mutableValueState: State<String>, fontSize: TextUnit) {
    Text(
        text = mutableValueState.value,
        style = MaterialTheme.typography.headlineLarge.copy(
            textAlign = TextAlign.End,
            fontSize = fontSize
        ),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(horizontal = 24.dp),
        color = White
    )
}

@Composable
fun calculateFontSize(text: String, isPortrait: Boolean): TextUnit {
    val baseFontSize = 94.sp
    val maxDigitsBeforeScaling = if (isPortrait) 6 else 9

    val scaleFactor = when {
        text.length <= maxDigitsBeforeScaling -> 1f
        else -> (maxDigitsBeforeScaling.toFloat() / text.length)
    }

    return baseFontSize * scaleFactor
}

@Composable
fun KeyboardUIComponent(
    modifier: Modifier = Modifier,
    buttonState: Operation,
    onNumberChange: (Int) -> Unit,
    onOperatorClick: (Operation) -> Unit,
    buttonFontSize: TextUnit = 32.sp
) {
    BoxWithConstraints {
        val isPortrait = maxWidth < maxHeight

        if (isPortrait) {
            Column(modifier = modifier) {
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    LightOperatorRoundedButton(Operation.AC, buttonFontSize) { onOperatorClick(Operation.AC) }
                    LightOperatorRoundedButton(Operation.PLUS_MINUS, buttonFontSize, onClick = onOperatorClick)
                    LightOperatorRoundedButton(Operation.PERCENTAGE, buttonFontSize, onClick = onOperatorClick)
                    OrangeOperatorRoundedButton(Operation.DIVISION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    NumberRoundedButton("7", onNumberChange, buttonFontSize)
                    NumberRoundedButton("8", onNumberChange, buttonFontSize)
                    NumberRoundedButton("9", onNumberChange, buttonFontSize)
                    OrangeOperatorRoundedButton(Operation.MULTIPLICATION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    NumberRoundedButton("4", onNumberChange, buttonFontSize)
                    NumberRoundedButton("5", onNumberChange, buttonFontSize)
                    NumberRoundedButton("6", onNumberChange, buttonFontSize)
                    OrangeOperatorRoundedButton(Operation.SUBTRACTION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    NumberRoundedButton("1", onNumberChange, buttonFontSize)
                    NumberRoundedButton("2", onNumberChange, buttonFontSize)
                    NumberRoundedButton("3", onNumberChange, buttonFontSize)
                    OrangeOperatorRoundedButton(Operation.ADDITION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    DarkOperatorRoundedButton(Operation.MODE, buttonFontSize)
                    NumberRoundedButton("0", onNumberChange, buttonFontSize)
                    DarkOperatorRoundedButton(Operation.COMMA, buttonFontSize, onClick = onOperatorClick)
                    OrangeOperatorRoundedButton(Operation.EQUALS, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
            }
        }
        else {
            Column(modifier = modifier) {
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    NumberRoundedButton("7", onNumberChange, buttonFontSize)
                    NumberRoundedButton("8", onNumberChange, buttonFontSize)
                    NumberRoundedButton("9", onNumberChange, buttonFontSize)
                    LightOperatorRoundedButton(Operation.AC, buttonFontSize) { onOperatorClick(Operation.AC) }
                    OrangeOperatorRoundedButton(Operation.DIVISION, buttonState, buttonFontSize, onClick = onOperatorClick)

                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    NumberRoundedButton("4", onNumberChange, buttonFontSize)
                    NumberRoundedButton("5", onNumberChange, buttonFontSize)
                    NumberRoundedButton("6", onNumberChange, buttonFontSize)
                    LightOperatorRoundedButton(Operation.PLUS_MINUS, buttonFontSize, onClick = onOperatorClick)
                    OrangeOperatorRoundedButton(Operation.MULTIPLICATION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    NumberRoundedButton("1", onNumberChange, buttonFontSize)
                    NumberRoundedButton("2", onNumberChange, buttonFontSize)
                    NumberRoundedButton("3", onNumberChange, buttonFontSize)
                    LightOperatorRoundedButton(Operation.PERCENTAGE, buttonFontSize, onClick = onOperatorClick)
                    OrangeOperatorRoundedButton(Operation.SUBTRACTION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
                Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
                    DarkOperatorRoundedButton(Operation.MODE, buttonFontSize)
                    NumberRoundedButton("0", onNumberChange, buttonFontSize)
                    DarkOperatorRoundedButton(Operation.COMMA, buttonFontSize, onClick = onOperatorClick)
                    OrangeOperatorRoundedButton(Operation.EQUALS, buttonState, buttonFontSize, onClick = onOperatorClick)
                    OrangeOperatorRoundedButton(Operation.ADDITION, buttonState, buttonFontSize, onClick = onOperatorClick)
                }
            }
        }
    }
}

@Composable
fun LightOperatorRoundedButton(
    button: Operation,
    fontSize: TextUnit,
    onClick: (Operation) -> Unit = { }
) {
    CustomAnimatedButton(
        text = button.symbol,
        textColor = White,
        backgroundColor = LightGray,
        fontSize = fontSize,
        onClick = { onClick.invoke(button) }
    )
}

@Composable
fun DarkOperatorRoundedButton(
    button: Operation,
    fontSize: TextUnit,
    onClick: (Operation) -> Unit = { }
) {
    CustomAnimatedButton(
        text = button.symbol,
        textColor = White,
        backgroundColor = DarkGray,
        fontSize = fontSize,
        onClick = { onClick.invoke(button) }
    )
}

@Composable
fun OrangeOperatorRoundedButton(
    operation: Operation,
    currentOperation: Operation,
    fontSize: TextUnit,
    onClick: (Operation) -> Unit = { }
) {
    val backgroundColor = if (currentOperation == operation) LightOrange else DarkOrange
    CustomAnimatedButton(
        text = operation.symbol,
        textColor = White,
        backgroundColor = backgroundColor,
        fontSize = fontSize,
        onClick = { onClick.invoke(operation) }
    )
}

@Composable
fun NumberRoundedButton(
    text: String,
    onClick: (Int) -> Unit = { },
    fontSize: TextUnit
) {
    CustomAnimatedButton(
        text = text,
        textColor = White,
        backgroundColor = DarkGray,
        fontSize = fontSize,
        onClick = { onClick(text.toInt()) }
    )
}

@Composable
fun CustomAnimatedButton(
    text: String,
    textColor: Color,
    backgroundColor: Color,
    fontSize: TextUnit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val cornerRadius by animateDpAsState(targetValue = if (isPressed.value) 45.dp else 50.dp)

    BoxWithConstraints {
        val modifier = if (maxWidth > maxHeight) {
            Modifier.size(width = 135.dp, height = 45.dp) // Horizontal size
        } else {
            Modifier.size(75.dp) // Vertical size
        }

        Box(
            modifier = modifier
                .background(color = backgroundColor, RoundedCornerShape(cornerRadius))
                .clip(RoundedCornerShape(cornerRadius))
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current
                ) {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = fontSize
            )
        }
    }
}