package tfg.cristian.project.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreFragment(player1Score: MutableState<Int>, player2Score: MutableState<Int>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.CenterEnd // Centrado verticalmente y alineado a la derecha
    ) {
        // Right-Round-Rectangle
        Button(
            onClick = {},
            enabled = false, // Desactivar botón para quitar animación de clic
            colors = ButtonDefaults.buttonColors(disabledContainerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(100),
            modifier = Modifier.size(30.dp, 80.dp)
        ) {}
        val scoreText = "${player2Score.value} | ${player1Score.value}"
        Text(
            text = scoreText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .rotate(90f)
                .padding(bottom = if (scoreText.length > 4) (10 * (scoreText.length - 4)).dp else 0.dp)
        )
    }
}

fun incrementPlayer1Score(player1Score: MutableState<Int>) {
    player1Score.value++
}

fun incrementPlayer2Score(player2Score: MutableState<Int>) {
    player2Score.value++
}