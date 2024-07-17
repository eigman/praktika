package com.example.journal

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.CheckBox
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class PairWithDiscipline(
    val ID_PAIR: Int,
    val NAME: String,
    val TYPE: String
)

class attendanceActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var db: MainDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        tableLayout = findViewById(R.id.tableAttendance)
        db = MainDb.getDb(this)

        val date = intent.getStringExtra("DATE") ?: "20.08.24" // Получить дату из Intent

        lifecycleScope.launch {
            val students = getStudents()
            val pairsWithDiscipline = getPairsWithDisciplineByDate(date)
            val attendance = getAttendance()

            withContext(Dispatchers.Main) {
                createTable(students, pairsWithDiscipline, attendance)
            }
        }
    }

    private suspend fun getStudents(): List<Student> = withContext(Dispatchers.IO) {
        db.getDao().getStudentsDirect()
    }

    private suspend fun getPairsWithDisciplineByDate(date: String): List<PairWithDiscipline> = withContext(Dispatchers.IO) {
        db.getDao().getPair(date)
    }

    private suspend fun getAttendance(): List<Attendance> = withContext(Dispatchers.IO) {
        db.getDao().getAttDirect()
    }

    @SuppressLint("SetTextI18n")
    private fun createTable(students: List<Student>, pairsWithDiscipline: List<PairWithDiscipline>, attendance: List<Attendance>) {
        tableLayout.removeAllViews()

        val headerRow = TableRow(this)
        headerRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)

        val emptyCell = TextView(this)
        emptyCell.text = ""
        emptyCell.gravity = Gravity.CENTER
        emptyCell.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        headerRow.addView(emptyCell)

        pairsWithDiscipline.forEachIndexed { index, pair ->
            val pairHeader = TextView(this)
            pairHeader.text = if (index == 0) "${pair.NAME}\n(${pair.TYPE})" else "\t${pair.NAME}\n(${pair.TYPE})"
            pairHeader.gravity = Gravity.CENTER
            pairHeader.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            headerRow.addView(pairHeader)
        }

        tableLayout.addView(headerRow)

        students.forEach { student ->
            val row = TableRow(this)
            row.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)

            val studentCell = TextView(this)
            studentCell.text = student.SURNAME
            studentCell.gravity = Gravity.CENTER
            studentCell.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            row.addView(studentCell)

            pairsWithDiscipline.forEach { pair ->
                val checkBox = CheckBox(this)
                val attRecord = attendance.find { it.ID_PAIR == pair.ID_PAIR && it.ID_STUDENT == student.ID_STUDENT }
                checkBox.isChecked = attRecord?.YESORNO == 1

                checkBox.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER
                }

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        student.ID_STUDENT?.let {
                            db.getDao().updateAttendance(pair.ID_PAIR, it, if (isChecked) 1 else 0)
                        }
                    }
                }
                row.addView(checkBox)
            }
            tableLayout.addView(row)
        }
    }
}
