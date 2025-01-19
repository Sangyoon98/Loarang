package com.cookandroid.loarang.ui.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cookandroid.loarang.R
import com.cookandroid.loarang.application.BaseApplication.Companion.sharedPreferenceUtil
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentHomeworkBinding
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.backgroundListItem

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
        viewModel.characterList.observe(viewLifecycleOwner) {
            homeworkAdapter.submitList(it)
        }
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
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HomeworkItem(homework: HomeworkModel) {
    val context = LocalContext.current

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
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            GlideImage(
                model = homework.characterImage,
                contentDescription = context.getString(R.string.character_class_image),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {

            }
        }
    }
}

//@Preview
@Composable
private fun HomeworkItemPreview() {
    AppTheme {
        HomeworkItem(
            HomeworkModel(
                characterNickname = "ddd",
                characterLevel = "1111",
                characterItemLevel = "13",
                characterClass = "dd",
                characterImage = "dd",
                characterServer = "ss"
            )
        )
    }
}