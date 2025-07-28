package com.example.dinhngocthe.ui.view

import com.example.dinhngocthe.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.model.User
import com.example.dinhngocthe.model.Users
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun SignUpScreen(
    innerPadding: PaddingValues,
    signUpSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }
    var passWordVisible by remember { mutableStateOf(true) }
    var passWordTrailingIcon by remember { mutableStateOf(R.drawable.ic_hidden) }
    var confirmPassWord by remember { mutableStateOf("") }
    var confirmPassWordVisible by remember { mutableStateOf(true) }
    var confirmPassWordTrailingIcon by remember { mutableStateOf(R.drawable.ic_hidden) }
    var email by remember { mutableStateOf("") }
    var userNameWarning by remember { mutableStateOf("") }
    var passWordWarning by remember { mutableStateOf("") }
    var confirmPassWordWarning by remember { mutableStateOf("") }
    var emailWarning by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding())
    ) {
        HeaderSignUp(onBack)

        Spacer(Modifier.size(50.dp))

        MainSignUp(
            userName,
            onUserNameChange = {
                userName = it
                userNameWarning = ""
            },
            passWord,
            onPassWordChange = {
                passWord = it
                passWordWarning = ""
            },
            confirmPassWord = confirmPassWord,
            onConfirmPassWordChange = {
                confirmPassWord = it
                confirmPassWordWarning = ""
            },
            passWordVisible =  passWordVisible,
            confirmPassWordVisible = confirmPassWordVisible,
            onClickPassWordTrailingIcon = {
                passWordVisible = !passWordVisible
                passWordTrailingIcon = if (!passWordVisible) R.drawable.ic_show else R.drawable.ic_hidden
            },
            onClickConfirmPassWordTrailingIcon = {
                confirmPassWordVisible = !confirmPassWordVisible
                confirmPassWordTrailingIcon = if (!confirmPassWordVisible) R.drawable.ic_show else R.drawable.ic_hidden
            },
            passWordTrailingIcon,
            confirmPassWordTrailingIcon,
            email = email,
            onEmailChange = {
                email = it
                emailWarning = ""
            },
            userNameWarning = userNameWarning,
            passWordWarning = passWordWarning,
            confirmPassWordWarning = confirmPassWordWarning,
            emailWarning = emailWarning,
            onUserNameWarningChange = {
                userNameWarning = it
                userName = ""
            },
            onPassWordWarningChange = {
                passWordWarning = it
                passWord = ""
            },
            onConfirmPassWordWarningChange = {
                confirmPassWordWarning = it
                confirmPassWord = ""
            },
            onEmailWarningChange = {
                emailWarning = it
                email = ""
            },
            signUpSuccess = signUpSuccess
        )
    }
}

@Composable
fun HeaderSignUp(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onBack
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                modifier = Modifier.size(50.dp).padding(8.dp),
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(Modifier.size(50.dp))

        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = AppFonts.mainFontBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MainSignUp(
    userName: String,
    onUserNameChange: (String) -> Unit,
    passWord: String,
    onPassWordChange: (String) -> Unit,
    confirmPassWord: String,
    onConfirmPassWordChange: (String) -> Unit,
    passWordVisible: Boolean = true,
    confirmPassWordVisible: Boolean = true,
    onClickPassWordTrailingIcon: () -> Unit,
    onClickConfirmPassWordTrailingIcon: () -> Unit,
    passWordTrailingIcon: Int,
    confirmPassWordTrailingIcon: Int,
    email: String,
    onEmailChange: (String) -> Unit,
    userNameWarning: String = "Error",
    passWordWarning: String = "Error",
    confirmPassWordWarning: String = "Error",
    emailWarning: String = "Error",
    onUserNameWarningChange: (String) -> Unit,
    onPassWordWarningChange: (String) -> Unit,
    onConfirmPassWordWarningChange: (String) -> Unit,
    onEmailWarningChange: (String) -> Unit,
    signUpSuccess: () -> Unit
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

        Text(
            userNameWarning,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.size(5.dp))

        InputField(
            name = "password",
            value = passWord,
            onValueChange = onPassWordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = passWordTrailingIcon,
            passWordVisible = passWordVisible,
            onClickTrailingIcon = onClickPassWordTrailingIcon
        )

        Text(
            passWordWarning,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.size(5.dp))

        InputField(
            name = "confirm password",
            value = confirmPassWord,
            onValueChange = onConfirmPassWordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = confirmPassWordTrailingIcon,
            passWordVisible = confirmPassWordVisible,
            onClickTrailingIcon = onClickConfirmPassWordTrailingIcon
        )

        Text(
            confirmPassWordWarning,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.size(5.dp))

        InputField(
            name = "email",
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_email
        )
        Text(
            emailWarning,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(Modifier.size(50.dp))

        Button(
            onClick = {
                submit(
                    userName,
                    passWord,
                    confirmPassWord,
                    email,
                    onUserNameWarningChange,
                    onPassWordWarningChange,
                    onConfirmPassWordWarningChange,
                    onEmailWarningChange,
                    signUpSuccess = signUpSuccess
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp)
            )
        }
    }
}

fun submit(
    userName: String,
    passWord: String,
    confirmPassWord: String,
    email: String,
    onUserNameWarningChange: (String) -> Unit,
    onPassWordWarningChange: (String) -> Unit,
    onConfirmPassWordWarningChange: (String) -> Unit,
    onEmailWarningChange: (String) -> Unit,
    signUpSuccess: () -> Unit
) {
    var hasError = false

    val trimmedUserName = userName.trim()
    val trimmedPassword = passWord.trim()
    val trimmedConfirmPassword = confirmPassWord.trim()
    val trimmedEmail = email.trim()

    val userNameRegex = Regex("^[a-z0-9]+\$", RegexOption.IGNORE_CASE)
    val passwordRegex = Regex("^[a-zA-Z0-9]+\$")
    val emailRegex = Regex("^[a-z0-9._-]+@apero\\.vn\$", RegexOption.IGNORE_CASE)

    // Username validation
    when {
        trimmedUserName.isBlank() -> {
            onUserNameWarningChange("Enter user name")
            hasError = true
        }
        !userNameRegex.matches(trimmedUserName) -> {
            onUserNameWarningChange("Only a–z and 0–9 allowed, no spaces")
            hasError = true
        }
        trimmedUserName.contains(" ") || trimmedUserName.contains("\t") -> {
            onUserNameWarningChange("Username cannot contain spaces or tabs")
            hasError = true
        }
    }

    // Password validation
    when {
        trimmedPassword.isBlank() -> {
            onPassWordWarningChange("Enter password")
            hasError = true
        }
        !passwordRegex.matches(trimmedPassword) -> {
            onPassWordWarningChange("Only a–z, A–Z and 0–9 allowed, no spaces")
            hasError = true
        }
        trimmedPassword.contains(" ") || trimmedPassword.contains("\t") -> {
            onPassWordWarningChange("Password cannot contain spaces or tabs")
            hasError = true
        }
    }

    // Confirm Password validation
    when {
        trimmedConfirmPassword.isBlank() -> {
            onConfirmPassWordWarningChange("Confirm your password")
            hasError = true
        }
        trimmedConfirmPassword != trimmedPassword -> {
            onConfirmPassWordWarningChange("Passwords do not match")
            hasError = true
        }
    }

    // Email validation
    when {
        trimmedEmail.isBlank() -> {
            onEmailWarningChange("Enter email")
            hasError = true
        }
        !emailRegex.matches(trimmedEmail) -> {
            onEmailWarningChange("Invalid email format. Must end with @apero.vn")
            hasError = true
        }
        trimmedEmail.contains(" ") || trimmedEmail.contains("\t") -> {
            onEmailWarningChange("Email cannot contain spaces or tabs")
            hasError = true
        }
    }

    if (!hasError) {
        Users.users.add(User(userName, passWord, email))
        signUpSuccess()
    }
}


//@Preview
//@Composable
//private fun preview() {
//    SignUpScreen(
//        signUpSuccess = {},
//        onBack = {}
//    )
//}