package tfg.cristian.project.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.autorenew
import me.sample.library.resources.oimage
import me.sample.library.resources.ximage
import org.jetbrains.compose.resources.painterResource
import tfg.cristian.project.ui.component.ResultDialog
import kotlin.random.Random

@Composable
fun TicTacToeScreen(navController: NavHostController) {
    var boxPositions by remember { mutableStateOf(IntArray(9)) }
    var playerTurn by remember { mutableStateOf(1) }
    var totalSelectedBoxes by remember { mutableStateOf(0) }
    var isClickable by remember { mutableStateOf(true) }
    var vsCPU by remember { mutableStateOf(false) }
    var impossibleMode by remember { mutableStateOf(false) }
    var player1Wins by remember { mutableStateOf(0) }
    var player2Wins by remember { mutableStateOf(0) }
    var showResultDialog by remember { mutableStateOf(false) }
    var resultMessage by remember { mutableStateOf("") }

    val isDarkMode = isSystemInDarkTheme()
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF8692F7) // Lavender color background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    PlayerInfo(
                        playerName = "Player 1",
                        playerImage = painterResource(Res.drawable.ximage),
                        playerWins = player1Wins
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    PlayerInfo(
                        playerName = if (vsCPU) "CPU" else "Player 2",
                        playerImage = painterResource(Res.drawable.oimage),
                        playerWins = player2Wins
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Checkbox(
                        checked = vsCPU,
                        onCheckedChange = { isChecked ->
                            vsCPU = isChecked
                            player1Wins = 0
                            player2Wins = 0
                            restartGame(
                                onBoxPositionsReset = { boxPositions = it },
                                onPlayerTurnReset = { playerTurn = it },
                                onTotalSelectedBoxesReset = { totalSelectedBoxes = it },
                                onIsClickableReset = { isClickable = it }
                            )
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "vsCPU",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    if (vsCPU) {
                        Checkbox(
                            checked = impossibleMode,
                            onCheckedChange = { isChecked ->
                                impossibleMode = isChecked
                                restartGame(
                                    onBoxPositionsReset = { boxPositions = it },
                                    onPlayerTurnReset = { playerTurn = it },
                                    onTotalSelectedBoxesReset = { totalSelectedBoxes = it },
                                    onIsClickableReset = { isClickable = it }
                                )
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "Impossible",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    } else {
                        Spacer(modifier = Modifier.width(141.8.dp)) // Espacio en blanco con mismo ancho que el checkbox
                    }
                    FloatingActionButton(
                        onClick = {
                            restartGame(
                                onBoxPositionsReset = { boxPositions = it },
                                onPlayerTurnReset = { playerTurn = it },
                                onTotalSelectedBoxesReset = { totalSelectedBoxes = it },
                                onIsClickableReset = { isClickable = it }
                            )
                        },
                        modifier = Modifier.padding(start = 16.dp),
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.autorenew),
                            contentDescription = "Reset",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
                Board(
                    playerTurn = playerTurn,
                    boxPositions = boxPositions,
                    isClickable = isClickable,
                    onBoxClicked = { boxPosition ->
                        if (isBoxSelectable(boxPosition, boxPositions) && isClickable) {
                            performPlayerAction(
                                boxPosition = boxPosition,
                                playerTurn = playerTurn,
                                boxPositions = boxPositions,
                                onUpdateBoxPositions = { boxPositions = it },
                                onPlayerTurnChange = { playerTurn = it },
                                onTotalSelectedBoxesChange = { totalSelectedBoxes = it },
                                onGameOver = { winner ->
                                    isClickable = false
                                    resultMessage = when (winner) {
                                        1 -> "Player 1 Wins!"
                                        2 -> if (vsCPU) "CPU Wins!" else "Player 2 Wins!"
                                        else -> "Match Draw!" // Empate
                                    }
                                    if (winner == 1) {
                                        player1Wins++
                                    } else if (winner == 2) {
                                        player2Wins++
                                    }
                                    showResultDialog = true
                                },
                                vsCPU = vsCPU,
                                impossibleMode = impossibleMode
                            )
                        }
                    }
                )
            }
            if (showResultDialog) {
                ResultDialog(
                    message = resultMessage,
                    onRestartGameClick = {
                        showResultDialog = false
                        restartGame(
                            onBoxPositionsReset = { boxPositions = it },
                            onPlayerTurnReset = { playerTurn = it },
                            onTotalSelectedBoxesReset = { totalSelectedBoxes = it },
                            onIsClickableReset = { isClickable = it }
                        )
                    },
                    onMainMenuClick = {
                        showResultDialog = false
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun PlayerInfo(playerName: String, playerImage: Painter, playerWins: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .width(90.dp)
    ) {
        Text(
            text = playerName,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF8692F7),
                fontWeight = FontWeight.Bold
            )
        )
        Image(
            painter = playerImage,
            contentDescription = "Player symbol",
            modifier = Modifier
                .size(80.dp)
                .padding(vertical = 16.dp)
        )
        Text(
            text = "Score: $playerWins",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}

@Composable
fun Board(
    playerTurn: Int,
    boxPositions: IntArray,
    isClickable: Boolean,
    onBoxClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(340.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(width = 2.dp, color = Color(0xFF8692F7), shape = RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until 3) {
            Row(modifier = Modifier.weight(1f)) {
                for (j in 0 until 3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(width = 2.dp, color = Color(0xFF8692F7))
                            .background(Color.White)
                            .padding(4.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (isClickable) {
                                            onBoxClicked(i * 3 + j)
                                        }
                                    }
                                )
                            }
                    ) {
                        when (boxPositions[i * 3 + j]) {
                            1 -> Image(
                                painter = painterResource(Res.drawable.ximage),
                                contentDescription = "Player 1 symbol",
                                modifier = Modifier.align(Alignment.Center)
                            )
                            2 -> Image(
                                painter = painterResource(Res.drawable.oimage),
                                contentDescription = "Player 2 symbol",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun performPlayerAction(
    boxPosition: Int,
    playerTurn: Int,
    boxPositions: IntArray,
    onUpdateBoxPositions: (IntArray) -> Unit,
    onPlayerTurnChange: (Int) -> Unit,
    onTotalSelectedBoxesChange: (Int) -> Unit,
    onGameOver: (Int) -> Unit,
    vsCPU: Boolean,
    impossibleMode: Boolean
) {
    val newBoxPositions = boxPositions.copyOf()
    newBoxPositions[boxPosition] = playerTurn
    onUpdateBoxPositions(newBoxPositions)

    val winner = checkResults(newBoxPositions, playerTurn)
    if (winner != 0) {
        onGameOver(winner)
    } else if (newBoxPositions.count { it != 0 } == 9) {
        onGameOver(0) // Draw
    } else {
        val nextPlayerTurn = if (playerTurn == 1) 2 else 1
        onPlayerTurnChange(nextPlayerTurn)
        onTotalSelectedBoxesChange(newBoxPositions.count { it != 0 })

        if (vsCPU && nextPlayerTurn == 2) {
            performCPUMove(
                playerTurn = nextPlayerTurn,
                boxPositions = newBoxPositions,
                onUpdateBoxPositions = onUpdateBoxPositions,
                onPlayerTurnChange = onPlayerTurnChange,
                onTotalSelectedBoxesChange = onTotalSelectedBoxesChange,
                onGameOver = onGameOver,
                impossibleMode = impossibleMode
            )
        }
    }
}

fun performCPUMove(
    playerTurn: Int,
    boxPositions: IntArray,
    onUpdateBoxPositions: (IntArray) -> Unit,
    onPlayerTurnChange: (Int) -> Unit,
    onTotalSelectedBoxesChange: (Int) -> Unit,
    onGameOver: (Int) -> Unit,
    impossibleMode: Boolean
) {
    val playerSign = if (playerTurn == 1) 1 else 2
    val cpuSign = if (playerSign == 1) 2 else 1

    val winningMove = findWinningMove(boxPositions, cpuSign)
    if (winningMove != -1) {
        performPlayerAction(
            boxPosition = winningMove,
            playerTurn = playerTurn,
            boxPositions = boxPositions,
            onUpdateBoxPositions = onUpdateBoxPositions,
            onPlayerTurnChange = onPlayerTurnChange,
            onTotalSelectedBoxesChange = onTotalSelectedBoxesChange,
            onGameOver = onGameOver,
            vsCPU = true,
            impossibleMode = impossibleMode
        )
        return
    }

    val blockingMove = findWinningMove(boxPositions, playerSign)
    if (blockingMove != -1) {
        performPlayerAction(
            boxPosition = blockingMove,
            playerTurn = playerTurn,
            boxPositions = boxPositions,
            onUpdateBoxPositions = onUpdateBoxPositions,
            onPlayerTurnChange = onPlayerTurnChange,
            onTotalSelectedBoxesChange = onTotalSelectedBoxesChange,
            onGameOver = onGameOver,
            vsCPU = true,
            impossibleMode = impossibleMode
        )
        return
    }

    if (impossibleMode) {
        val strategicMove = findStrategicMove(boxPositions)
        if (strategicMove != -1) {
            performPlayerAction(
                boxPosition = strategicMove,
                playerTurn = playerTurn,
                boxPositions = boxPositions,
                onUpdateBoxPositions = onUpdateBoxPositions,
                onPlayerTurnChange = onPlayerTurnChange,
                onTotalSelectedBoxesChange = onTotalSelectedBoxesChange,
                onGameOver = onGameOver,
                vsCPU = true,
                impossibleMode = impossibleMode
            )
            return
        }
    }

    performRandomMove(
        boxPositions = boxPositions,
        playerTurn = playerTurn,
        onUpdateBoxPositions = onUpdateBoxPositions,
        onPlayerTurnChange = onPlayerTurnChange,
        onTotalSelectedBoxesChange = onTotalSelectedBoxesChange,
        onGameOver = onGameOver,
        vsCPU = true,
        impossibleMode = impossibleMode
    )
}

fun findWinningMove(boxPositions: IntArray, sign: Int): Int {
    val combinationList = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    for (combination in combinationList) {
        val count = combination.count { boxPositions[it] == sign }
        if (count == 2) {
            val emptyBox = combination.firstOrNull { boxPositions[it] == 0 }
            if (emptyBox != null) {
                return emptyBox
            }
        }
    }
    return -1
}

fun findStrategicMove(boxPositions: IntArray): Int {
    val strategicMoves = listOf(4, 0, 2, 6, 8)
    for (move in strategicMoves) {
        if (boxPositions[move] == 0) {
            return move
        }
    }
    return -1
}

fun performRandomMove(
    boxPositions: IntArray,
    playerTurn: Int,
    onUpdateBoxPositions: (IntArray) -> Unit,
    onPlayerTurnChange: (Int) -> Unit,
    onTotalSelectedBoxesChange: (Int) -> Unit,
    onGameOver: (Int) -> Unit,
    vsCPU: Boolean,
    impossibleMode: Boolean
) {
    val availablePositions = boxPositions.indices.filter { boxPositions[it] == 0 }
    if (availablePositions.isNotEmpty()) {
        val randomPosition = availablePositions[Random.nextInt(availablePositions.size)]
        performPlayerAction(
            boxPosition = randomPosition,
            playerTurn = playerTurn,
            boxPositions = boxPositions,
            onUpdateBoxPositions = onUpdateBoxPositions,
            onPlayerTurnChange = onPlayerTurnChange,
            onTotalSelectedBoxesChange = onTotalSelectedBoxesChange,
            onGameOver = onGameOver,
            vsCPU = vsCPU,
            impossibleMode = impossibleMode
        )
    }
}

fun checkResults(boxPositions: IntArray, playerTurn: Int): Int {
    val combinationList = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    for (combination in combinationList) {
        if (combination.all { boxPositions[it] == playerTurn }) {
            return playerTurn
        }
    }
    return 0
}

fun isBoxSelectable(boxPosition: Int, boxPositions: IntArray): Boolean {
    return boxPositions[boxPosition] == 0
}

fun restartGame(
    onBoxPositionsReset: (IntArray) -> Unit,
    onPlayerTurnReset: (Int) -> Unit,
    onTotalSelectedBoxesReset: (Int) -> Unit,
    onIsClickableReset: (Boolean) -> Unit
) {
    onBoxPositionsReset(IntArray(9))
    onPlayerTurnReset(1)
    onTotalSelectedBoxesReset(0)
    onIsClickableReset(true)
}