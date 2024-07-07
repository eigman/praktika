package com.example.journal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.journal.databinding.AddGroupBinding
import com.example.journal.databinding.ListStudentsBinding

class ListStudentsActivity : AppCompatActivity() {
    private lateinit var binding: ListStudentsBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = mainDb.getDb(this)
        binding = ListStudentsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        binding.addStudent.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
            val editTextSurnameStudent = dialogView.findViewById<EditText>(R.id.surnameStudent)
            val editTextNameStudent = dialogView.findViewById<EditText>(R.id.nameStudent)
            val editTextPatronymicStudent = dialogView.findViewById<EditText>(R.id.patronymicStudent)

            builder.setView(dialogView)
                .setTitle("Добавьте студента")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->

                    val surnameStudent = editTextSurnameStudent.text.toString()
                    val nameStudent = editTextNameStudent.text.toString()
                    val patronymicStudent = editTextPatronymicStudent.text.toString()
                    val groupNumber = db.getDao().getGroupNumber()
                    val student = students(null, groupNumber, nameStudent, surnameStudent, patronymicStudent)
                    Thread {
                        db.getDao().insertStudent(student)
                    }.start()

                    dialogInterface.dismiss()
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }

        binding.button5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}