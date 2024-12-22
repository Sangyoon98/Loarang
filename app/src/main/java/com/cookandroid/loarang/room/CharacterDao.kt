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

    // epona 값 수정
    @Query("UPDATE CharacterEntity SET epona = :epona WHERE characterName = :characterName")
    suspend fun updateEpona(characterName: String, epona: Int)

    // chaos 값 수정
    @Query("UPDATE CharacterEntity SET chaos = :chaos WHERE characterName = :characterName")
    suspend fun updateChaos(characterName: String, chaos: Int)

    // gadian 값 수정
    @Query("UPDATE CharacterEntity SET gadian = :gadian WHERE characterName = :characterName")
    suspend fun updateGadian(characterName: String, gadian: Int)

    // end 값 수정
    @Query("UPDATE CharacterEntity SET endContent = :end WHERE characterName = :characterName")
    suspend fun updateEnd(characterName: String, end: Int)

    // 모든 데이터의 epona, chaos, gadian, end 값을 0으로 설정
    @Query("UPDATE CharacterEntity SET epona = 0, chaos = 0, gadian = 0, endContent = 0")
    suspend fun resetAllHomework()

    // 모든 데이터의 epona, chaos, gadian 값을 0으로 설정
    @Query("UPDATE CharacterEntity SET epona = 0, chaos = 0, gadian = 0")
    suspend fun resetDailyHomework()
}