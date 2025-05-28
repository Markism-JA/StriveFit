package com.marky.strivefit.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.EyeOff
import com.composables.icons.lucide.Lucide

data class PasswordFieldRequirementStatus(
    val message: String,
    val isValid: Boolean
)

@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isFocused: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    errorMessage: String? = null,
    passwordRequirements: List<PasswordFieldRequirementStatus>? = null,
) {
    val areAllRequirementsMet = passwordRequirements?.all { it.isValid } ?: true
    val showErrorState = errorMessage != null || (isFocused && passwordRequirements != null && !areAllRequirementsMet && value.isNotEmpty())

    val borderWidth by animateFloatAsState(
        targetValue = if (isFocused) 2f else 1f,
        label = "borderWidth"
    )

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChanged(it.isFocused) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (showErrorState && isFocused) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (showErrorState && !isFocused && value.isNotEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
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
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            isError = showErrorState
        )
        if (errorMessage == null && passwordRequirements != null && passwordRequirements.isNotEmpty() && isFocused && value.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
               modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                mainAxisSpacing = 8.dp, //Spacing between column
                crossAxisSpacing = 4.dp, //Spacing between row
            ) {
                passwordRequirements.forEach { requirement ->
                    ValidationHintItem(
                        text = requirement.message,
                        isValid = requirement.isValid
                    )
                }

            }
        } else if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
private fun ValidationHintItem(text: String, isValid: Boolean, modifier: Modifier = Modifier) {
    val activeColor = Color(0xFF4CAF50)
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = if (isValid) activeColor else inactiveColor,
            modifier = Modifier.padding(end = 4.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (isValid) activeColor else inactiveColor
        )
    }
}