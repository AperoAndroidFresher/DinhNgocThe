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
import com.example.dinhngocthe.data.room.entities.User
import com.example.dinhngocthe.presentation.login.LoginViewModel
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.view.InputField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    innerPadding: PaddingValues,
    signUpSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val app = LocalContext.current.applicationContext as Application
    val viewModel: SignUpViewModel = viewModel(
        factory = remember { SignUpViewModel.SignUpViewModelFactory(app) }
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { e ->
            when (e) {
                SignUpEvent.BackToLogin  -> onBack()
                SignUpEvent.SignUpSuccess -> signUpSuccess()
            }
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val passwordIcon = if (passwordVisible) R.drawable.ic_hidden else R.drawable.ic_show
    val confirmIcon = if (confirmPasswordVisible) R.drawable.ic_hidden else R.drawable.ic_show

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
            username = username,
            onUsernameChange = { username = it },
            password = password,
            onPasswordChange = { password = it },
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = { confirmPassword = it },
            passwordVisible = passwordVisible,
            confirmPasswordVisible = confirmPasswordVisible,
            onTogglePassword = { passwordVisible = !passwordVisible },
            onToggleConfirmPassword = { confirmPasswordVisible = !confirmPasswordVisible },
            passwordTrailingIcon = passwordIcon,
            confirmPasswordTrailingIcon = confirmIcon,
            email = email,
            onEmailChange = { email = it },
            usernameError = state.usernameError,
            passwordError = state.passwordError,
            confirmPasswordError = state.confirmPasswordError,
            emailError = state.emailError,
            isLoading = state.isLoading,
            onSignUpClick = { viewModel.processIntent(SignUpIntent.SignUpClicked(username, password, confirmPassword, email)) }
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
