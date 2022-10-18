package com.example.yolo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [YoloDb::class], version = 10, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun yoloDao(): YoloDao

    companion object {
        private var instance: PhotoDatabase? = null

        fun getInstance(context: Context): PhotoDatabase {
            if (instance == null) {
                synchronized(PhotoDatabase::class) {
                    instance = buildRoomDB(context)
                }
            }
            return instance!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PhotoDatabase::class.java,
                "yolo_db"
            )
                .allowMainThreadQueries()
                .build()
    }
}