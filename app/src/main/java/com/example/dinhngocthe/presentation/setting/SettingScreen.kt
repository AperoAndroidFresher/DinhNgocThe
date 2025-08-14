package com.example.dinhngocthe.presentation.setting

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dinhngocthe.R
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.dinhngocthe.presentation.components.LanguageDropDownMenu
import com.example.dinhngocthe.utils.dynamicString

@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    onBack: () -> Unit,
    viewModel: SettingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isLanguageDropDownMenuVisible by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf(state.languageCode) }
    var isButtonAgreeVisible = language != state.languageCode

    LaunchedEffect(Unit) {
        viewModel.processIntent(SettingIntent.LoadData)
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
        HeaderSetting(
            onBack = { onBack() },
            isButtonAgreeVisible = isButtonAgreeVisible,
            onChangeLanguage = { viewModel.processIntent(SettingIntent.ChangeLanguage(language)) }
        )

        Spacer(Modifier.size(20.dp))

        MainSetting(
            languageCode = language,
            onLanguageClick = { isLanguageDropDownMenuVisible = !isLanguageDropDownMenuVisible }
        )

        Row(
            modifier = Modifier.align(Alignment.End)
        ) {
            LanguageDropDownMenu(
                expanded = isLanguageDropDownMenuVisible,
                onDismissRequest = { isLanguageDropDownMenuVisible = false },
                onChangToEnglish = { language = it }
            )
        }
    }
}

@Composable
fun MainSetting(
    modifier: Modifier = Modifier,
    languageCode: String,
    onLanguageClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onLanguageClick() }
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_language),
                contentDescription = "Icon language",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(26.dp)
            )

            Spacer(Modifier.size(15.dp))

            Text(
                text = stringResource(R.string.language),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
            )
        }
        Text(
            text = dynamicString(languageCode),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun HeaderSetting(
    modifier: Modifier = Modifier,
    isButtonAgreeVisible: Boolean,
    onBack: () -> Unit,
    onChangeLanguage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.align(Alignment.Center)
        )
        if (isButtonAgreeVisible) {
            IconButton(
                onClick = { onChangeLanguage() },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_agree),
                    contentDescription = "Icon agree",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

//@Preview
//@Composable
//private fun HeaderSettingPrev() {
//    HeaderSetting(
//        onBack = {}
//    )
//}

//@Preview
//@Composable
//private fun MainSettingPrev() {
//    MainSetting(
//
//    )
//}