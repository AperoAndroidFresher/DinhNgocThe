package com.example.dinhngocthe.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun InputField(
    name: String = "Input",
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    minLine: Int = 1,
    maxLine: Int = 1
) {
    Column(modifier = modifier) {
        Text(
            text = name.uppercase(),
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 2.dp),
            color = Color.DarkGray,
            fontFamily = AppFonts.mainFontBold
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("${name[0].uppercase()}${name.slice(1 until name.length)}...", fontSize = 14.sp) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color.Black
            ),
            textStyle = TextStyle(
                fontFamily = AppFonts.mainFont,
                fontSize = 14.sp,
                color = Color.Blue
            ),
            singleLine = false,
            minLines = minLine,
            maxLines = maxLine,
            enabled = enable,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )
    }
}
