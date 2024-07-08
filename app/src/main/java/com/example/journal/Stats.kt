package com.example.journal

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journal.databinding.ActivityStatsBinding

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
            adapter = studentAdapterStats // Сохраняем ссылку на адаптер
        }


        db.getDao().selectStudents().asLiveData().observe(this) { list ->
            studentAdapterStats.updateList(list) // Вызываем метод на экземпляре адаптера
        }



        builder = AlertDialog.Builder(this)


    }
}
