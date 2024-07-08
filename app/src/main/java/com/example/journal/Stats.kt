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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Stats : AppCompatActivity() {
    private lateinit var binding: ActivityStatsBinding
    private lateinit var builder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = MainDb.getDb(this)

        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentAdapterStats = StudentAdapterStats(emptyList())
        binding.RecyclerViewStats.apply {
            layoutManager = LinearLayoutManager(this@Stats)
            adapter = studentAdapterStats
        }


        lifecycleScope.launch {
            val disciplines = withContext(Dispatchers.IO) {
                db.getDao().getAllDisciplineNames()
            }
            val adapter = ArrayAdapter(this@Stats, android.R.layout.simple_spinner_item, disciplines)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDisciplines.adapter = adapter
        }

        binding.spinnerDisciplines.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDiscipline = parent.getItemAtPosition(position) as String
                lifecycleScope.launch {
                    db.getDao().getAttendanceForDiscipline(selectedDiscipline).collect { list ->
                        studentAdapterStats.updateList(list)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        builder = AlertDialog.Builder(this)
    }
}
