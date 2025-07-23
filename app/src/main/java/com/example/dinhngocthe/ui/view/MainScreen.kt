package com.example.dinhngocthe.ui.view


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.dinhngocthe.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.example.dinhngocthe.ui.theme.AppFonts
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {
    val context = LocalContext.current
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5FAFF))
            .padding(top = 30.dp)
    ) {
        Header(
            onClick = {
                enableEditing = !enableEditing
                showEditButton = !showEditButton
            },
            showEditButton = showEditButton
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
                    containerColor = Color.Black,
                    contentColor = Color.White
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
    onClick: () -> Unit,
    showEditButton: Boolean
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "MY INFORMATION",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 50.dp),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = AppFonts.mainFont
            )
        )

        if (showEditButton) {
            IconButton(
                onClick = { onClick() },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(50.dp)
                    .padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Settings"
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
            .border(2.dp, Color.Black, shape = CircleShape)
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
                fontFamily = AppFonts.mainFont,
                fontSize = 15.sp,
                color = Color.Red
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
                fontFamily = AppFonts.mainFont,
                fontSize = 15.sp,
                color = Color.Red
            )
        }
    }

    Spacer(modifier = Modifier.height(30.dp))

    InputField(
        name = "university name",
        value = txtUniversityName,
        onValueChange = onUniversityNameChange,
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        enable = enableEditing
    )
    Text(
        universityNameWarning,
        fontFamily = AppFonts.mainFont,
        fontSize = 15.sp,
        color = Color.Red,
        modifier = Modifier.padding(start = 15.dp)
    )

    Spacer(modifier = Modifier.height(30.dp))

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


