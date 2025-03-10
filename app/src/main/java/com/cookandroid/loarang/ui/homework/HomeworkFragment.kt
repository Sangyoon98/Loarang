package com.cookandroid.loarang.ui.homework

import android.app.Application
import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cookandroid.loarang.R
import com.cookandroid.loarang.application.BaseApplication.Companion.sharedPreferenceUtil
import com.cookandroid.loarang.room.CharacterEntity
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundLightGreen
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.black
import com.cookandroid.loarang.ui.theme.component_green
import com.cookandroid.loarang.ui.theme.iconColor
import com.cookandroid.loarang.ui.theme.textColor

@Composable
fun HomeworkScreen(name: String, modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val homeworkList by viewModel.characterList.collectAsState()
    val context = LocalContext.current
    
    var chaosGate by remember { mutableStateOf(sharedPreferenceUtil.getBooleanPreference("CHAOS_GATE")) }
    var fieldBoss by remember { mutableStateOf(sharedPreferenceUtil.getBooleanPreference("FIELD_BOSS")) }
    var adventureIsland by remember { mutableStateOf(sharedPreferenceUtil.getBooleanPreference("ADVENTURE_ISLAND")) }

    LaunchedEffect(homeworkList) {
        viewModel.getCharacterList()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.backgroundGrey
    ) {
        Column(Modifier.fillMaxWidth()) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .wrapContentHeight()
                            .shadow(12.dp, RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = context.getString(R.string.homework_event_title),
                                style = AppTypography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Card(
                                    onClick = {
                                        if (chaosGate) {
                                            sharedPreferenceUtil.setBooleanPreference("CHAOS_GATE", false)
                                            chaosGate = false
                                        } else {
                                            sharedPreferenceUtil.setBooleanPreference("CHAOS_GATE", true)
                                            chaosGate = true
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            text = context.getString(R.string.homework_gate),
                                            style = AppTypography.bodySmall,
                                            color = MaterialTheme.colorScheme.textColor
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Row {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(3.dp)
                                                    .background(color = if (chaosGate) component_green else MaterialTheme.colorScheme.backgroundGrey)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Card(
                                    onClick = {
                                        if (fieldBoss) {
                                            sharedPreferenceUtil.setBooleanPreference("FIELD_BOSS", false)
                                            fieldBoss = false
                                        } else {
                                            sharedPreferenceUtil.setBooleanPreference("FIELD_BOSS", true)
                                            fieldBoss = true
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            text = context.getString(R.string.homework_field),
                                            style = AppTypography.bodySmall,
                                            color = MaterialTheme.colorScheme.textColor
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Row {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(3.dp)
                                                    .background(color = if (fieldBoss) component_green else MaterialTheme.colorScheme.backgroundGrey)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Card(
                                    onClick = {
                                        if (adventureIsland) {
                                            sharedPreferenceUtil.setBooleanPreference("ADVENTURE_ISLAND", false)
                                            adventureIsland = false
                                        } else {
                                            sharedPreferenceUtil.setBooleanPreference("ADVENTURE_ISLAND", true)
                                            adventureIsland = true
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            text = context.getString(R.string.homework_adventure),
                                            style = AppTypography.bodySmall,
                                            color = MaterialTheme.colorScheme.textColor
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Row {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(3.dp)
                                                    .background(color = if (adventureIsland) component_green else MaterialTheme.colorScheme.backgroundGrey)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                items(
                    items = homeworkList,
                    key = { homework -> homework.characterName }
                ) { homework ->
                    HomeworkItem(homework, viewModel, context)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.backgroundListItem)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 2.dp),
                    colors = ButtonDefaults.buttonColors(component_green),
                    onClick = {
                        chaosGate = false
                        fieldBoss = false
                        adventureIsland = false
                        sharedPreferenceUtil.setBooleanPreference("CHAOS_GATE", false)
                        sharedPreferenceUtil.setBooleanPreference("FIELD_BOSS", false)
                        sharedPreferenceUtil.setBooleanPreference("ADVENTURE_ISLAND", false)
                        viewModel.resetDailyHomework()
                    }
                ) {
                    Text(
                        text = context.getString(R.string.homework_delete_today),
                        style = AppTypography.bodySmall,
                        color = MaterialTheme.colorScheme.black
                    )
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 2.dp, end = 16.dp),
                    colors = ButtonDefaults.buttonColors(component_green),
                    onClick = {
                        chaosGate = false
                        fieldBoss = false
                        adventureIsland = false
                        sharedPreferenceUtil.setBooleanPreference("CHAOS_GATE", false)
                        sharedPreferenceUtil.setBooleanPreference("FIELD_BOSS", false)
                        sharedPreferenceUtil.setBooleanPreference("ADVENTURE_ISLAND", false)
                        viewModel.resetAllHomework()
                    }
                ) {
                    Text(
                        text = context.getString(R.string.homework_delete_all),
                        style = AppTypography.bodySmall,
                        color = MaterialTheme.colorScheme.black
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HomeworkItem(homework: CharacterEntity, viewModel: MainViewModel, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .shadow(12.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            GlideImage(
                model = homework.characterImage,
                contentDescription = context.getString(R.string.character_class_image),
                modifier = Modifier.size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.iconColor)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = homework.characterName ?: "no nickname",
                    style = AppTypography.titleLarge,
                    color = MaterialTheme.colorScheme.textColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = homework.itemLevel ?: "no level",
                    style = AppTypography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    color = MaterialTheme.colorScheme.textColor
                )
            }
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = context.getString(R.string.homework_daily_title),
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    onClick = {
                        val newEpona = if (homework.epona == 3) 0 else homework.epona + 1
                        viewModel.updateEpona(homework.characterName, newEpona)
                    },
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = context.getString(R.string.homework_daily_epona),
                            style = AppTypography.bodySmall,
                            color = MaterialTheme.colorScheme.textColor
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(3.dp)
                                        .background(if (homework.epona > index) component_green else MaterialTheme.colorScheme.backgroundGrey)
                                )
                                if (index < 2) Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    onClick = {
                        val newChaos = if (homework.chaos == 1) 0 else homework.chaos + 1
                        viewModel.updateChaos(homework.characterName, newChaos)
                    },
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = context.getString(R.string.homework_daily_chaos),
                            style = AppTypography.bodySmall,
                            color = MaterialTheme.colorScheme.textColor
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (homework.chaos == 1) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    onClick = {
                        val newGadian = if (homework.gadian == 1) 0 else homework.gadian + 1
                        viewModel.updateGadian(homework.characterName, newGadian)
                    },
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = context.getString(R.string.homework_daily_gadian),
                            style = AppTypography.bodySmall,
                            color = MaterialTheme.colorScheme.textColor
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (homework.gadian == 1) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = context.getString(R.string.homework_weekly_title),
                style = AppTypography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    onClick = {
                        val newEndContent = if (homework.endContent == 3) 0 else homework.endContent + 1
                        viewModel.updateEnd(homework.characterName, newEndContent)
                    },
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundLightGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = context.getString(R.string.homework_weekly_end),
                            style = AppTypography.bodySmall,
                            color = MaterialTheme.colorScheme.textColor
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(3.dp)
                                        .background(if (homework.endContent > index) component_green else MaterialTheme.colorScheme.backgroundGrey)
                                )
                                if (index < 2) Spacer(modifier = Modifier.width(5.dp))
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun HomeworkScreenPreview() {
    AppTheme {
        HomeworkScreen(name = "Homework")
    }
}

/*
@Preview
@Composable
private fun HomeworkItemPreview() {
    AppTheme {
        HomeworkItem(
            CharacterEntity(
                characterName = "0iloll0",
                characterLevel = "1643.40",
                itemLevel = "13",
                characterClassName = "dd",
                characterImage = "dd",
                serverName = "ss",
                epona = 0,
                chaos = 0,
                gadian = 0,
                endContent = 0
            ),
            viewModel = MainViewModel(application = Application()),
            context = LocalContext.current
        )
    }
}*/
