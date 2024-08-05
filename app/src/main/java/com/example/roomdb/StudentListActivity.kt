package com.example.roomdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class StudentListActivity : AppCompatActivity() {
    private lateinit var studentDao: StudentDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val db = AppDatabase.getDatabase(this)
        studentDao = db.studentDao()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val students = studentDao.getAllStudents()
            adapter = StudentAdapter(
                studentList = students,
                onEditClick = { student ->
                    // Handle edit action here
                },
                onDeleteClick = { student ->
                    lifecycleScope.launch {
                        studentDao.deleteStudent(student)  // Ensure deleteStudent method is added in your DAO
                        val updatedStudents = studentDao.getAllStudents()
                        adapter = StudentAdapter(updatedStudents, ::onEditClick, ::onDeleteClick)
                        recyclerView.adapter = adapter
                    }
                }
            )
            recyclerView.adapter = adapter
        }
    }

    private fun onEditClick(student: Student) {
        // Handle edit action here
    }

    private fun onDeleteClick(student: Student) {
        lifecycleScope.launch {
            studentDao.deleteStudent(student)
            val updatedStudents = studentDao.getAllStudents()
            adapter = StudentAdapter(updatedStudents, ::onEditClick, ::onDeleteClick)
            recyclerView.adapter = adapter
        }
    }
}
