package com.example.journal

import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.journal.db.db
import com.example.journal.ui.theme.JournalTheme

class MainActivity : ComponentActivity() {
    var db:db?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = db(this)
        insertData()
    }


    private fun insertData(){
        val sqlQuery = "INSERT INTO GROUP(ID_GROUP, AMOUNT_STUDENTS) VALUES (1241, 54)"
        db?.executeQuery(sqlQuery)
    }
}
