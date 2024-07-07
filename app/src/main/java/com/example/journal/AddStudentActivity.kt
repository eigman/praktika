package com.example.journal

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.journal.databinding.ActivityAddGroupBinding
import com.example.journal.databinding.AddStudentBinding

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: AddStudentBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = MainDb.getDb(this)
        binding = AddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        binding.addStudent.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
            val editTextSurnameStudent = dialogView.findViewById<EditText>(R.id.surnameStudent)
            val editTextNameStudent = dialogView.findViewById<EditText>(R.id.nameStudent)
            val editTextPatronymicStudent = dialogView.findViewById<EditText>(R.id.patronymicStudent)

            builder.setView(dialogView)
                .setTitle("Добавьте группу")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->

                    val surnameStudent = editTextSurnameStudent.text.toString()
                    val nameStudent = editTextNameStudent.text.toString()
                    val patronymicStudent = editTextPatronymicStudent.text.toString()
                    val groupNumber = 1241
                    val student = Student(null, groupNumber, surnameStudent, nameStudent, patronymicStudent)
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


    }
}