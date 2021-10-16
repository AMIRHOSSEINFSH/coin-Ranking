package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface BookmarkDao {

    @Insert(onConflict = REPLACE)
    fun insert(bookmark: Bookmark)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarks(bookmarkList: List<Bookmark>)

    @Delete
    fun remove(bookmark: Bookmark)

    @Query("SELECT * FROM Bookmark")
    fun getBookmarks(): List<Bookmark>


}