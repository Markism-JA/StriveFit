package com.marky.strivefit.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedFormField(
    label: String,
    modifier: Modifier = Modifier,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val focusState = remember { mutableStateOf(false) }

    val animatedBorderColor by animateColorAsState(
        targetValue = if (focusState.value) focusedBorderColor else unfocusedBorderColor,
        label = "BorderColorAnimation"
    )

    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                focusState.value = it.isFocused
            },
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = animatedBorderColor,
            unfocusedBorderColor = animatedBorderColor,
            focusedLabelColor = animatedBorderColor,
            unfocusedLabelColor = animatedBorderColor
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AnimatedFormFieldPreview() {
    var text by remember { mutableStateOf("") }

    MaterialTheme {
        Column(modifier = Modifier.padding(24.dp)) {
            AnimatedFormField(
                label = "Username",
                text = text,
                onTextChange = { text = it }
            )
            AnimatedFormField(
                label = "Username",
                text = text,
                onTextChange = { text = it }
            )
        }
    }
}
