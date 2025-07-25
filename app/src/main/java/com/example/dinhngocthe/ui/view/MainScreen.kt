package com.example.dinhngocthe.ui.view

import android.content.res.Resources
import androidx.compose.foundation.Image
import com.example.dinhngocthe.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.ui.theme.AppFonts
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onChangeMode: () -> Unit,
    isDarkTheme: Boolean,
    innerPadding: Dp
) {
    var txtName by remember { mutableStateOf("") }
    var txtPhoneNumber by remember { mutableStateOf("") }
    var txtUniversityName by remember { mutableStateOf("") }
    var txtDescribeYourSelf by remember { mutableStateOf("") }
    var enableEditing by remember { mutableStateOf(false) }
    var showEditButton by remember { mutableStateOf(true) }
    var nameWarning by remember { mutableStateOf("") }
    var phoneNumberWarning by remember { mutableStateOf("") }
    var universityNameWarning by remember { mutableStateOf("") }
    var onDismissSuccessDialog by remember { mutableStateOf(false) }
    var icChangeMode by remember { mutableStateOf(R.drawable.ic_dark_mode ) }
    icChangeMode = if (isDarkTheme == false) R.drawable.ic_dark_mode else R.drawable.ic_light_mode

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = innerPadding)
    ) {
        Header(
            onEdit = {
                enableEditing = !enableEditing
                showEditButton = !showEditButton
            },
            showEditButton = showEditButton,
            onChangeMode = {
                onChangeMode()
                icChangeMode = if (isDarkTheme == true) R.drawable.ic_dark_mode else R.drawable.ic_light_mode
            },
            icChangeMode
        )

        Spacer(modifier = Modifier.height(30.dp))

        Main(
            txtName,
            onNameChange = { txtName = it },
            txtPhoneNumber,
            onPhoneNumberChange = { txtPhoneNumber = it },
            txtUniversityName,
            onUniversityNameChange = { txtUniversityName = it },
            txtDescribeYourSelf,
            onDescribeYourSelfChange = { txtDescribeYourSelf = it },
            nameWarning,
            phoneNumberWarning,
            universityNameWarning,
            enableEditing
        )

        Spacer(modifier = Modifier.height(50.dp))

        if (enableEditing) {
            Button(
                onClick = {
                    submit(
                        txtName,
                        txtPhoneNumber,
                        txtUniversityName,
                        { nameWarning = it },
                        { phoneNumberWarning = it },
                        { universityNameWarning = it },
                        { onDismissSuccessDialog = it }
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "Submit",
                    fontSize = 16.sp,
                    fontFamily = AppFonts.mainFontBold,
                )
            }
        }

        if (onDismissSuccessDialog) {
            SuccessDialog(
                onDismiss = {
                    onDismissSuccessDialog
                },
                "Your information has been updated"
            )
            LaunchedEffect(Unit) {
                delay(2000)
                onDismissSuccessDialog = false
            }
        }
    }
}

@Composable
fun ColumnScope.Header(
    onEdit: () -> Unit,
    showEditButton: Boolean,
    onChangeMode: () -> Unit,
    icChangeMode: Int
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        IconButton(
            onClick = { onChangeMode() },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(50.dp)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(icChangeMode),
                contentDescription = "Switch display mode",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "MY INFORMATION",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 10.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        if (showEditButton) {
            IconButton(
                onClick = { onEdit() },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(50.dp)
                    .padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit Profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Image(
        painterResource(R.drawable.img_avatar),
        contentDescription = "Avatar",
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(150.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun Main(
    txtName: String,
    onNameChange: (String) -> Unit,
    txtPhoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    txtUniversityName: String,
    onUniversityNameChange: (String) -> Unit,
    txtDescribeYourSelf: String,
    onDescribeYourSelfChange: (String) -> Unit,
    nameWarning: String,
    phoneNumberWarning: String,
    universityNameWarning: String,
    enableEditing: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            InputField(
                name = "name",
                value = txtName,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                enable = enableEditing
            )
            Text(
                nameWarning,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)
        ) {
            InputField(
                name = "phone number",
                value = txtPhoneNumber,
                onValueChange = onPhoneNumberChange,
                modifier = Modifier.fillMaxWidth(),
                enable = enableEditing
            )
            Text(
                phoneNumberWarning,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    InputField(
        name = "university name",
        value = txtUniversityName,
        onValueChange = onUniversityNameChange,
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        enable = enableEditing
    )
    Text(
        universityNameWarning,
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
        modifier = Modifier.padding(start = 15.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))

    InputField(
        name = "describe yourself",
        value = txtDescribeYourSelf,
        onValueChange = onDescribeYourSelfChange,
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        enable = enableEditing,
        minLine = 8,
        maxLine = 8
    )
}

fun submit(
    txtName: String,
    txtPhoneNumber: String,
    txtUniversityName: String,
    onNameWarningChange: (String) -> Unit,
    onPhoneNumberWarningChange: (String) -> Unit,
    onUniversityNameWarningChange: (String) -> Unit,
    onDismissSuccessDialog: (Boolean) -> Unit
) {
    var hasError = false

    when {
        txtName.isBlank() -> {
            onNameWarningChange("Enter your name")
            hasError = true
        }
        !txtName.all { it.isLetter() || it.isWhitespace() } -> {
            onNameWarningChange("Invalid format")
            hasError = true
        }
        else -> onNameWarningChange("")
    }

    when {
        txtPhoneNumber.isBlank() -> {
            onPhoneNumberWarningChange("Enter phone number")
            hasError = true
        }
        !txtPhoneNumber.all { it.isDigit() } -> {
            onPhoneNumberWarningChange("Invalid format")
            hasError = true
        }
        else -> onPhoneNumberWarningChange("")
    }

    when {
        txtUniversityName.isBlank() -> {
            onUniversityNameWarningChange("Enter university name")
            hasError = true
        }
        !txtUniversityName.all { it.isLetter() || it.isWhitespace() } -> {
            onUniversityNameWarningChange("Invalid format")
            hasError = true
        }
        else -> onUniversityNameWarningChange("")
    }

    if (!hasError) {
        onDismissSuccessDialog(true)
    }
}

//@Preview
//@Composable
//private fun preview() {
//    MainScreen()
//}
