package com.cookandroid.loarang.ui.character

import android.app.Application
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cookandroid.loarang.R
import com.cookandroid.loarang.room.CharacterEntity
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.component_green
import com.cookandroid.loarang.ui.theme.iconColor
import com.cookandroid.loarang.ui.theme.textColor

@Composable
fun CharacterScreen(name: String, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
    val characterList by viewModel.characterList.collectAsState()
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.backgroundGrey
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(
                    items = characterList,
                    key = { it.characterName }
                ) {
                    CharacterItem(
                        character = it,
                        viewModel = viewModel,
                        context = context
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = { openAlertDialog.value = true },
                containerColor = component_green
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = stringResource(id = R.string.add_character_title)
                )
            }
            
            when {
                openAlertDialog.value -> {
                    DialogScreen(
                        context,
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = { nickname ->
                            openAlertDialog.value = false
                            viewModel.addCharacter(nickname).observeForever { result ->
                                result.onSuccess {
                                    Toast.makeText(context, nickname + context.getString(R.string.add_character_success), Toast.LENGTH_SHORT).show()
                                }.onFailure {
                                    Toast.makeText(context, context.getString(R.string.search_error), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharacterItem(character: CharacterEntity, viewModel: MainViewModel, context: Context) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .shadow(12.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem),
        onClick = {
            val characterFragmentDetail = Intent(context, CharacterFragmentDetail::class.java)
            characterFragmentDetail.putExtra("nickname", character.characterName)
            context.startActivity(characterFragmentDetail)
        }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = character.characterImage,
                contentDescription = context.getString(R.string.character_class_image),
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.iconColor)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = character.characterName ?: "no nickname",
                style = AppTypography.titleLarge,
                color = MaterialTheme.colorScheme.textColor,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.icon_dots),
                    contentDescription = stringResource(id = R.string.character_more_menu_description),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { isDropDownMenuExpanded = true }
                )
                DropdownMenu(
                    modifier = Modifier.wrapContentSize(),
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.character_reload)) },
                        onClick = {
                            viewModel.updateCharacter(character.characterName)
                            isDropDownMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.character_delete)) },
                        onClick = {
                            viewModel.deleteCharacter(character.characterName)
                            isDropDownMenuExpanded = false
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = character.characterClassName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = character.characterLevel,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Text(
                    text = character.serverName,
                    fontSize = 11.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Text(
                text = character.itemLevel,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Normal
            )
        }
    }
}


@Composable
fun DialogScreen(
    context: Context,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.search_character),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = {
                            if (text.isEmpty()) {
                                Toast.makeText(context, context.getString(R.string.search_empty), Toast.LENGTH_SHORT).show()
                            } else {
                                onConfirmation(text)
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.search))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun CharacterScreenPreview() {
    AppTheme {
        CharacterScreen(
            name = "Character"
        )
    }
}

@Preview
@Composable
private fun CharacterItemPreview() {
    AppTheme {
        CharacterItem(
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
}