package com.example.movies.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.movies.core.Constant.databaseName
import com.example.movies.core.Constant.databaseVersion
import com.example.movies.core.Constant.moviesTableName
import com.example.movies.data.model.MovieModel

class DatabaseHelper(
    context: Context
): SQLiteOpenHelper(context, databaseName, null, databaseVersion) {
    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL("CREATE TABLE $moviesTableName(id integer PRIMARY KEY, title text, posterPath text, releaseDate text, voteAverage real, voteCount integer)")
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.execSQL("DROP TABLE if exists $moviesTableName")
        onCreate(database)
    }

    fun recover(): ArrayList<MovieModel> {
        val database = readableDatabase
        val cursor = database.rawQuery("SELECT title, posterPath, releaseDate, voteAverage, voteCount FROM $moviesTableName", null)
        val movieList = arrayListOf<MovieModel>()

        if (cursor.moveToFirst()) {
            do {
                movieList.add(
                    MovieModel(
                        cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("posterPath")),
                        cursor.getString(cursor.getColumnIndexOrThrow("releaseDate")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("voteAverage")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("voteCount"))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return movieList
    }

    fun insert(movieModel: MovieModel): Boolean {
        movieModel.run {
            val database = writableDatabase
            val contentValues = ContentValues()
            contentValues.put("title", title)
            contentValues.put("posterPath", posterPath)
            contentValues.put("releaseDate", releaseDate)
            contentValues.put("voteAverage", voteAverage)
            contentValues.put("voteCount", voteCount)
            val result = database.insert(moviesTableName, null, contentValues)
            if (result == -1L) return false
        }
        return true
    }

    fun clearTable() {
        val database = writableDatabase
        database.execSQL("DELETE FROM $moviesTableName")
    }
}