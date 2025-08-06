package com.example.dinhngocthe.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dinhngocthe.presentation.components.InputField
import com.example.dinhngocthe.presentation.components.SuccessDialog
import com.example.dinhngocthe.presentation.theme.AppFonts
import kotlinx.coroutines.delay

@Composable
fun ColumnScope.Main(
    fullName: String,
    phoneNumber: String,
    universityName: String,
    description: String,
    fullNameWarning: String,
    phoneNumberWarning: String,
    universityNameWarning: String,
    isEditingMode: Boolean,
    isSuccessDialogVisible: Boolean,
    modifier: Modifier = Modifier,
    onFullNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onUniversityNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onChangeSuccessDialogState: (Boolean) -> Unit,
    onSubmitChange: () -> Unit
) {
    Row(
        modifier = modifier
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
                value = fullName,
                onValueChange = onFullNameChange,
                modifier = Modifier.fillMaxWidth(),
                enable = isEditingMode
            )
            Text(
                fullNameWarning,
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
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                modifier = Modifier.fillMaxWidth(),
                enable = isEditingMode
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
        value = universityName,
        onValueChange = onUniversityNameChange,
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        enable = isEditingMode
    )
    Text(
        universityNameWarning,
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
        modifier = modifier.padding(start = 15.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))

    InputField(
        name = "describe yourself",
        value = description,
        onValueChange = onDescriptionChange,
        modifier = modifier.padding(start = 15.dp, end = 15.dp),
        enable = isEditingMode,
        minLine = 8,
        maxLine = 8
    )

    Spacer(modifier = Modifier.height(50.dp))

    if (isEditingMode) {
        Button(
            onClick = { onSubmitChange() },
            modifier = modifier
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

    if (isSuccessDialogVisible) {
        SuccessDialog(
            onDismiss = { onChangeSuccessDialogState(false) },
            message = "Your information has been updated"
        )
        LaunchedEffect(Unit) {
            delay(2000)
            onChangeSuccessDialogState(false)
        }
    }
}