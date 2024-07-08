package com.example.journal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.example.journal.databinding.ActivityMainBinding

import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        val typesOfPair = arrayOf("Л", "ПР", "ЛЕК")
        val numbersOfPair = arrayOf("1", "2", "3", "4", "5", "6")
        builder = AlertDialog.Builder(this)
        super.onCreate(savedInstanceState)
        val db = MainDb.getDb(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.group.setOnClickListener {
            val intent = Intent(this, AddGroupActivity::class.java)
            startActivity(intent)
        }
        binding.student.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
        binding.disciplines.setOnClickListener {
            val intent = Intent(this, AddDisciplinesActivity::class.java)
            startActivity(intent)
        }
        binding.addSchedule.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_schedule, null)
            val editTextPairDate = dialogView.findViewById<EditText>(R.id.editTextDate)

            val editSpinnerType: Spinner = dialogView.findViewById(R.id.spinnerType)
            val adapterType = ArrayAdapter(this, android.R.layout.simple_spinner_item, typesOfPair)
            adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editSpinnerType.adapter = adapterType

            val editSpinnerNumber: Spinner = dialogView.findViewById(R.id.spinnerNumber)
            val adapterNumber = ArrayAdapter(this, android.R.layout.simple_spinner_item, numbersOfPair)
            adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editSpinnerNumber.adapter = adapterNumber

            /*val disciplineNames = db.getDao().getAllDisciplineNames()
            val editSpinnerDiscipline: Spinner = dialogView.findViewById(R.id.spinnerDiscipline)
            val adapterDiscipline = ArrayAdapter(this, android.R.layout.simple_spinner_item, disciplineNames)
            adapterDiscipline.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editSpinnerDiscipline.adapter = adapterDiscipline*/

            builder.setView(dialogView)
                .setTitle("Добавьте расписание")
                .setCancelable(true)
                .setPositiveButton("Ок") { dialogInterface, it ->

                    Thread {
                        val typeOfPair = editSpinnerType.selectedItem.toString()
                        val numberOfPair = editSpinnerNumber.selectedItem.toString()
                        val pairDate = editTextPairDate.text.toString()
                        //val discipline =
                        val schedule = Schedule(null, 5454, pairDate, numberOfPair, typeOfPair)
                        db.getDao().insertSchedule(schedule)
                    }.start()

                    dialogInterface.dismiss()
                }
                .setNegativeButton("Отмена") { dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .show()
        }


    }


}