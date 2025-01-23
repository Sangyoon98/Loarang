package com.cookandroid.loarang.ui.character

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.cookandroid.loarang.R
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentCharacterBinding
import com.cookandroid.loarang.room.CharacterEntity
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.backgroundGrey

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
        /*viewModel.characterList.observe(viewLifecycleOwner) {
            characterAdapter.submitList(it)
        }*/
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

        /*binding.addCharBtn.setOnClickListener {
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
        }*/

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCharacterList()
    }
}

@Composable
fun CharacterScreen(name: String, modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel() // ViewModel 생성
    val characterList by viewModel.characterList.collectAsState()
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.backgroundGrey
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            items(
                items = characterList,
                key = { it.characterName }
            ) {

            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CharacterItem(character: CharacterEntity, viewModel: MainViewModel, context: Context) {

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