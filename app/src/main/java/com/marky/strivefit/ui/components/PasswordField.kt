package com.marky.strivefit.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lucide
import com.marky.strivefit.ui.viewModel.PasswordFieldRequirementStatus


@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isFocused: Boolean,
    onFocusChanged: (isFocused: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    errorMessage: String? = null,
    passwordRequirements: List<PasswordFieldRequirementStatus>? = null,
) {
    val areAllRequirementsMet = passwordRequirements?.all { it.isSatisfied } != false

    val colorScheme = MaterialTheme.colorScheme
    val tertiaryColor = colorScheme.tertiary
    val primaryFocusedColor = colorScheme.primary
    val errorColor = colorScheme.error
    val outlineColor = colorScheme.outline

    var fieldInErrorStateWhenUnfocused by remember { mutableStateOf(false) }
    var messageToDisplayBelowField by remember { mutableStateOf<String?>(null) }
    var currentFocusedBorderColor by remember { mutableStateOf(primaryFocusedColor) }

    LaunchedEffect(isFocused, value, errorMessage, passwordRequirements, areAllRequirementsMet, tertiaryColor, primaryFocusedColor) {
        if (isFocused) {
            fieldInErrorStateWhenUnfocused = false
            messageToDisplayBelowField = null

            currentFocusedBorderColor = if (passwordRequirements != null) {
                if (areAllRequirementsMet && value.isNotEmpty()) {
                    tertiaryColor
                } else {
                    primaryFocusedColor
                }
            } else {
                if (errorMessage == null && value.isNotEmpty()) {
                    tertiaryColor
                } else {
                    primaryFocusedColor
                }
            }
        } else {

            if (passwordRequirements != null) {
                if (!areAllRequirementsMet && value.isNotEmpty()) {
                    fieldInErrorStateWhenUnfocused = true
                    messageToDisplayBelowField = "Password does not meet requirements."
                } else {
                    if (errorMessage != null) {
                        fieldInErrorStateWhenUnfocused = true
                        messageToDisplayBelowField = errorMessage
                    } else {
                        fieldInErrorStateWhenUnfocused = false
                        messageToDisplayBelowField = null
                    }
                }
            } else {
                if (errorMessage != null) {
                    fieldInErrorStateWhenUnfocused = true
                    messageToDisplayBelowField = errorMessage
                } else {
                    fieldInErrorStateWhenUnfocused = false
                    messageToDisplayBelowField = null
                }
            }
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChanged(it.isFocused) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = currentFocusedBorderColor,
                unfocusedBorderColor = if (fieldInErrorStateWhenUnfocused) errorColor else outlineColor,
                errorBorderColor = errorColor,
                focusedContainerColor = colorScheme.surfaceVariant,
                unfocusedContainerColor = colorScheme.surfaceVariant,
                cursorColor = primaryFocusedColor,
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        imageVector = if (isPasswordVisible) Lucide.Eye else Lucide.EyeOff,
                        contentDescription = "Toggle password visibility",
                        tint = colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            isError = fieldInErrorStateWhenUnfocused
        )

        if (isFocused && value.isNotEmpty() && !passwordRequirements.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 4.dp,
            ) {
                passwordRequirements.forEach { requirement ->
                    ValidationHintItem(
                        text = requirement.message,
                        isValid = requirement.isSatisfied
                    )
                }
            }
        } else if (messageToDisplayBelowField != null && !isFocused) {
            Text(
                text = messageToDisplayBelowField!!,
                color = errorColor,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun ValidationHintItem(text: String, isValid: Boolean, modifier: Modifier = Modifier) {
    val activeColor = MaterialTheme.colorScheme.tertiary
    val inactiveTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)

    val contentColor = if (isValid) activeColor else inactiveTextColor
    val chipBackgroundColor = if (isValid) {
        activeColor.copy(alpha = 0.15f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val iconSize = MaterialTheme.typography.bodySmall.fontSize.value.dp + 4.dp

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(chipBackgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = if (isValid) "$text: Valid" else "$text: Invalid",
            tint = contentColor,
            modifier = Modifier.size(iconSize)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = contentColor
        )
    }
}