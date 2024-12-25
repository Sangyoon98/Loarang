package com.cookandroid.loarang.ui.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.cookandroid.loarang.R
import com.cookandroid.loarang.application.BaseApplication.Companion.sharedPreferenceUtil
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentHomeworkBinding
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.main.MainViewModel

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
        if (animator is SimpleItemAnimator){          //아이템 애니메이커 기본 하위클래스
            animator.supportsChangeAnimations = false  //애니메이션 값 false (리사이클러뷰가 화면을 다시 갱신 했을때 뷰들의 깜빡임 방지)
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