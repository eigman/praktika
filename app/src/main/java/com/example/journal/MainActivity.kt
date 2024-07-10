package com.example.journal

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.journal.databinding.ActivityMainBinding

import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.group.setOnClickListener {
            val intent = Intent(this, AddGroupActivity::class.java)
            startActivity(intent)
        }
        binding.student.setOnClickListener {
            val intent = Intent(this, ListStudentActivity::class.java)
            startActivity(intent)
        }


    }


}