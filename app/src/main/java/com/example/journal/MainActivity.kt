package com.example.journal

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
        val db = mainDb.getDb(this)
        binding.button.setOnClickListener {
            val group = group(
                binding.enNumber.text.toString(),
                binding.edGroup.text.toString()
            )
            Thread {
                db.getDao().insertGroup(group)
            }.start()


        }


    }


}