package com.example.journal

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journal.databinding.ActivityStatsBinding
import kotlinx.coroutines.launch

class Stats : AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var db: MainDb
    private lateinit var studentAdapterStats: StudentAdapterStats
    private lateinit var datePickerFrom: EditText
    private lateinit var datePickerTo: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = MainDb.getDb(this)

        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datePickerFrom = findViewById(R.id.datePickerFrom)
        datePickerTo = findViewById(R.id.datePickerTo)

        studentAdapterStats = StudentAdapterStats(emptyList())
        binding.RecyclerViewStats.apply {
            layoutManager = LinearLayoutManager(this@Stats)
            adapter = studentAdapterStats
        }

        setupSpinner()
        observeStudents()
    }

    private fun setupSpinner() {
        db.getDao().selectAllDisciplineNames().observe(this) { disciplineNames ->
            val allDisciplines = listOf("Все") + disciplineNames
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allDisciplines)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDisciplines.adapter = adapter

            binding.spinnerDisciplines.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedDiscipline = allDisciplines[position]
                    updateAttendanceData(selectedDiscipline)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Ничего не делать
                }
            }
        }
    }

    private fun updateAttendanceData(discipline: String) {
        val dateFrom = datePickerFrom.toString()
        val dateTo = datePickerTo.toString()

        lifecycleScope.launch {
            val attendanceMap = mutableMapOf<Int, Triple<Int, Int, Int>>()

            if (discipline == "Все") {
                val attendances = db.getDao().selectAllAttendance()
                attendances.forEach {
                    val current = attendanceMap[it.ID_STUDENT] ?: Triple(0, 0, 0)
                    attendanceMap[it.ID_STUDENT] = Triple(
                        current.first + 1,
                        current.second + it.YESORNO,
                        current.third + it.YESORNO
                    )
                }
            } else {
                val disciplineId = db.getDao().getDisciplineIdByName(discipline)
                val attendances = db.getDao().selectAttendanceByDiscipline(disciplineId)
                attendances.forEach {
                    val current = attendanceMap[it.ID_STUDENT] ?: Triple(0, 0, 0)
                    attendanceMap[it.ID_STUDENT] = Triple(
                        current.first + 1,
                        current.second + it.YESORNO,
                        current.third + it.YESORNO
                    )
                }
            }

            db.getDao().selectStudents().asLiveData().observe(this@Stats) { list ->
                studentAdapterStats.updateList(list, attendanceMap)
            }
        }
    }



    private fun observeStudents() {
        db.getDao().selectStudents().asLiveData().observe(this) { list ->
            updateAttendanceData(binding.spinnerDisciplines.selectedItem.toString())
        }
    }
}
