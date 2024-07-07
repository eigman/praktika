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
import com.example.journal.databinding.ActivityAddDisciplineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class AddDisciplineActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddDisciplineBinding
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        val db = MainDb.getDb(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_discipline)
        binding = ActivityAddDisciplineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)
            binding.addDiscipline.setOnClickListener{
                val dialogView = layoutInflater.inflate(R.layout.dialog_add_discipline, null)
                val editTextDisciplineName = dialogView.findViewById<EditText>(R.id.disciplineName)

                builder.setView(dialogView)
                    .setTitle("Добавьте дисциплину")
                    .setCancelable(true)
                    .setPositiveButton("Далее") { dialogInterface, it ->


                        Thread {
                            val disciplineName = editTextDisciplineName.text.toString()
                            val groupNumber = db.getDao().selectGroupNumber()
                            val discipline = Discipline(null, disciplineName, groupNumber)
                            db.getDao().insertDiscipline(discipline)
                        }.start()

                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("Стоп") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
            }
        }
    }
