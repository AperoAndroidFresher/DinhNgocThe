package com.example.dinhngocthe.ui.lesson_06_jetpack_compose_ui


import androidx.compose.foundation.Image
import com.example.dinhngocthe.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    var txtName = remember { mutableStateOf("") }
    var txtPhoneNumber = remember { mutableStateOf("") }
    var txtUniversityName = remember { mutableStateOf("") }
    var txtDescribeYourSelf = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5FAFF))
            .padding(top = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = "MY INFORMATION",
                modifier = Modifier.align(Alignment.Center).padding(top = 50.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = AppFonts.mainFont
                )
            )

            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterEnd).size(50.dp).padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Settings"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painterResource(R.drawable.img_avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(150.dp)
                .border(2.dp, Color.Black, shape =  CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
        ) {
            InputField(
                name = "name",
                value = txtName.value,
                onValueChange = { txtName.value = it },
                modifier = Modifier.weight(1f).padding(end = 10.dp)
            )
            InputField(
                name = "phone number",
                value = txtPhoneNumber.value,
                onValueChange = { txtPhoneNumber.value = it },
                modifier = Modifier.weight(1f).padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        InputField(
            name = "university name",
            value = txtUniversityName.value,
            onValueChange = { txtUniversityName.value = it },
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        InputField(
            name = "describe yourself",
            value = txtDescribeYourSelf.value,
            onValueChange = { txtDescribeYourSelf.value = it },
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            8,
            8
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {},
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