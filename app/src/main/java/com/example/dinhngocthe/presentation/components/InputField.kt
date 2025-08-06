package com.example.dinhngocthe.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    name: String = "Input",
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    minLine: Int = 1,
    maxLine: Int = 1,
    showLabel: Boolean = true,
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    onClickTrailingIcon: () -> Unit = {},
    passWordVisible: Boolean = true
) {
    Column(modifier = modifier) {
        if (showLabel) {
            Text(
            text = name.uppercase(),
            modifier = Modifier.padding(start = 2.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("${name[0].uppercase()}${name.slice(1 until name.length)}", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                focusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondaryContainer,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            singleLine = false,
            minLines = minLine,
            maxLines = maxLine,
            enabled = enable,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            leadingIcon = if (leadingIcon != null) {
                {
                    Icon(
                        painter = painterResource(leadingIcon),
                        contentDescription = "Leading icon",
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else null,
            trailingIcon = if (trailingIcon != null) {
                {
                    IconButton(onClick = onClickTrailingIcon) {
                        Icon(
                            painter = painterResource(trailingIcon),
                            contentDescription = "Trailing icon",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            } else null,
            visualTransformation = if (passWordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
}
