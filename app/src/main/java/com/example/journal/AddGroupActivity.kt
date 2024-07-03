package com.example.journal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.journal.databinding.ActivityMainBinding
import com.example.journal.databinding.AddGroupBinding

class AddGroupActivity : AppCompatActivity() {
    private lateinit var binding: AddGroupBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = mainDb.getDb(this)
        binding = AddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        binding.addGroup.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_group, null)
            val editTextGroupNumber = dialogView.findViewById<EditText>(R.id.groupNumber)
            val editTextNumberOfPeople = dialogView.findViewById<EditText>(R.id.amountStudents)

            builder.setView(dialogView)
                .setTitle("Добавьте группу")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->

                    val groupNumber = editTextGroupNumber.text.toString().toInt()
                    val numberOfPeople = editTextNumberOfPeople.text.toString()

                    val group = group(groupNumber, numberOfPeople)
                    Thread {
                        db.getDao().insertGroup(group)
                    }.start()

                    dialogInterface.dismiss()
                }
                .setNegativeButton("No") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }

        binding.button3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}