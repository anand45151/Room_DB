package com.example.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Delete
    suspend fun deleteStudent(student: Student)
}