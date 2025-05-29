package tfg.cristian.project.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import me.sample.library.resources.Res
import me.sample.library.resources.mainbkg
import org.jetbrains.compose.resources.painterResource
import tfg.cristian.project.About
import tfg.cristian.project.LightFingers
import tfg.cristian.project.Login
import tfg.cristian.project.NinjaBubble
import tfg.cristian.project.PullTheRope
import tfg.cristian.project.TicTacToe
import tfg.cristian.project.UserRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, username: String, userRepository: UserRepository) {
    val countries = listOf("Global", "Andorra", "Estonia", "France", "Germany", "Ireland", "Italy", "Portugal", "Spain", "Switzerland", "USA")
    val selectedCountry =
        rememberSaveable { mutableStateOf(userRepository.getSelectedCountry() ?: "Global") }
    var isCountryListExpanded by remember { mutableStateOf(false) }

    val isDarkMode = isSystemInDarkTheme()
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f) // Set higher zIndex to overlay Card
            ) {
                TopAppBar(
                    title = { Text("Main Menu") },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(lightColorScheme().primary.value),
                        titleContentColor = Color(lightColorScheme().onPrimary.value)
                    ),
                    actions = {
                        Button(
                            onClick = { isCountryListExpanded = !isCountryListExpanded },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = lightColorScheme().inversePrimary)
                        ) {
                            Text(selectedCountry.value)
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Use weight for flexible spacing
                        Button(
                            onClick = { navController.navigate(Login) },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = lightColorScheme().inversePrimary)
                        ) {
                            Text(username)
                        }
                    }
                )

                if (isCountryListExpanded) {
                    LazyColumn(
                        modifier = Modifier
                            .width(212.dp)
                            .height(400.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(16.dp)
                            )
                            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                    ) {
                        items(countries) { country ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCountry.value = country
                                        userRepository.saveSelectedCountry(country)
                                        isCountryListExpanded = false
                                    }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = country)
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 56.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.mainbkg),
                    contentDescription = "Main Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "2 Player Games",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        GameButton(
                            text = "Tic Tac Toe",
                            onClick = { navController.navigate(TicTacToe) })
                        Spacer(modifier = Modifier.height(20.dp))
                        GameButton(
                            text = "Pull the Rope",
                            onClick = { navController.navigate(PullTheRope) })
                        Spacer(modifier = Modifier.height(20.dp))
                        GameButton(
                            text = "Light Fingers",
                            onClick = { navController.navigate(LightFingers) })
                        Spacer(modifier = Modifier.height(20.dp))
                        GameButton(
                            text = "Ninja Bubble",
                            onClick = { navController.navigate(NinjaBubble) })
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { navController.navigate(About) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .align(alignment = Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text(text = "About", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GameButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}