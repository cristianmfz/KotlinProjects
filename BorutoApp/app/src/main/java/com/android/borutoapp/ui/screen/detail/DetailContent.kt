package com.android.borutoapp.ui.screen.detail

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.android.borutoapp.R
import com.android.borutoapp.domain.model.Hero
import com.android.borutoapp.ui.components.InfoBox
import com.android.borutoapp.ui.components.OrderedList
import com.android.borutoapp.ui.theme.*
import com.android.borutoapp.util.Constants.ABOUT_TEXT_MAX_LINES
import com.android.borutoapp.util.Constants.BASE_URL
import com.android.borutoapp.util.Constants.MIN_BKG_IMAGE_HEIGHT
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    navController: NavHostController,
    selectedHero: Hero?,
    colors: Map<String, String> = emptyMap()
) {
    val vibrant = colors["vibrant"]?.let { Color(it.toColorInt()) } ?: MaterialTheme.colorScheme.primary
    val darkVibrant = colors["darkVibrant"]?.let { Color(it.toColorInt()) } ?: MaterialTheme.colorScheme.surface
    val onDarkVibrant = colors["onDarkVibrant"]?.let { Color(it.toColorInt()) } ?: MaterialTheme.colorScheme.onSurface

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    )

    val currentSheetFraction = calculateSheetFraction(sheetState = scaffoldState.bottomSheetState)

    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f) EXTRA_LARGE_PADDING else EXPANDED_RADIUS_LEVEL
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let {
                BottomSheetContent(
                    selectedHero = it,
                    infoBoxIconColor = vibrant,
                    sheetBackgroundColor = darkVibrant,
                    contentColor = onDarkVibrant
                )
            }
        },
        content = {
            selectedHero?.let { hero ->
                BackgroundContent(
                    heroImage = hero.image,
                    imageFraction = currentSheetFraction,
                    backgroundColor = if (colors.isNotEmpty()) darkVibrant else detailScreenBackgroundColor,
                    onCloseClicked = { navController.popBackStack() }
                )
            }
        },
        sheetContainerColor = darkVibrant,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(color = onDarkVibrant)
        }
        //sheetDragHandle = null
    )
}

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colorScheme.primary,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = titleColor
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(INFO_ICON_SIZE)
                    .weight(2f),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColor
            )
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.day,
                smallText = stringResource(R.string.birthday),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(0.7f)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            maxLines = ABOUT_TEXT_MAX_LINES
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = detailScreenBackgroundColor,
    onCloseClicked: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val imageUrl = "$BASE_URL${heroImage}"
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        error = painterResource(R.drawable.ic_placeholder)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .run {
                    if (isPortrait) {
                        fillMaxHeight(fraction = (imageFraction + MIN_BKG_IMAGE_HEIGHT)
                            .coerceIn(0f, 1f))
                    } else {
                        fillMaxHeight().graphicsLayer(
                            scaleX = 1f + imageFraction * MIN_BKG_IMAGE_HEIGHT,
                            scaleY = 1f + imageFraction * MIN_BKG_IMAGE_HEIGHT
                        )
                    }
                }
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = stringResource(id = R.string.hero_image),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(
                    horizontal = EXTRA_SMALL_PADDING,
                    vertical = INFO_ICON_SIZE
                ),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(INFO_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = topAppBarContentColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun calculateSheetFraction(sheetState: SheetState): Float {
    val currentValue = sheetState.currentValue
    val targetValue = sheetState.targetValue

    val fraction = when {
        currentValue == SheetValue.Expanded && targetValue == SheetValue.Expanded -> 0f
        currentValue == SheetValue.Expanded && targetValue == SheetValue.Hidden -> 0f
        currentValue == SheetValue.Hidden && targetValue == SheetValue.Expanded -> 1f
        currentValue == SheetValue.PartiallyExpanded || currentValue == SheetValue.Hidden -> 1f
        else -> 1f
    }

    return animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    ).value
}

@Composable
@Preview
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 1,
            name = "Naruto",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            rating = 4.5,
            power = 0,
            month = "Oct",
            day = "1st",
            family = listOf("Minato", "Kushina", "Boruto", "Himawari"),
            abilities = listOf("Sage Mode", "Shadow Clone", "Rasengan"),
            natureTypes = listOf("Earth", "Wind")
        )
    )
}