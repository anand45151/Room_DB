package com.example.roomdb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getDatabase(this)
        studentDao = db.studentDao()

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextAvatar = findViewById<EditText>(R.id.editTextAvatar)
        val buttonInsert = findViewById<Button>(R.id.buttonInsert)

        buttonInsert.setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val avatar = editTextAvatar.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && avatar.isNotEmpty()) {
                val student = Student(courseID = generateUniqueID(), name = name, email = email, avatar = avatar)
                lifecycleScope.launch {
                    studentDao.insertStudent(student)
                    clearInputs()
                    val intent = Intent(this@MainActivity, StudentListActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun generateUniqueID(): Int {
        // Generate a unique ID for the course. In a real app, this could come from a sequence or similar.
        return (1..1000).random()
    }

    private fun clearInputs() {
        findViewById<EditText>(R.id.editTextName).text.clear()
        findViewById<EditText>(R.id.editTextEmail).text.clear()
        findViewById<EditText>(R.id.editTextAvatar).text.clear()
    }
}
