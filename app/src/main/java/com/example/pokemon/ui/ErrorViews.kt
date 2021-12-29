package com.example.pokemon.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
@Preview
fun ErrorMessage(@PreviewParameter(MessageProvider::class) message: String) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize() ) {
        Text(text = message)
    }
}

class MessageProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = "Error".lineSequence()
}