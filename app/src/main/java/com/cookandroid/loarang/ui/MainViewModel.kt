package com.cookandroid.loarang.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.cookandroid.loarang.base.BaseViewModel
import com.cookandroid.loarang.room.CharacterDatabase
import com.cookandroid.loarang.room.CharacterEntity
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val characterDao = CharacterDatabase.getInstance(application.applicationContext)!!.characterDao()

    private val _characterList = MutableLiveData<List<CharacterEntity>>()
    val characterList get() = _characterList

    fun getCharacterList() {
        viewModelScope.launch {
            _characterList.postValue(characterDao.getAll())
        }
    }

    fun addCharacter(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
                characterImage = image
            ))

            _characterList.postValue(characterDao.getAll())
        }
    }
}