package tfg.cristian.project.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.center_mark
import me.sample.library.resources.puller1
import me.sample.library.resources.puller2
import me.sample.library.resources.rope
import org.jetbrains.compose.resources.painterResource
import tfg.cristian.project.ui.component.ExitFragment
import tfg.cristian.project.ui.component.ResultDialog
import tfg.cristian.project.ui.component.ScoreFragment
import tfg.cristian.project.ui.component.incrementPlayer1Score
import tfg.cristian.project.ui.component.incrementPlayer2Score

@Composable
fun PullTheRopeScreen(navController: NavHostController) {
    var deltaY by remember { mutableStateOf(0f) }
    var gameOver by remember { mutableStateOf(false) }
    var winnerText by remember { mutableStateOf("") }
    val line1Y = remember { mutableStateOf(0f) }
    val line2Y = remember { mutableStateOf(0f) }
    val centerMarkY = remember { mutableStateOf(0f) }
    val centerMarkHeight = remember { mutableStateOf(0f) }
    val deltaStep = 10f
    val player1Score = remember { mutableStateOf(0) }
    val player2Score = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Contenedor del campo de juego
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF03DAC5))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                if (!gameOver) {
                                    deltaY -= deltaStep
                                    checkWinPuller2(line2Y.value, centerMarkY.value, centerMarkHeight.value, player2Score) {
                                        gameOver = true
                                        winnerText = "Puller 2 Wins!"
                                    }
                                }
                            }
                        )
                    }
                    .onGloballyPositioned {
                        line2Y.value = it.positionInParent().y + it.size.height / 4
                    }
            )

            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF018786))
            )

            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF03DAC5))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                if (!gameOver) {
                                    deltaY += deltaStep
                                    checkWinPuller1(line1Y.value, centerMarkY.value, centerMarkHeight.value, player1Score) {
                                        gameOver = true
                                        winnerText = "Puller 1 Wins!"
                                    }
                                }
                            }
                        )
                    }
                    .onGloballyPositioned {
                        line1Y.value = it.positionInParent().y - it.size.height / 4
                    }
            )
        }

        // Contenedor de imágenes con marca, cuerda y tiradores
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .scale(1.6f)
                .offset(y = deltaY.dp)
                .onGloballyPositioned { coordinates ->
                    centerMarkY.value = coordinates.positionInParent().y
                    centerMarkHeight.value = coordinates.size.height.toFloat()
                }
        ) {
            Image(
                painter = painterResource(Res.drawable.center_mark),
                contentDescription = "Center Mark",
                modifier = Modifier.align(Alignment.Center)
            )

            Image(
                painter = painterResource(Res.drawable.rope),
                contentDescription = "Rope",
                modifier = Modifier.align(Alignment.Center)
            )

            Image(
                painter = painterResource(Res.drawable.puller1),
                contentDescription = "Puller 1",
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            Image(
                painter = painterResource(Res.drawable.puller2),
                contentDescription = "Puller 2",
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        if (gameOver) {
            ResultDialog(
                message = winnerText,
                onRestartGameClick = {
                    restartGame(
                        onDeltaYReset = { deltaY = it },
                        onGameOverReset = { gameOver = it }
                    )
                },
                onMainMenuClick = {
                    gameOver = false
                    navController.navigateUp()
                }
            )
        }

        ExitFragment(navController)
        ScoreFragment(player1Score, player2Score)
    }
}

fun checkWinPuller1(line1Y: Float, centerMarkY: Float, centerMarkHeight: Float, player1Score: MutableState<Int>, onWin: () -> Unit) {
    val centerMarkBottom = centerMarkY + centerMarkHeight / 2
    if (centerMarkBottom >= line1Y) {
        incrementPlayer1Score(player1Score)
        onWin()
    }
}

fun checkWinPuller2(line2Y: Float, centerMarkY: Float, centerMarkHeight: Float, player2Score: MutableState<Int>, onWin: () -> Unit) {
    val centerMarkTop = centerMarkY - centerMarkHeight / 2
    if (centerMarkTop <= line2Y) {
        incrementPlayer2Score(player2Score)
        onWin()
    }
}

fun restartGame(onDeltaYReset: (Float) -> Unit, onGameOverReset: (Boolean) -> Unit) {
    onDeltaYReset(0f)
    onGameOverReset(false)
}