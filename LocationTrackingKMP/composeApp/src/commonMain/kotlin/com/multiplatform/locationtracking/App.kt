package com.multiplatform.locationtracking

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.jordond.compass.Coordinates
import dev.jordond.compass.Place
import dev.jordond.compass.Priority
import dev.jordond.compass.autocomplete.Autocomplete
import dev.jordond.compass.autocomplete.mobile
import dev.jordond.compass.geocoder.MobileGeocoder
import dev.jordond.compass.geocoder.placeOrNull
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.LocationRequest
import dev.jordond.compass.geolocation.TrackingStatus
import dev.jordond.compass.geolocation.isPermissionDeniedForever
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val isDarkMode = isSystemInDarkTheme()
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val geoLocation = remember { Geolocator.mobile() }
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                when (val result = geoLocation.current()) {
                    is GeolocatorResult.Success -> {
                        println("LOCATION: ${result.data.coordinates}")
                        println("LOCATION NAME: ${MobileGeocoder().placeOrNull(result.data.coordinates)?.locality}")
                    }

                    is GeolocatorResult.Error -> when (result) {
                        is GeolocatorResult.NotSupported -> println("LOCATION ERROR: ${result.message}")
                        is GeolocatorResult.NotFound -> println("LOCATION ERROR: ${result.message}")
                        is GeolocatorResult.PermissionError -> println("LOCATION ERROR: ${result.message}")
                        is GeolocatorResult.GeolocationFailed -> println("LOCATION ERROR: ${result.message}")
                        else -> println("LOCATION ERROR: ${result.message}")
                    }
                }
            }

            NavHost(navController = navController, startDestination = Home) {
                composable<Home> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LocationTracking()
                        Spacer(modifier = Modifier.height(20.dp))
                        PlacesAutocomplete()
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = { navController.navigate(Maps) }) {
                            Text("Maps")
                        }
                    }
                }
                composable<Maps> { MapsScreen(navController) }
            }
        }
    }
}

@Composable
fun LocationTracking() {
    val scope = rememberCoroutineScope()
    val geoLocator = remember { Geolocator.mobile() }
    val trackingStatus by geoLocator.trackingStatus.collectAsState(initial = null)
    var currentLocation: Coordinates? by remember { mutableStateOf(null) }
    var currentCity: String? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        geoLocator.locationUpdates.collectLatest {
            currentLocation = it.coordinates
            currentCity = MobileGeocoder().placeOrNull(it.coordinates)?.locality
        }
    }

    LaunchedEffect(Unit) {
        geoLocator.trackingStatus.collectLatest { status ->
            when (status) {
                is TrackingStatus.Idle -> {}
                is TrackingStatus.Tracking -> {}
                is TrackingStatus.Update -> {}
                is TrackingStatus.Error -> {
                    val error: GeolocatorResult.Error = status.cause
                    println("TRACKING ERROR: $error")
                    // Show the permissions settings screen
                    val permissionDeniedForever = error.isPermissionDeniedForever()
                    println("TRACKING PERMISSION DENIED: $permissionDeniedForever")
                }
            }
        }
    }

    Text(
        text = "Location Tracking",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = currentCity ?: "Waiting..."
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "LAT: ${currentLocation?.latitude}\nLNG: ${currentLocation?.longitude}"
    )
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            enabled = trackingStatus == TrackingStatus.Idle,
            onClick = {
                scope.launch(Dispatchers.IO) {
                    geoLocator.startTracking(
                        LocationRequest(
                            Priority.HighAccuracy
                        )
                    )
                }
            }
        ) {
            Text(text = "Start")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(
            enabled = trackingStatus != TrackingStatus.Idle,
            onClick = {
                scope.launch(Dispatchers.IO) {
                    geoLocator.stopTracking()
                    currentLocation = null
                    currentCity = null
                }
            }
        ) {
            Text(text = "Stop")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesAutocomplete() {
    val scope = rememberCoroutineScope()
    val autocomplete = remember { Autocomplete.mobile() }
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val places = remember { mutableStateListOf<Place?>() }
    var selectedPlace: Place? by remember { mutableStateOf(null) }

    Text(
        text = "Places Autocomplete",
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = if (selectedPlace != null)
            "${selectedPlace?.locality} (${selectedPlace?.country})\n" +
                    "LAT: ${selectedPlace?.coordinates?.latitude}\n" +
                    "LNG: ${selectedPlace?.coordinates?.longitude}"
        else "No place selected"
    )
    Spacer(modifier = Modifier.height(12.dp))

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded } // Toggle dropdown expansion
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                expanded = it.isNotEmpty() // Show dropdown if the search query is not empty
                if (expanded) {
                    scope.launch {
                        autocomplete.search(searchQuery).getOrNull()?.let { result ->
                            places.clear()
                            places.addAll(result.toList())
                        }
                    }
                }
            },
            label = { Text("Search place") },
            trailingIcon = {
                if (expanded) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Collapse"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    scope.launch {
                        autocomplete.search(searchQuery).getOrNull()?.let {
                            places.clear()
                            places.addAll(it.toList())
                        }
                    }
                    expanded = !expanded
                }
            ),
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
        )

        ExposedDropdownMenu(
            expanded = expanded && places.isNotEmpty(),
            onDismissRequest = { expanded = false }
        ) {
            places.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(text = if (selectionOption?.locality != null)
                            "${selectionOption.locality} (${selectionOption.country})"
                        else "Unknown place")
                    },
                    onClick = {
                        selectedPlace = selectionOption
                        expanded = false
                    }
                )
            }
        }
    }
}

/*@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}*/