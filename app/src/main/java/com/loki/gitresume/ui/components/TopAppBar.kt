package com.loki.gitresume.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onNavigationClick: () -> Unit
) {

    MediumTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(imageVector = Icons.Filled.ArrowBackIos, contentDescription = "arrow_back_icon")
            }
        }
    )
}