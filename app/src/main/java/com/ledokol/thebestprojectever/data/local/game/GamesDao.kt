package com.ledokol.thebestprojectever.data.local.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.domain.GameJSON

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
    fun getGames(query: String) : List<Game>

    @Query("SELECT * FROM games WHERE package = :packageName")
    fun getGame(packageName: String) : Game

}