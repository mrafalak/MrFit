package com.mrapps.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mrapps.presentation.UiText

@Composable
fun CommonTextField(
    modifier: Modifier = Modifier,
    value: String,
    labelResId: Int,
    error: UiText? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(labelResId))
        },
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        readOnly = readOnly,
        isError = error != null,
        supportingText = {
            if (error != null) {
                Text(
                    text = error.asString(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        trailingIcon = trailingIcon
    )
}