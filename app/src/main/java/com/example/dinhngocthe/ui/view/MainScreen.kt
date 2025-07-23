package com.example.dinhngocthe.ui.view


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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.ui.theme.AppFonts

@Composable
fun MainScreen() {
    var txtName by remember { mutableStateOf("") }
    var txtPhoneNumber by remember { mutableStateOf("") }
    var txtUniversityName by remember { mutableStateOf("") }
    var txtDescribeYourSelf by remember { mutableStateOf("") }
    var enableEditing by remember { mutableStateOf(false) }
    var showEditButton by remember { mutableStateOf(true) }
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
            enableEditing
        )

        Spacer(modifier = Modifier.height(50.dp))

        if (enableEditing) {
            Button(
                onClick = {

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
    enableEditing: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        InputField(
            name = "name",
            value = txtName,
            onValueChange = onNameChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            enable = enableEditing
        )
        InputField(
            name = "phone number",
            value = txtPhoneNumber,
            onValueChange = onPhoneNumberChange,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            enable = enableEditing
        )
    }

    Spacer(modifier = Modifier.height(30.dp))

    InputField(
        name = "university name",
        value = txtUniversityName,
        onValueChange = onUniversityNameChange,
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        enable = enableEditing
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
