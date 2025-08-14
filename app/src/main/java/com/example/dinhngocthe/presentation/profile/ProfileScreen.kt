package com.example.dinhngocthe.presentation.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinhngocthe.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    onChangeMode: () -> Unit,
    isDarkTheme: Boolean,
    innerPadding: PaddingValues,
    onLogOut: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val tag = "ProfileScreen"
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    var isSuccessDialogVisible by remember { mutableStateOf(false) }
    var isEditingMode by remember { mutableStateOf(false) }
    var icChangeTheme = if (isDarkTheme) R.drawable.ic_dark_mode else R.drawable.ic_light_mode

    LaunchedEffect(Unit) {
        viewModel.processIntent(ProfileIntent.LoadData)
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ProfileEvent.ShowSuccessDialog ->  {
                    isSuccessDialogVisible = true
                    isEditingMode = false
                }

                ProfileEvent.LogOut -> {
                    onLogOut()
                }
            }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.processIntent(ProfileIntent.AvatarUriChanged(it))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        Header(
            isEditingMode = isEditingMode,
            icChangeMode = icChangeTheme,
            avatarUri = state.avatarUri,
            onEdit = { isEditingMode = true },
            modifier = Modifier,
            onChangeMode = {
                onChangeMode()
                icChangeTheme = if (isDarkTheme) R.drawable.ic_dark_mode else R.drawable.ic_light_mode
            },
            onChangePicture = { imagePickerLauncher.launch("image/*") },
        )

        Main(
            fullName = state.fullName,
            phoneNumber = state.phoneNumber,
            universityName = state.university,
            description = state.description,
            fullNameWarning = state.nameWarning,
            phoneNumberWarning = state.phoneWarning,
            universityNameWarning = state.universityWarning,
            isEditingMode = isEditingMode,
            isSuccessDialogVisible = isSuccessDialogVisible,
            modifier = Modifier,
            onFullNameChange = { viewModel.processIntent(ProfileIntent.FullNameChanged(it)) },
            onPhoneNumberChange = { viewModel.processIntent(ProfileIntent.PhoneNumberChanged(it)) },
            onUniversityNameChange = { viewModel.processIntent(ProfileIntent.UniversityNameChanged(it)) },
            onDescriptionChange = { viewModel.processIntent(ProfileIntent.DescriptionChanged(it)) },
            onChangeSuccessDialogState = { isSuccessDialogVisible = it },
            onSubmitChange = { viewModel.processIntent(ProfileIntent.SubmitChange) },
            onLogOut = { viewModel.processIntent(ProfileIntent.LogOut) }
        )
    }
}




