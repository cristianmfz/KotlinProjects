package com.multiplatform.windows95.ui.screen.windows95

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import com.multiplatform.windows95.ui.components.DraggableFolder
import com.multiplatform.windows95.ui.components.DraggableWindow
import com.multiplatform.windows95.ui.components.RightClickMenu
import com.multiplatform.windows95.ui.screen.windowsbarmenu.WindowsBar
import com.multiplatform.windows95.ui.screen.windowsbarmenu.WindowsBarMenuScreen
import com.multiplatform.windows95.extensions.clickableWithoutRipple
import com.multiplatform.windows95.extensions.onRightClick
import com.multiplatform.windows95.helpers.DefaultFoldersProvider
import com.multiplatform.windows95.model.FolderModel
import com.multiplatform.windows95.model.FolderSortType
import com.multiplatform.windows95.model.WindowModel

@Composable
fun Windows95Screen(isDarkMode: MutableState<Boolean>) {
    var showWindowsMenu by remember { mutableStateOf(false) }
    var folders by remember { mutableStateOf(DefaultFoldersProvider.default) }
    var windows by remember { mutableStateOf(listOf<WindowModel>()) }

    var showRightClickMenu by remember { mutableStateOf(false) }
    var rightClickPosition by remember { mutableStateOf(IntOffset.Zero) }

    Column {
        Box(Modifier.fillMaxWidth().weight(1f).clickableWithoutRipple {
            showWindowsMenu = false
            showRightClickMenu = false
            folders = clearFolders(folders)
        }.onRightClick {
            rightClickPosition = it
            showRightClickMenu = true
        }) {
            folders.forEach { folder ->
                DraggableFolder(folder, onMove = { newPosition ->
                    folders = folders.map {
                        if (it.id == folder.id) it.copy(position = newPosition) else it
                    }
                }, onTapFolder = { id ->
                    folders = folders.map { it.copy(selected = id == it.id) }
                }, onRename = { newName ->
                    folders = folders.map {
                        if (it.id == folder.id) it.copy(name = newName) else it
                    }
                    windows = updateWindowTitleForRenamedFolder(folders, windows)
                }, onDoubleTapFolder = { selectedFolder ->
                    if (windows.any { it.id == selectedFolder.id }) {
                        windows = windows.map {
                            if (it.id == selectedFolder.id) it.copy(
                                selected = true, minimized = false
                            ) else it
                        }
                    } else {
                        val extraPosition = windows.size * 10
                        val currentFolder = findCurrentFolder(selectedFolder.id, folders)
                        val newWindow = WindowModel(
                            id = currentFolder.id,
                            title = currentFolder.name,
                            selected = true,
                            position = Offset(100f + extraPosition, 100f + extraPosition)
                        )
                        windows = windows + newWindow
                    }
                })
            }
            WindowsBarMenuScreen(isDarkMode, showWindowsMenu)
            windows.filter { !it.minimized }.forEach { window ->
                key(window.id) {
                    DraggableWindow(windowModel = window,
                        onMove = { offSet ->
                            windows =
                                windows.map { if (it.id == window.id) it.copy(position = offSet) else it }
                        },
                        onClose = { windows = windows.filter { it.id != window.id } },
                        onMinimize = {
                            windows =
                                windows.map { if (it.id == window.id) it.copy(minimized = true) else it }
                        },
                        onExpand = {
                            windows =
                                windows.map { if (it.id == window.id) it.copy(expanded = !it.expanded) else it }
                        })
                }
            }
            RightClickMenu(showMenu = showRightClickMenu,
                position = rightClickPosition,
                onDismissRequest = { showRightClickMenu = false },
                createNewFolder = {
                    val newFolder = FolderModel(
                        id = folders.size + 1, position = Offset(it.x.toFloat(), it.y.toFloat())
                    )
                    folders = folders + newFolder
                    showRightClickMenu = false
                },
                sortFolders = { sortType ->
                    showRightClickMenu = false
                    folders = when(sortType){
                        FolderSortType.ByName -> folders.sortedBy { it.name }
                        FolderSortType.ByDate -> folders.sortedByDescending { it.createdDate }
                    }.mapIndexed { pos: Int, folder: FolderModel ->
                        folder.copy(
                            position = Offset(
                                y = 10 + (pos * 90).toFloat(),
                                x = 10f
                            )
                        )
                    }
                })
        }
        WindowsBar(windows = windows, onClickMinimizedWindow = { window ->
            windows =
                windows.map { if (window.id == it.id) it.copy(minimized = !it.minimized) else it }
        }) { showWindowsMenu = !showWindowsMenu }
    }
}

fun clearFolders(folders: List<FolderModel>): List<FolderModel> {
    val clearFolders = folders.map { it.copy(selected = false) }
    return clearFolders
}

fun findCurrentFolder(id: Int, folders: List<FolderModel>): FolderModel =
    folders.find { it.id == id } ?: error("Folder not found")

fun updateWindowTitleForRenamedFolder(folders: List<FolderModel>, windows: List<WindowModel>): List<WindowModel> {
    val folderMap = folders.associateBy { it.id }

    return windows.map { window ->
        val updatedFolder = folderMap[window.id]
        if (updatedFolder != null) {
            window.copy(title = updatedFolder.name)
        } else {
            window
        }
    }
}