package com.cookandroid.loarang.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.cookandroid.loarang.R
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentCharacterBinding
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.main.MainViewModel

class CharacterFragment : BaseFragment() {
    companion object {
        fun newInstance() = CharacterFragment()
        const val TAG = "CharacterFragment"
    }

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!
    lateinit var context: MainActivity
    private val viewModel: MainViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.characterList.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
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
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)

        characterAdapter = CharacterAdapter(context, { deleteNickname ->
            viewModel.deleteCharacter(deleteNickname)
        }, { updateNickname ->
            viewModel.updateCharacter(updateNickname)
        })
        binding.characterList.adapter = characterAdapter

        binding.addCharBtn.setOnClickListener {
            AddCharacterDialog(context) { dlgEdt ->
                if (dlgEdt.isEmpty()) {
                    Toast.makeText(context, getString(R.string.search_empty), Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addCharacter(dlgEdt).observe(viewLifecycleOwner) { result ->
                        result.onSuccess {
                            viewModel.characterList.observe(viewLifecycleOwner) {
                                characterAdapter.submitList(it)
                            }
                        }.onFailure {
                            Toast.makeText(context, getString(R.string.search_error), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.showDialog()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCharacterList()
    }
}