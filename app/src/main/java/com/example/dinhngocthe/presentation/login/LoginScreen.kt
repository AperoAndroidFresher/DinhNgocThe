package com.example.dinhngocthe.presentation.login

import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinhngocthe.model.Users
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.view.InputField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    innerPadding: PaddingValues,
    onSignUp: () -> Unit,
    loginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val trailingIcon = if (state.passwordVisible) R.drawable.ic_show else R.drawable.ic_hidden
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> loginSuccess()
                is LoginEvent.NavigateToSignUp -> onSignUp()
                is LoginEvent.ShowError -> if(event.error != "") Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding())
    ) {
        Header()

        Spacer(Modifier.size(50.dp))

        Main(
            username = state.username,
            onUserNameChange = { viewModel.processIntent(LoginIntent.UsernameChanged(it)) },
            password = state.password,
            onPassWordChange = { viewModel.processIntent(LoginIntent.PasswordChanged(it)) },
            checked = state.rememberMe,
            onCheckedChange = { viewModel.processIntent(LoginIntent.RememberMeChecked(it)) },
            passwordVisible = state.passwordVisible,
            onClickTrailingIcon = { viewModel.processIntent(LoginIntent.TogglePasswordVisible) },
            trailingIcon,
            onLoginClick = { viewModel.processIntent(LoginIntent.LoginClicked) }
        )

        Footer(onSignUp = {viewModel.processIntent(LoginIntent.NavigateToSignUp)})
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
    username: String,
    onUserNameChange: (String) -> Unit,
    password: String,
    onPassWordChange: (String) -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    passwordVisible: Boolean,
    onClickTrailingIcon: () -> Unit,
    trailingIcon: Int,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        InputField(
            name = "user name",
            value = username,
            onValueChange = onUserNameChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_user
        )

        Spacer(Modifier.size(15.dp))

        InputField(
            name = "password",
            value = password,
            onValueChange = onPassWordChange,
            modifier = Modifier.fillMaxWidth(),
            showLabel = false,
            leadingIcon = R.drawable.ic_password,
            trailingIcon = trailingIcon,
            passWordVisible = passwordVisible,
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
            onClick = onLoginClick,
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

//@Preview
//@Composable
//private fun preview() {
//    LoginScreen(0.dp, {}, {})
//}