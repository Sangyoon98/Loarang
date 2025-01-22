package com.cookandroid.loarang.ui.homework

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cookandroid.loarang.R
import com.cookandroid.loarang.application.BaseApplication.Companion.sharedPreferenceUtil
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentHomeworkBinding
import com.cookandroid.loarang.room.CharacterEntity
import com.cookandroid.loarang.ui.character.CharacterModel
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.schedule.ScheduleModel
import com.cookandroid.loarang.ui.schedule.ScheduleViewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundLightGreen
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.black
import com.cookandroid.loarang.ui.theme.component_green
import com.cookandroid.loarang.ui.theme.iconColor
import com.cookandroid.loarang.ui.theme.mainGreen
import com.cookandroid.loarang.ui.theme.textColor

/*private var homeworkList = mutableStateListOf<HomeworkModel>()

class HomeworkFragment : BaseFragment() {
    companion object {
        fun newInstance() = HomeworkFragment()
        const val TAG = "HomeworkFragment"
    }

    private var _binding: FragmentHomeworkBinding? = null
    private val binding get() = _binding!!
    lateinit var context: MainActivity
    private val viewModel: MainViewModel by viewModels()
    private lateinit var homeworkAdapter: HomeworkAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        *//*viewModel.characterList.observe(viewLifecycleOwner) {
            homeworkAdapter.submitList(it)
        }*//*
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeworkBinding.inflate(inflater, container, false)

        binding.homeworkList.itemAnimator = null
        val animator = binding.homeworkList.itemAnimator     //리사이클러뷰 애니메이터 get
        if (animator is SimpleItemAnimator) {          //아이템 애니메이커 기본 하위클래스
            animator.supportsChangeAnimations =
                false  //애니메이션 값 false (리사이클러뷰가 화면을 다시 갱신 했을때 뷰들의 깜빡임 방지)
        }

        homeworkAdapter = HomeworkAdapter(context, { nickname, count ->
            viewModel.updateEpona(nickname, count)
        }, { nickname, count ->
            viewModel.updateChaos(nickname, count)
        }, { nickname, count ->
            viewModel.updateGadian(nickname, count)
        }, { nickname, count ->
            viewModel.updateEnd(nickname, count)
        })
        binding.homeworkList.adapter = homeworkAdapter

        getEvent()

        binding.containerGate.setOnClickListener {
            setEvent("CHAOS_GATE", binding.gate)
        }

        binding.containerField.setOnClickListener {
            setEvent("FIELD_BOSS", binding.field)
        }

        binding.containerAdventure.setOnClickListener {
            setEvent("ADVENTURE_ISLAND", binding.adventure)
        }

        binding.allDelete.setOnClickListener {
            resetEvent()
            viewModel.resetAllHomework()
        }

        binding.todayDelete.setOnClickListener {
            resetEvent()
            viewModel.resetDailyHomework()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCharacterList()
    }

    private fun getEvent() {
        when (sharedPreferenceUtil.getBooleanPreference("CHAOS_GATE")) {
            true -> binding.gate.setBackgroundColor(context.getColor(R.color.component_green))
            else -> binding.gate.setBackgroundColor(context.getColor(R.color.white))
        }

        when (sharedPreferenceUtil.getBooleanPreference("FIELD_BOSS")) {
            true -> binding.field.setBackgroundColor(context.getColor(R.color.component_green))
            else -> binding.field.setBackgroundColor(context.getColor(R.color.white))
        }

        when (sharedPreferenceUtil.getBooleanPreference("ADVENTURE_ISLAND")) {
            true -> binding.adventure.setBackgroundColor(context.getColor(R.color.component_green))
            else -> binding.adventure.setBackgroundColor(context.getColor(R.color.white))
        }
    }

    private fun setEvent(sharedName: String, view: View) {
        when (sharedPreferenceUtil.getBooleanPreference(sharedName)) {
            true -> {
                sharedPreferenceUtil.setBooleanPreference(sharedName, false)
                view.setBackgroundColor(context.getColor(R.color.white))
            }

            else -> {
                sharedPreferenceUtil.setBooleanPreference(sharedName, true)
                view.setBackgroundColor(context.getColor(R.color.component_green))
            }
        }
    }

    private fun resetEvent() {
        sharedPreferenceUtil.setBooleanPreference("CHAOS_GATE", false)
        sharedPreferenceUtil.setBooleanPreference("FIELD_BOSS", false)
        sharedPreferenceUtil.setBooleanPreference("ADVENTURE_ISLAND", false)
        binding.gate.setBackgroundColor(context.getColor(R.color.white))
        binding.field.setBackgroundColor(context.getColor(R.color.white))
        binding.adventure.setBackgroundColor(context.getColor(R.color.white))
    }
}*/

@Composable
fun HomeworkScreen(name: String, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel() // ViewModel 생성
    val homeworkList by viewModel.characterList.collectAsState()
    val context = LocalContext.current
    
    var chaosGate by remember { mutableStateOf(sharedPreferenceUtil.getBooleanPreference("CHAOS_GATE")) }
    var fieldBoss by remember { mutableStateOf(sharedPreferenceUtil.getBooleanPreference("FIELD_BOSS")) }
    var adventureIsland by remember { mutableStateOf(sharedPreferenceUtil.getBooleanPreference("ADVENTURE_ISLAND")) }


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
                    HomeworkItem(homework, viewModel)
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
private fun HomeworkItem(homework: CharacterEntity, viewModel: MainViewModel) {
    val context = LocalContext.current
    var boxEpona by remember { mutableIntStateOf(homework.epona) }
    var boxChaos by remember { mutableIntStateOf(homework.chaos) }
    var boxGadian by remember { mutableIntStateOf(homework.gadian) }
    var boxEndContent by remember { mutableIntStateOf(homework.endContent) }

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
                        if (boxEpona == 3) {
                            viewModel.updateEpona(homework.characterName, 0)
                            boxEpona = 0
                        } else {
                            viewModel.updateEpona(homework.characterName, homework.epona + 1)
                            boxEpona += 1
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
                            text = context.getString(R.string.homework_daily_epona),
                            style = AppTypography.bodySmall,
                            color = MaterialTheme.colorScheme.textColor
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (boxEpona >= 1) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (boxEpona >= 2) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (boxEpona >= 3) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    onClick = {
                        if (boxChaos == 0) {
                            viewModel.updateChaos(homework.characterName, 1)
                            boxChaos = 1
                        } else {
                            viewModel.updateChaos(homework.characterName, 0)
                            boxChaos = 0
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
                                    .background(color = if (boxChaos == 1) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    onClick = {
                        if (boxGadian == 0) {
                            viewModel.updateGadian(homework.characterName, 1)
                            boxGadian = 1
                        } else {
                            viewModel.updateGadian(homework.characterName, 0)
                            boxGadian = 0
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
                                    .background(color = if (boxGadian == 1) component_green else MaterialTheme.colorScheme.backgroundGrey)
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
                        if (boxEndContent == 3) {
                            viewModel.updateEnd(homework.characterName, 0)
                            boxEndContent = 0
                        } else {
                            viewModel.updateEnd(homework.characterName, homework.endContent + 1)
                            boxEndContent += 1
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
                            text = context.getString(R.string.homework_weekly_end),
                            style = AppTypography.bodySmall,
                            color = MaterialTheme.colorScheme.textColor
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (boxEndContent >= 1) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (boxEndContent >= 2) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(3.dp)
                                    .background(color = if (boxEndContent >= 3) component_green else MaterialTheme.colorScheme.backgroundGrey)
                            )
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
        HomeworkScreen(
            /*listOf(
                HomeworkModel("", "Nickname", "1640.00", "13", "dd", "dd"),
                HomeworkModel("", "Nickname", "1640.00", "13", "dd", "dd"),
            )*/
            name = "Homework"
        )
    }
}

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
            viewModel = MainViewModel(application = Application())
        )
    }
}