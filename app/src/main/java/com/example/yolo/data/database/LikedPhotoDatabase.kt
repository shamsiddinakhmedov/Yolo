package com.example.yolo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LikedPhotoDB::class], version = 6, exportSchema = false)
@TypeConverters(PhotoTypeConverter::class)
abstract class LikedPhotoDatabase : RoomDatabase() {

    abstract fun likePhotoDao(): LikedPhotoDao

    companion object {
        private var instance: LikedPhotoDatabase? = null

        fun getLikePhotoDb(context: Context): LikedPhotoDatabase {
            if (instance == null) {
                synchronized(LikedPhotoDatabase::class) {
                    instance = buildRoomDB(context)
                }
            }
            return instance!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LikedPhotoDatabase::class.java,
                "liked_photo_db"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}