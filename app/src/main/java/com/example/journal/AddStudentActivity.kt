package com.example.journal

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import com.example.journal.databinding.ActivityAddGroupBinding
import com.example.journal.databinding.AddStudentBinding

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: AddStudentBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = MainDb.getDb(this)
        db.getDao().selectStudents().asLiveData().observe(this) { list ->
            binding.textView2.text = ""
            list.forEach {
                val text = "id: ${it.ID_STUDENT}, " +
                        "group: ${it.GROUP_NUMBER}, " +
                        "surname: ${it.SURNAME}, " +
                        "name: ${it.NAME}, " +
                        "patronymic: ${it.PATRONYMIC}\n"
                binding.textView2.append(text)
            }
        }
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


                    Thread {
                        val surnameStudent = editTextSurnameStudent.text.toString()
                        val nameStudent = editTextNameStudent.text.toString()
                        val patronymicStudent = editTextPatronymicStudent.text.toString()
                        val groupNumber = db.getDao().selectGroupNumber()
                        val student = Student(null, groupNumber, nameStudent, surnameStudent, patronymicStudent)
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