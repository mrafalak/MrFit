package com.mrapps.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mrapps.main.util.text.capitalizeFirstLetter
import com.mrapps.presentation.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CommonExposedDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<T>,
    labelResId: Int,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    enabled: Boolean = true,
    error: UiText? = null,
    optionToString: (T) -> String = { it.toString().capitalizeFirstLetter() }
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        CommonTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled)
                .fillMaxWidth(),
            value = selectedOption?.let { optionToString(it) } ?: "",
            labelResId = labelResId,
            readOnly = true,
            error = error,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = optionToString(selectionOption)) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}