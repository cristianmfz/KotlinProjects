package com.multiplatform.windows95.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multiplatform.windows95.model.FolderModel
import com.multiplatform.windows95.ui.theme.windowsBlue
import org.jetbrains.compose.resources.painterResource

@Composable
fun DraggableFolder(
    folderModel: FolderModel,
    onMove: (Offset) -> Unit,
    onTapFolder: (Int) -> Unit,
    onRename: (String) -> Unit,
    onDoubleTapFolder: (FolderModel) -> Unit
) {
    var offset by remember { mutableStateOf(folderModel.position) }

    var newName by remember { mutableStateOf(folderModel.name) }
    var isEditing by remember { mutableStateOf(false) }
    var lastClickTime by remember { mutableStateOf(0L) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(folderModel) {
        offset = folderModel.position
    }

    Box(Modifier.offset(x = folderModel.position.x.dp, y = folderModel.position.y.dp).width(83.dp)
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, _, _ ->
                offset = offset.copy(x = offset.x + pan.x, y = offset.y + pan.y)
                onMove(offset)
            }
        }.pointerInput(Unit) {
            detectTapGestures(onTap = {
                onTapFolder(folderModel.id)

                val currentTime = System.currentTimeMillis()
                val timeSinceLastClick = currentTime - lastClickTime
                if (timeSinceLastClick in 300..800) {
                    isEditing = true
                }
                lastClickTime = currentTime

            }, onPress = {
                onTapFolder(folderModel.id)
            }, onDoubleTap = {
                onDoubleTapFolder(folderModel)
            })
        }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.size(50.dp)) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(folderModel.icon),
                    contentDescription = "folder"
                )
                if (folderModel.selected) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(folderModel.icon),
                        contentDescription = "folder",
                        tint = windowsBlue.copy(alpha = 0.4f)
                    )
                }
            }
            if (isEditing) {
                BasicTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    modifier = Modifier.focusRequester(focusRequester),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            isEditing = false
                            onRename(newName)
                        }
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.outline)
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

            } else {
                Text(
                    folderModel.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 13.sp,
                    maxLines = 2,
                    style = TextStyle(lineHeight = 0.sp),
                    modifier = Modifier.background(if (folderModel.selected) windowsBlue else Color.Transparent),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row {
                Spacer(Modifier.weight(1f))
            }
        }
        if (isEditing) {
            Box(Modifier.fillMaxSize().pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isEditing = false
                        onRename(newName)
                    }
                )
            })
        }
    }

}