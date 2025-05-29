package tfg.cristian.project.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.compose_multiplatform
import me.sample.library.resources.loginbkg
import me.sample.library.resources.visibility
import me.sample.library.resources.visibility_off
import org.jetbrains.compose.resources.painterResource
import tfg.cristian.project.AuthResult
import tfg.cristian.project.AuthService
import tfg.cristian.project.UserRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    userRepository: UserRepository,
    onLoginSuccess: (String) -> Unit,
    onLogoutSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val authService = remember { AuthService() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val isUserAuthenticated = remember { mutableStateOf(authService.isUserAuthenticated()) }

    val isDarkMode = isSystemInDarkTheme()
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MaterialTheme(colorScheme) {
                TopAppBar(
                    title = { Text(
                        text = "Login",
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
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Background
                Image(
                    painter = painterResource(Res.drawable.loginbkg),
                    contentDescription = "Login Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.Center)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icon
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "Login Icon",
                        modifier = Modifier
                            .size(128.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email Input
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Input
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        trailingIcon = {
                            val icon = if (passwordVisibility) Res.drawable.visibility_off else Res.drawable.visibility
                            val description = if (passwordVisibility) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(painter = painterResource(icon), contentDescription = description)
                            }
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sign In Button
                    Button(
                        onClick = {
                            if (!isUserAuthenticated.value) {
                                loading = true
                                message = ""
                                isError = false
                                coroutineScope.launch {
                                    authService.signInWithEmail(email, password, coroutineScope).collect { result ->
                                        loading = false
                                        when (result) {
                                            is AuthResult.Success -> {
                                                message = "Sign in successful"
                                                isError = false
                                                isUserAuthenticated.value = true
                                                userRepository.saveLoggedInState(email)
                                                onLoginSuccess(email)
                                                snackbarHostState.showSnackbar(message, "OK")
                                            }
                                            is AuthResult.Error -> {
                                                message = "Sign in failed: ${result.message}"
                                                isError = true
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        enabled = !isUserAuthenticated.value
                    ) {
                        Text("Sign in")
                    }

                    /*Spacer(modifier = Modifier.height(16.dp))

                    // Sign In with Google Button
                    Button(
                        onClick = {
                            runCatching { TODO("Not yet implemented") }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text("Sign in with Google")
                    }*/

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show loading indicator and message
                    if (loading) {
                        CircularProgressIndicator()
                    }
                    if (message.isNotEmpty()) {
                        Text(
                            message,
                            color = if (isError) Color.Red else Color.Green,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    // Bottom Buttons Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        // Log Out Button
                        Button(
                            onClick = {
                                if (isUserAuthenticated.value) {
                                    loading = true
                                    message = ""
                                    isError = false
                                    coroutineScope.launch {
                                        authService.signOut().collect { result ->
                                            loading = false
                                            when (result) {
                                                is AuthResult.Success -> {
                                                    message = "Logged out"
                                                    isError = false
                                                    isUserAuthenticated.value = false
                                                    userRepository.clearLoggedInState()
                                                    onLogoutSuccess()
                                                    snackbarHostState.showSnackbar(message, "OK")
                                                }
                                                is AuthResult.Error -> {
                                                    message = "Error logging out: ${result.message}"
                                                    isError = true
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            enabled = isUserAuthenticated.value
                        ) {
                            Text("Log out")
                        }

                        // Create Account Button
                        Button(
                            onClick = {
                                loading = true
                                message = ""
                                isError = false
                                coroutineScope.launch {
                                    authService.createAccountWithEmail(email, password, coroutineScope).collect { result ->
                                        loading = false
                                        when (result) {
                                            is AuthResult.Success -> {
                                                message = "Account creation successful"
                                                isError = false
                                            }
                                            is AuthResult.Error -> {
                                                message = "Account creation failed: ${result.message}"
                                                isError = true
                                            }
                                        }
                                    }
                                }
                            },
                        ) {
                            Text("Create Account")
                        }
                    }
                }
            }
        }
    )
}