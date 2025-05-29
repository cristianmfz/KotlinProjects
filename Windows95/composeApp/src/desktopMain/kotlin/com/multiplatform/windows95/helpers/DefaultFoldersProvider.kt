package com.multiplatform.windows95.helpers

import androidx.compose.ui.geometry.Offset
import com.multiplatform.windows95.model.FolderModel
import windows95.composeapp.generated.resources.Res
import windows95.composeapp.generated.resources.ic_aoe
import windows95.composeapp.generated.resources.ic_bin
import windows95.composeapp.generated.resources.ic_ie
import windows95.composeapp.generated.resources.ic_my_computer
import windows95.composeapp.generated.resources.ic_network

object DefaultFoldersProvider {
    val default = listOf(
        FolderModel(0, "My Computer", Offset(x = 10f, y = 10f), icon = Res.drawable.ic_my_computer),
        FolderModel(
            1, "Network Neighborhood", Offset(x = 10f, y = 90f), icon = Res.drawable.ic_network
        ),
        FolderModel(
            2, "Internet Explorer", Offset(x = 10f, y = 190f), icon = Res.drawable.ic_ie
        ),
        FolderModel(
            3,
            "Microsoft Age of Empires II",
            Offset(x = 10f, y = 290f),
            icon = Res.drawable.ic_aoe
        ),
        FolderModel(
            4, "Recycle Bin", Offset(x = 1820f, y = 950f), icon = Res.drawable.ic_bin
        ),
    )
}