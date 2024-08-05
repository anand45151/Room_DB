// Student.kt
package com.example.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey val courseID: Int,
    @ColumnInfo(name = "courseName") val name: String?,
    @ColumnInfo(name = "courseEmail") val email: String?,
    @ColumnInfo(name = "coursePrice") val avatar: String?
)
