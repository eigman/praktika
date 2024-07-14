package com.example.journal

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = MainDb.getDb(this)

        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        lifecycleScope.launch {
            val attendanceMap = mutableMapOf<Int, Int>()
            val totalPairsMap = mutableMapOf<Int, Int>() // Новый мап для общего количества пар

            if (discipline == "Все") {
                val attendances = db.getDao().selectAllAttendance()
                attendances.forEach {
                    attendanceMap[it.ID_STUDENT] = attendanceMap.getOrDefault(it.ID_STUDENT, 0) + it.YESORNO
                }
                val schedules = db.getDao().selectAllSchedule()
                schedules.forEach {
                    totalPairsMap[it.ID_DISCIPLINE] = totalPairsMap.getOrDefault(it.ID_DISCIPLINE, 0) + 1
                }
            } else {
                val disciplineId = db.getDao().getDisciplineIdByName(discipline)
                val attendances = db.getDao().selectAttendanceByDiscipline(disciplineId)
                attendances.forEach {
                    attendanceMap[it.ID_STUDENT] = attendanceMap.getOrDefault(it.ID_STUDENT, 0) + it.YESORNO
                }
                val schedules = db.getDao().selectScheduleByDiscipline(disciplineId)
                totalPairsMap[disciplineId] = schedules.size
            }

            db.getDao().selectStudents().asLiveData().observe(this@Stats) { list ->
                studentAdapterStats.updateList(list, attendanceMap, totalPairsMap, discipline)
            }
        }
    }

    private fun observeStudents() {
        db.getDao().selectStudents().asLiveData().observe(this) { list ->
            val selectedItem = binding.spinnerDisciplines.selectedItem
            if (selectedItem != null) {
                updateAttendanceData(selectedItem.toString())
            }
        }
    }
}
