package com.cookandroid.loarang.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var serverName: String,
    var characterName: String,
    var characterLevel: String,
    var characterClassName: String,
    var itemLevel: String,
    var characterImage: String
)