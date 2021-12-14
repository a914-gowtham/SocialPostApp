package com.compose.template.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView(errorMessage: String, onBtnClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            errorMessage, style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center, fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = onBtnClick
        ) {
            Text("Retry")
        }
    }
}