package com.example.dinhngocthe.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.R
import com.example.dinhngocthe.presentation.setting.AppLanguage
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.utils.dynamicString

@Composable
fun LanguageDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onChangToEnglish: (String) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier.background(colorResource(R.color.brown)).padding(start = 20.dp, end = 20.dp)
    ) {
        for (language in AppLanguage.languages) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = dynamicString(language),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    )
                },
                onClick = {
                    onChangToEnglish(language)
                    onDismissRequest()
                }
            )
        }
    }
}

