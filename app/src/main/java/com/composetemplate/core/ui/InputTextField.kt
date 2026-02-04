package com.composetemplate.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun InputTextField(
    text: String,
    label: String = "",
    type: InputTextFieldType = InputTextFieldType.Classic,
    icon: ImageVector = Icons.AutoMirrored.Filled.Send,
    enabled: Boolean = true,
    maxLine: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions(),
    modifier: Modifier = Modifier,
    searchQuery: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    when (type) {
        InputTextFieldType.Classic -> TextField(
            value = text,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            enabled = enabled,
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            ),
            shape = MaterialTheme.shapes.extraSmall,
            placeholder = { Text(text = label) },
            maxLines = maxLine
        )

        InputTextFieldType.Outlined -> OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            label = { Text(label) },
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(),
            shape = MaterialTheme.shapes.small,
            maxLines = maxLine
        )

        InputTextFieldType.WithIcon -> OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            label = { Text(label) },
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            colors = OutlinedTextFieldDefaults.colors(),
            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            maxLines = maxLine
        )

        InputTextFieldType.IconClickable -> OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            leadingIcon = {
                IconButton(onClick = searchQuery) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            label = { Text(label) },
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            colors = OutlinedTextFieldDefaults.colors(),
            enabled = enabled,
            shape = MaterialTheme.shapes.small,
            maxLines = maxLine
        )
    }
}

@Preview
@Composable
fun PreviewOutlinedTextField() {
    AppTheme {
        InputTextField(text = "Outlined", type = InputTextFieldType.Outlined) {}
    }
}

@Preview
@Composable
fun PreviewClassicTextField() {
    AppTheme {
        InputTextField(text = "Classic", type = InputTextFieldType.Classic) {}
    }
}

@Preview
@Composable
fun PreviewWithIconTextField() {
    AppTheme {
        InputTextField(
            text = "With Icon",
            type = InputTextFieldType.WithIcon,
            icon = Icons.AutoMirrored.Filled.Send
        ) {}
    }
}

enum class InputTextFieldType {
    Classic, Outlined, WithIcon, IconClickable
}