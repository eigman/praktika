package com.example.journal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journal.databinding.AddStudentBinding
import com.example.journal.databinding.ListStudentBinding

class ListStudentActivity : AppCompatActivity() {
    private lateinit var binding: ListStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ListStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MainDb.getDb(this)

        val studentAdapter = ListStudentAdapter(emptyList())
        binding.recyclerViewStudents.apply {
            layoutManager = LinearLayoutManager(this@ListStudentActivity)
            adapter = studentAdapter
        }

        // Установка наблюдателя
        db.getDao().selectStudents().asLiveData().observe(this) { list ->
            studentAdapter.updateList(list)
        }


        binding.editStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }
}