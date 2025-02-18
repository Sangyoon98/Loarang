package com.cookandroid.loarang.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cookandroid.loarang.base.BaseViewModel
import com.cookandroid.loarang.room.CharacterDatabase
import com.cookandroid.loarang.room.CharacterEntity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val characterDao = CharacterDatabase.getInstance(application.applicationContext)!!.characterDao()

    private val _characterList = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val characterList: StateFlow<List<CharacterEntity>> get() = _characterList

    init {
        getCharacterList()
    }

    fun getCharacterList() {
        viewModelScope.launch {
            _characterList.value = characterDao.getAll()
        }
    }

    fun addCharacter(nickname: String): LiveData<Result<Unit>> {
        val resultLiveData = MutableLiveData<Result<Unit>>()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = "https://lostark.game.onstove.com/Profile/Character/$nickname"
                val document = Jsoup.connect(url).get()

                var name = ""
                var server = ""
                var charLevel = ""
                var itemLevel = ""
                var classInfo = ""
                var image = ""

                val nameElements = document.select("span[class=profile-character-info__name]")
                for (element in nameElements) name += element.text()

                val serverElements = document.select("span[class=profile-character-info__server]")
                for (element in serverElements) server += element.text()

                val charLevelElements = document.select("span[class=profile-character-info__lv]")
                for (element in charLevelElements) charLevel += element.text()

                var temp = ""
                val itemLevelElements = document.select("div[class=level-info2__expedition]")
                for (element in itemLevelElements) temp += element.text()
                val arr = temp.split("벨".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                itemLevel = arr[1]

                val classElements = document.select("img[class=profile-character-info__img]")
                classInfo = classElements.attr("alt")

                val imgElements = document.select("img[class=profile-character-info__img]")
                image = imgElements.attr("src")

                characterDao.addCharacter(CharacterEntity(
                    serverName = server,
                    characterName = name,
                    characterLevel = charLevel,
                    characterClassName = classInfo,
                    itemLevel = itemLevel,
                    characterImage = image,
                    epona = 0,
                    chaos = 0,
                    gadian = 0,
                    endContent = 0
                ))

                _characterList.value = characterDao.getAll()

                resultLiveData.postValue(Result.success(Unit))
            } catch (e: Exception) {
                resultLiveData.postValue(Result.failure(e))
            }
        }
        return resultLiveData
    }

    fun updateCharacter(nickname: String) {
        viewModelScope.launch {
            characterDao.delete(nickname)
            addCharacter(nickname)
        }
    }

    fun deleteCharacter(nickname: String) {
        viewModelScope.launch {
            characterDao.delete(nickname)
            _characterList.value = characterDao.getAll()
        }
    }

    fun updateEpona(nickname: String, count: Int) {
        viewModelScope.launch {
            characterDao.updateEpona(nickname, count)
            _characterList.value = characterDao.getAll()
        }
    }

    fun updateChaos(nickname: String, count: Int) {
        viewModelScope.launch {
            characterDao.updateChaos(nickname, count)
            _characterList.value = characterDao.getAll()
        }
    }

    fun updateGadian(nickname: String, count: Int) {
        viewModelScope.launch {
            characterDao.updateGadian(nickname, count)
            _characterList.value = characterDao.getAll()
        }
    }

    fun updateEnd(nickname: String, count: Int) {
        viewModelScope.launch {
            characterDao.updateEnd(nickname, count)
            _characterList.value = characterDao.getAll()
        }
    }

    fun resetAllHomework() {
        viewModelScope.launch {
            characterDao.resetAllHomework()
            _characterList.value = characterDao.getAll()
        }
    }

    fun resetDailyHomework() {
        viewModelScope.launch {
            characterDao.resetDailyHomework()
            _characterList.value = characterDao.getAll()
        }
    }
}