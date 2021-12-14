package com.compose.template.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .size(35.dp)
            .wrapContentSize(Alignment.Center),
        strokeWidth = 5.dp
    )
}