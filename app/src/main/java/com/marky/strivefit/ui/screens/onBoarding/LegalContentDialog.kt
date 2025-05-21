package com.marky.strivefit.ui.screens.onBoarding

import LegalContentType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import privacyPolicyText
import termsOfServiceText

@Composable
fun LegalContentDialog(
    type: LegalContentType,
    onDismiss: () -> Unit,
    isLandscape: Boolean
) {
    val title = when (type) {
        LegalContentType.TERMS_OF_SERVICE -> "Terms of Service"
        LegalContentType.PRIVACY_POLICY -> "Privacy Policy"
    }
    val content = when (type) {
        LegalContentType.TERMS_OF_SERVICE -> termsOfServiceText
        LegalContentType.PRIVACY_POLICY -> privacyPolicyText
    }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    val dialogModifier = Modifier.Companion
        .fillMaxHeight(0.85f) // Use 85% of the available height
        .then(
            if (isLandscape) {
                // In landscape, use 90% of the screen width, capped at 800dp.
                // Min width is 320dp.
                Modifier.Companion.widthIn(
                    min = 320.dp,
                    max = min(screenWidthDp * 0.9f, 800.dp) // Corrected logic
                )
            } else {
                // In portrait, use 85% of the screen width, capped at 560dp.
                // Min width is 280dp.
                Modifier.Companion.widthIn(
                    min = 280.dp,
                    max = min(screenWidthDp * 0.85f, 560.dp)
                )
            }
        )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column(
                modifier = Modifier.Companion
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        modifier = dialogModifier
    )
}