package com.codewithdipesh.scaleustask.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class Navitem(
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route:String
)