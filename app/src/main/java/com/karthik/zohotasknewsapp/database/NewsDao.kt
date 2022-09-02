package com.karthik.zohotasknewsapp.database

import androidx.room.*
import com.karthik.zohotasknewsapp.News
import com.karthik.zohotasknewsapp.utilities.Constants

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsTable")
    fun getAllNews(): List<News>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllNews(users: List<News>)

    @Query("DELETE FROM NewsTable")
    fun deleteAllNews()
}

