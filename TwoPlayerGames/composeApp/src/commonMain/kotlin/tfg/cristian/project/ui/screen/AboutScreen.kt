package tfg.cristian.project.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.large_text
import org.jetbrains.compose.resources.stringResource
import tfg.cristian.project.Greeting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val isDarkMode = isSystemInDarkTheme()
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    TopAppBar(
                        title = { Text(
                            text = "About",
                            color = MaterialTheme.colorScheme.onSurface
                        ) },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color(lightColorScheme().primary.value),
                            titleContentColor = Color(lightColorScheme().onPrimary.value)
                        ),
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Default.Home, contentDescription = "Home")
                            }
                        }
                    )

                    AboutContent(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                    )
                }

                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("2playergames@gmail.com")
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Alinear al final del contenido
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Email, contentDescription = "Contact")
                }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 76.dp)
                )
            }
        }
    }
}

@Composable
fun AboutContent(modifier: Modifier = Modifier) {
    val greeting = remember { Greeting().farewell() }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(Res.string.large_text))
                append("\n\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(greeting)
                }
            },
            textAlign = TextAlign.Left
        )
    }
}