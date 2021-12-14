package com.compose.template.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.compose.template.domain.Post

@Composable
fun PostRow(post: Post) {

    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = post.title, style = MaterialTheme.typography.body1,
            color = Color.Black, fontWeight = FontWeight.SemiBold
        )
        Text(text = post.body, style = MaterialTheme.typography.body2)
    }


}