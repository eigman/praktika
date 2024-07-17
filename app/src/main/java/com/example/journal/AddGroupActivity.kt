package com.example.journal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import com.example.journal.databinding.ActivityAddGroupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class AddGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGroupBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = MainDb.getDb(this)

        binding = ActivityAddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        binding.addGroup.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_group, null)
            val editTextGroupNumber = dialogView.findViewById<EditText>(R.id.groupNumber)
            val editTextAmountOfStudents = dialogView.findViewById<EditText>(R.id.amountStudents)

            builder.setView(dialogView)
                .setTitle("Добавьте группу")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialogInterface, it ->

                    val groupNumber = editTextGroupNumber.text.toString().toInt()
                    val numberOfPeople = editTextAmountOfStudents.text.toString().toInt()

                    val group = Group(groupNumber, numberOfPeople)
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