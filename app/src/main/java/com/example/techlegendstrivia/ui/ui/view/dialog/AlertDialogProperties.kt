package com.example.techlegendstrivia.ui.ui.view.dialog

import androidx.compose.ui.graphics.vector.ImageVector

data class AlertDialogProperties(
    val dialogType: AlertDialogType,
    val title: String,
    val text: String,
    val icon: ImageVector
) {

}