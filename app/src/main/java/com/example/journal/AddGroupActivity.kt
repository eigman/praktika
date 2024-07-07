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
import com.example.journal.databinding.ActivityAddGroupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGroupBinding
    private lateinit var builder: AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MainDb.getDb(this)
        CoroutineScope(Dispatchers.IO).launch {
            db.getDao().insertGroup(Group(2, 2))

        }
        binding.button3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            val groupNumber = binding.groupNumber1.text.toString().toInt()
            val numberOfPeople = binding.amountStudents1.text.toString().toInt()

            Log.d("XUI", "$groupNumber, $numberOfPeople")
        }
    }
}