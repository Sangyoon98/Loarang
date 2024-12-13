package com.cookandroid.loarang.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {
    @Insert
    suspend fun addCharacter(character: CharacterEntity) {
        val existData = get(character.characterName)
        existData?.let { delete(character.characterName) }
        insert(character)
    }

    // 모든 데이터 가져오기
    @Query("SELECT * FROM CharacterEntity")
    suspend fun getAll(): List<CharacterEntity>

    @Insert
    suspend fun insert(character: CharacterEntity)

    // 검색어로 기존에 저장된 데이터 로드
    @Query("SELECT * FROM CharacterEntity WHERE characterName=:search")
    suspend fun get(search: String): CharacterEntity?

    // 검색어로 기존에 저장된 데이터 삭제
    @Query("DELETE FROM CharacterEntity WHERE characterName=:search")
    suspend fun delete(search: String)
}