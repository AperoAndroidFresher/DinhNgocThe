package com.example.dinhngocthe.ui.view

import com.example.dinhngocthe.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun LoginScreen(
    innerPadding: Dp = 0.dp,
    onSignUp: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var passWordVisible by remember { mutableStateOf(true) }
    var trailingIcon by remember { mutableStateOf(R.drawable.ic_hidden) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(top = innerPadding)
    ) {
        Header()

        Spacer(Modifier.size(50.dp))

        Main(
            userName,
            onUserNameChange = { userName = it },
            passWord,
            onPassWordChange = { passWord = it },
            checked,
            onCheckedChange = {checked = it},
            passWordVisible,
            onClickTrailingIcon = {
                passWordVisible = !passWordVisible
                trailingIcon = if (!passWordVisible) R.drawable.ic_show else R.drawable.ic_hidden
            },
            trailingIcon
        )

        Footer(onSignUp)
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Login to your account",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = AppFonts.mainFontBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun Main(
    userName: String,
    onUserNameChange: (String) -> Unit,
    passWord: String,
    onPassWordChange: (String) -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    passWordVisible: Boolean = true,
    onClickTrailingIcon: () -> Unit,
    trailingIcon: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        InputField(
            name = "user name",
            value = userName,
            onValueChange = onUserNameChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_user
        )

        Spacer(Modifier.size(15.dp))

        InputField(
            name = "password",
            value = passWord,
            onValueChange = onPassWordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = trailingIcon,
            passWordVisible = passWordVisible,
            onClickTrailingIcon = onClickTrailingIcon
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            Text(
                text = "Remember me",
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }

        Spacer(Modifier.size(35.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp)
            )
        }
    }
}

@Composable
fun ColumnScope.Footer(
    onSignUp: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxHeight().align(Alignment.CenterHorizontally).padding(bottom = 60.dp)
    ) {
        Text(
            text = "Don't have an account?",
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            ),
            modifier = Modifier.align(Alignment.Bottom)
        )
        Spacer(Modifier.size(7.dp))
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            ),
            modifier = Modifier.align(Alignment.Bottom).clickable{ onSignUp() }
        )
    }
}

@Preview
@Composable
private fun preview() {
    LoginScreen(0.dp, {})
}