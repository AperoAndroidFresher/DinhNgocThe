package com.example.dinhngocthe.presentation.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import com.example.dinhngocthe.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinhngocthe.presentation.theme.AppFonts
import com.example.dinhngocthe.presentation.view.InputField
import com.example.dinhngocthe.presentation.view.SuccessDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onChangeMode: () -> Unit,
    isDarkTheme: Boolean,
    innerPadding: PaddingValues,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showSuccessDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ProfileEvent.ChangeTheme -> onChangeMode()
                ProfileEvent.ShowSuccessDialog -> showSuccessDialog = true
            }
        }
    }

    var icChangeMode = if (isDarkTheme) R.drawable.ic_dark_mode else R.drawable.ic_light_mode

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.processIntent(ProfileIntent.AvatarChanged(uri))
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
            onEdit = { viewModel.processIntent(ProfileIntent.ToggleEditMode) },
            showEditButton = !state.isEditing,
            onChangeMode = {
                viewModel.processIntent(ProfileIntent.ChangeTheme)
                icChangeMode = if (isDarkTheme) R.drawable.ic_dark_mode else R.drawable.ic_light_mode
            },
            icChangeMode = icChangeMode,
            enableEditing = state.isEditing,
            onChangePicture = { imagePickerLauncher.launch("image/*") },
            avatarUri = state.avatarUri
        )

        Spacer(modifier = Modifier.height(30.dp))

        Main(
            txtName = state.name,
            onNameChange = { viewModel.processIntent(ProfileIntent.NameChanged(it)) },
            txtPhoneNumber = state.phoneNumber,
            onPhoneNumberChange = { viewModel.processIntent(ProfileIntent.PhoneNumberChanged(it)) },
            txtUniversityName = state.university,
            onUniversityNameChange = { viewModel.processIntent(ProfileIntent.UniversityChanged(it)) },
            txtDescribeYourSelf = state.description,
            onDescribeYourSelfChange = { viewModel.processIntent(ProfileIntent.DescriptionChanged(it)) },
            nameWarning = state.nameWarning,
            phoneNumberWarning = state.phoneWarning,
            universityNameWarning = state.universityWarning,
            enableEditing = state.isEditing
        )

        Spacer(modifier = Modifier.height(50.dp))

        if (state.isEditing) {
            Button(
                onClick = { viewModel.processIntent(ProfileIntent.Submit) },
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

        if (showSuccessDialog) {
            SuccessDialog(
                onDismiss = { showSuccessDialog = false },
                message = "Your information has been updated"
            )
            LaunchedEffect(Unit) {
                delay(2000)
                showSuccessDialog = false
            }
        }
    }
}

@Composable
fun ColumnScope.Header(
    onEdit: () -> Unit,
    showEditButton: Boolean,
    onChangeMode: () -> Unit,
    icChangeMode: Int,
    enableEditing: Boolean,
    onChangePicture: () -> Unit,
    avatarUri: Uri?
) {
    val density = LocalDensity.current
    val imageSizePx = with(density) { 150.dp.roundToPx() }

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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        if (avatarUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(avatarUri)
                    .size(imageSizePx)
                    .crossfade(true)
                    .build(),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(150.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painterResource(R.drawable.img_avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(150.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }

        if (enableEditing) {
            Button(
                onClick = { onChangePicture() },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_choose_picture),
                    contentDescription = "Press to change avatar",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
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


