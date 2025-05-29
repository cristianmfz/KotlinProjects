package com.android.firebasesongs.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.firebasesongs.Main
import com.android.firebasesongs.R
import com.android.firebasesongs.ui.model.Artist
import com.android.firebasesongs.ui.model.Player
import com.android.firebasesongs.ui.theme.Black
import com.android.firebasesongs.ui.theme.Green
import com.android.firebasesongs.ui.theme.Purple40
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = HomeViewModel()) {
    val artists by remember { viewModel.artist }.collectAsState()
    val player by viewModel.player.collectAsState()
    val blockVersion by viewModel.blockVersion.collectAsState()

    if (blockVersion) {
        val context = LocalContext.current
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Actualización",
                        fontSize = 22.sp,
                        color = Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Para poder disfrutar de todo el contenido actualice la app",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { navigateToPlayStore(context) }) {
                        Text(text = "¡Actualizar!")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Popular artists",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Main) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Green)
            ) {
                Text("Log Out",
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        LazyRow(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(artists, key = { artist -> artist.name.orEmpty() + artist.hashCode() }) { artist ->
                ArtistItem(artist = artist) {
                    viewModel.addPlayer(artist)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        player?.artist?.let { artist ->
            ArtistDetail(artist)
        }

        Spacer(modifier = Modifier.weight(1f))
        player?.let {
            PlayerComponent(player = it,
                onCancelSelected = { viewModel.onCancelSelected() },
                onPlaySelected = { viewModel.onPlaySelected() })
        }
    }
}

@Composable
fun ArtistDetail(artist: Artist) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {
        Text(
            text = artist.name.orEmpty(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = artist.description.orEmpty(),
            fontSize = 18.sp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            model = artist.image,
            contentDescription = "Artist Image",
        )
    }
}

@Composable
fun PlayerComponent(player: Player, onPlaySelected: () -> Unit, onCancelSelected: () -> Unit) {
    val icon = if (player.play == true) R.drawable.ic_pause else R.drawable.ic_play

    Row(
        Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(Purple40),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.artist?.name.orEmpty(),
            modifier = Modifier.padding(start = 12.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = icon),
            contentDescription = "Play/Pause",
            modifier = Modifier
                .size(40.dp)
                .clickable { onPlaySelected() })
        Image(painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close",
            modifier = Modifier
                .padding(end = 12.dp)
                .size(40.dp)
                .clickable { onCancelSelected() })
    }
}

@Composable
fun ArtistItem(artist: Artist, onItemSelected: (Artist) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onItemSelected(artist) }) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = artist.image,
            contentDescription = "Artists image",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = artist.name.orEmpty(), color = Color.White)
    }
}

@Preview
@Composable
fun ArtistItemPreview() {
    val artist = Artist(
        "Benito",
        "El conejo",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTI-U7dNu2SSAYrjaM6tx3vKTWfgJ2KeaLgWH-tdF6dx8rcLxo0",
        //emptyList()
    )
    ArtistItem(artist = artist) {}
}

fun navigateToPlayStore(context: Context) {
    val appPackage = context.packageName
    try {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$appPackage")
            )
        )
    } catch (e: Exception) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")
            )
        )

    }
}

/*fun createArtists(db: FirebaseFirestore) {
    val random = (1..1000).random()
    val artist = Artist(name = "Artist $random", numberOfSongs = random)

    db.collection("artists").add(artist)
        .addOnSuccessListener {
            Log.i("Cristian", "SUCCESS")
        }
        .addOnFailureListener {
            Log.i("Cristian", "FAILURE")
        }
        .addOnCompleteListener {
            Log.i("Cristian", "COMPLETE")
        }
}*/