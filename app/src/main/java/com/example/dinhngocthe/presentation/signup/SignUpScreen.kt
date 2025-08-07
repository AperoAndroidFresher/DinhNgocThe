package com.example.dinhngocthe.presentation.signup

import android.app.Application
import com.example.dinhngocthe.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.components.InputField
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: SignUpViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { e ->
            when (e) {
                SignUpEvent.NavigateToLogin  -> onBack()
                SignUpEvent.SignUpSuccess -> onBack()
            }
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    val passwordTrailingIcon = if (isPasswordVisible) R.drawable.ic_hidden else R.drawable.ic_show
    val confirmPasswordTrailingIcon = if (isConfirmPasswordVisible) R.drawable.ic_hidden else R.drawable.ic_show

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        HeaderSignUp(
            modifier = Modifier,
            onBack = { viewModel.processIntent(SignUpIntent.NavigateToLogin) }
        )

        MainSignUp(
            username = username,
            password = password,
            confirmPassword = confirmPassword,
            isPasswordVisible = isPasswordVisible,
            isConfirmPasswordVisible = isConfirmPasswordVisible,
            passwordTrailingIcon = passwordTrailingIcon,
            confirmPasswordTrailingIcon = confirmPasswordTrailingIcon,
            email = email,
            usernameError = state.usernameError,
            passwordError = state.passwordError,
            confirmPasswordError = state.confirmPasswordError,
            emailError = state.emailError,
            modifier = Modifier,
            onUsernameChange = { username = it },
            onPasswordChange = { password = it },
            onConfirmPasswordChange = { confirmPassword = it },
            onChangePasswordVisibleState = { isPasswordVisible = !isPasswordVisible },
            onChangeConfirmPasswordVisibleState = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
            onEmailChange = { email = it },
            onSignUpClick = { viewModel.processIntent(SignUpIntent.SignUpClicked(username, password, confirmPassword, email)) }
        )
    }
}

@Composable
private fun HeaderSignUp(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        IconButton(onClick = onBack) {
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

    Spacer(Modifier.size(50.dp))
}

@Composable
private fun MainSignUp(
    username: String,
    password: String,
    confirmPassword: String,
    isPasswordVisible: Boolean,
    passwordTrailingIcon: Int,
    confirmPasswordTrailingIcon: Int,
    isConfirmPasswordVisible: Boolean,
    email: String,
    usernameError: String?,
    passwordError: String?,
    confirmPasswordError: String?,
    emailError: String?,
    modifier: Modifier = Modifier,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onChangePasswordVisibleState: () -> Unit,
    onChangeConfirmPasswordVisibleState: () -> Unit,
    onEmailChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        InputField(
            name = "user name",
            value = username,
            onValueChange = onUsernameChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_user
        )
        if (!usernameError.isNullOrEmpty()) {
            Text(
                usernameError,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(Modifier.size(5.dp))

        InputField(
            name = "password",
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = passwordTrailingIcon,
            passWordVisible = isPasswordVisible,
            onClickTrailingIcon = onChangePasswordVisibleState
        )
        if (!passwordError.isNullOrEmpty()) {
            Text(
                passwordError,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(Modifier.size(5.dp))

        InputField(
            name = "confirm password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = confirmPasswordTrailingIcon,
            passWordVisible = isConfirmPasswordVisible,
            onClickTrailingIcon = onChangeConfirmPasswordVisibleState
        )
        if (!confirmPasswordError.isNullOrEmpty()) {
            Text(
                confirmPasswordError,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(Modifier.size(5.dp))

        InputField(
            name = "email",
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_email
        )
        if (!emailError.isNullOrEmpty()) {
            Text(
                emailError,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(Modifier.size(30.dp))

        Button(
            onClick = onSignUpClick,
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
