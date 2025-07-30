package com.example.dinhngocthe.presentation.signup

import com.example.dinhngocthe.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.view.InputField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    innerPadding: PaddingValues,
    signUpSuccess: () -> Unit,
    onBack: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { e ->
            when (e) {
                SignUpEvent.BackToLogin  -> onBack()
                SignUpEvent.SignUpSuccess -> signUpSuccess()
            }
        }
    }

    val passwordIcon =
        if (state.passwordVisible) R.drawable.ic_hidden else R.drawable.ic_show
    val confirmIcon =
        if (state.confirmPasswordVisible) R.drawable.ic_hidden else R.drawable.ic_show

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        HeaderSignUp(onBack = { viewModel.processIntent(SignUpIntent.BackToLogin) })

        Spacer(Modifier.size(50.dp))

        MainSignUp(
            username = state.username,
            onUsernameChange = { viewModel.processIntent(SignUpIntent.UsernameChanged(it)) },
            password = state.password,
            onPasswordChange = { viewModel.processIntent(SignUpIntent.PasswordChanged(it)) },
            confirmPassword = state.confirmPassword,
            onConfirmPasswordChange = { viewModel.processIntent(SignUpIntent.ConfirmPasswordChanged(it)) },
            passwordVisible = state.passwordVisible,
            confirmPasswordVisible = state.confirmPasswordVisible,
            onTogglePassword = { viewModel.processIntent(SignUpIntent.TogglePasswordVisible) },
            onToggleConfirmPassword = { viewModel.processIntent(SignUpIntent.ToggleConfirmPasswordVisible) },
            passwordTrailingIcon = passwordIcon,
            confirmPasswordTrailingIcon = confirmIcon,
            email = state.email,
            onEmailChange = { viewModel.processIntent(SignUpIntent.EmailChanged(it)) },
            usernameError = state.usernameError,
            passwordError = state.passwordError,
            confirmPasswordError = state.confirmPasswordError,
            emailError = state.emailError,
            isLoading = state.isLoading,
            onSignUpClick = { viewModel.processIntent(SignUpIntent.SignUpClicked) }
        )
    }
}

@Composable
private fun HeaderSignUp(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
}

@Composable
private fun MainSignUp(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    confirmPasswordVisible: Boolean,
    onTogglePassword: () -> Unit,
    onToggleConfirmPassword: () -> Unit,
    passwordTrailingIcon: Int,
    confirmPasswordTrailingIcon: Int,
    email: String,
    onEmailChange: (String) -> Unit,
    usernameError: String?,
    passwordError: String?,
    confirmPasswordError: String?,
    emailError: String?,
    isLoading: Boolean,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        // Username
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

        // Password
        InputField(
            name = "password",
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = passwordTrailingIcon,
            passWordVisible = passwordVisible,
            onClickTrailingIcon = onTogglePassword
        )
        if (!passwordError.isNullOrEmpty()) {
            Text(
                passwordError,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(Modifier.size(5.dp))

        // Confirm Password
        InputField(
            name = "confirm password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = confirmPasswordTrailingIcon,
            passWordVisible = confirmPasswordVisible,
            onClickTrailingIcon = onToggleConfirmPassword
        )
        if (!confirmPasswordError.isNullOrEmpty()) {
            Text(
                confirmPasswordError,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(Modifier.size(5.dp))

        // Email
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

        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.size(12.dp))
        }

        Button(
            onClick = onSignUpClick,
            enabled = !isLoading,
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
