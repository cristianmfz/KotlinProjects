package tfg.cristian.project.ui.screen

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.diamond
import me.sample.library.resources.hand1
import me.sample.library.resources.hand2
import me.sample.library.resources.support
import org.jetbrains.compose.resources.painterResource
import tfg.cristian.project.ui.component.ExitFragment
import tfg.cristian.project.ui.component.ResultDialog
import tfg.cristian.project.ui.component.ScoreFragment
import tfg.cristian.project.TimeProvider
import kotlin.random.Random

@Composable
fun LightFingersScreen(navController: NavHostController) {
    val timeProvider = remember { TimeProvider() }

    var player1Clickable by remember { mutableStateOf(false) }
    var player2Clickable by remember { mutableStateOf(false) }
    var player1Clicked by remember { mutableStateOf(false) }
    var player2Clicked by remember { mutableStateOf(false) }
    var roundClickable by remember { mutableStateOf(true) }
    var winnerText by remember { mutableStateOf("") }
    var backgroundColor by remember { mutableStateOf(Color(0xFF018786)) }
    val diamondPosition by animateFloatAsState(targetValue = if (player1Clicked) 1.445f else if (player2Clicked) 0.555f else 1f)
    val player1Score = remember { mutableStateOf(0) }
    val player2Score = remember { mutableStateOf(0) }
    var roomSize by remember { mutableStateOf(IntSize.Zero) }
    var diamondSize by remember { mutableStateOf(IntSize.Zero) }
    val coroutineScope = rememberCoroutineScope()

    // State for reaction times
    var startTime by remember { mutableStateOf(0L) }
    var reactionTime by remember { mutableStateOf(0L) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .onGloballyPositioned { coordinates ->
                roomSize = coordinates.size
            }
    ) {
        // Contenedor del campo de juego
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                if (roundClickable && player2Clickable && !player2Clicked) {
                                    player2Clicked = true
                                    playerReaction(
                                        timeProvider,
                                        startTime,
                                        onRoundClickableChange = { roundClickable = it },
                                        onWinnerTextChange = { winnerText = it },
                                        onScoreChange = { player2Score.value++ },
                                        player = "Player 2"
                                    )
                                }
                            }
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                if (roundClickable && player1Clickable && !player1Clicked) {
                                    player1Clicked = true
                                    playerReaction(
                                        timeProvider,
                                        startTime,
                                        onRoundClickableChange = { roundClickable = it },
                                        onWinnerTextChange = { winnerText = it },
                                        onScoreChange = { player1Score.value++ },
                                        player = "Player 1"
                                    )
                                }
                            }
                        )
                    }
            )
        }

        // Contenedor de imágenes con soporte, manos y diamante
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    diamondSize = coordinates.size
                }
        ) {
            Image(
                painter = painterResource(Res.drawable.support),
                contentDescription = "Support",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .scale(1.8f)
                    .padding(end = 42.dp)
            )

            Image(
                painter = painterResource(Res.drawable.hand1),
                contentDescription = "Hand 1",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .scale(1.635f)
                    .padding(bottom = 21.dp)
            )

            Image(
                painter = painterResource(Res.drawable.hand2),
                contentDescription = "Hand 2",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .scale(1.635f)
                    .padding(top = 21.dp)
            )

            Image(
                painter = painterResource(Res.drawable.diamond),
                contentDescription = "Diamond",
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(1.8f)
                    .offset {
                        IntOffset(
                            x = (roomSize.width - diamondSize.width) / 2,
                            y = ((roomSize.height * diamondPosition / 2) - (diamondSize.height / 2)).toInt()
                        )
                    }
            )
        }

        if (winnerText.isNotEmpty()) {
            ResultDialog(
                message = winnerText,
                onRestartGameClick = {
                    coroutineScope.launch {
                        restartGame(
                            timeProvider,
                            onPlayer1ClickableChange = { player1Clickable = it },
                            onPlayer2ClickableChange = { player2Clickable = it },
                            onPlayer1ClickedChange = { player1Clicked = it },
                            onPlayer2ClickedChange = { player2Clicked = it },
                            onRoundClickableChange = { roundClickable = it },
                            onWinnerTextChange = { winnerText = it },
                            onBackgroundColorChange = { backgroundColor = it },
                            onStartTimeChange = { startTime = it },
                            onReactionTimeChange = { reactionTime = it }
                        )
                    }
                },
                onMainMenuClick = {
                    winnerText = ""
                    navController.navigateUp()
                }
            )
        }

        ExitFragment(navController)
        ScoreFragment(player1Score, player2Score)
    }

    LaunchedEffect(Unit) {
        startGame(
            timeProvider,
            onPlayer1ClickableChange = { player1Clickable = it },
            onPlayer2ClickableChange = { player2Clickable = it },
            onStartTimeChange = { startTime = it },
            onBackgroundColorChange = { backgroundColor = it }
        )
    }
}

fun playerReaction(
    timeProvider: TimeProvider,
    startTime: Long,
    onRoundClickableChange: (Boolean) -> Unit,
    onWinnerTextChange: (String) -> Unit,
    onScoreChange: () -> Unit,
    player: String
) {
    onRoundClickableChange(false)
    val reactionTime = timeProvider.getCurrentTimeMillis() - startTime
    onWinnerTextChange("$player wins! ${reactionTime}ms")
    onScoreChange()
}

suspend fun restartGame(
    timeProvider: TimeProvider,
    onPlayer1ClickableChange: (Boolean) -> Unit,
    onPlayer2ClickableChange: (Boolean) -> Unit,
    onPlayer1ClickedChange: (Boolean) -> Unit,
    onPlayer2ClickedChange: (Boolean) -> Unit,
    onRoundClickableChange: (Boolean) -> Unit,
    onWinnerTextChange: (String) -> Unit,
    onBackgroundColorChange: (Color) -> Unit,
    onStartTimeChange: (Long) -> Unit,
    onReactionTimeChange: (Long) -> Unit
) {
    // Reset game state
    onPlayer1ClickableChange(false)
    onPlayer2ClickableChange(false)
    onPlayer1ClickedChange(false)
    onPlayer2ClickedChange(false)
    onRoundClickableChange(true)
    onWinnerTextChange("")
    onBackgroundColorChange(Color(0xFF018786))

    // Reset start and reaction times
    onStartTimeChange(0L)
    onReactionTimeChange(0L)

    // Start a new game
    startGame(
        timeProvider,
        onPlayer1ClickableChange = { onPlayer1ClickableChange(it) },
        onPlayer2ClickableChange = { onPlayer2ClickableChange(it) },
        onStartTimeChange = { onStartTimeChange(it) },
        onBackgroundColorChange = { onBackgroundColorChange(it) }
    )
}

suspend fun startGame(
    timeProvider: TimeProvider,
    onPlayer1ClickableChange: (Boolean) -> Unit,
    onPlayer2ClickableChange: (Boolean) -> Unit,
    onStartTimeChange: (Long) -> Unit,
    onBackgroundColorChange: (Color) -> Unit
) {
    val delayTime = Random.nextInt(3000, 8000).toLong()
    delay(delayTime)
    val startTime = timeProvider.getCurrentTimeMillis()
    onStartTimeChange(startTime)
    onPlayer1ClickableChange(true)
    onPlayer2ClickableChange(true)
    onBackgroundColorChange(Color(0xFF121212))
}