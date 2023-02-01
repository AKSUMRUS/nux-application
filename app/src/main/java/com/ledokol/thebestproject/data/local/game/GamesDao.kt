package com.ledokol.thebestproject.data.local.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<Game>)

    @Query("DELETE FROM games")
    fun clearGames()

    @Query(
        """
            SELECT * 
            FROM games
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
        """
    )
    fun getGames(query: String): List<Game>

    @Query("SELECT * FROM games WHERE package = :packageName LIMIT 1")
    fun getGame(packageName: String): Game

}