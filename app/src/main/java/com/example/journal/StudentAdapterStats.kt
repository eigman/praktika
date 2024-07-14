package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapterStats(private var students: List<Student>) : RecyclerView.Adapter<StudentAdapterStats.StudentViewHolderStats>() {

    private lateinit var listener: OnItemClickListener
    private var attendanceMap: Map<Int, Int> = emptyMap()
    private var totalPairsMap: Map<Int, Int> = emptyMap()
    private var currentDiscipline: String = "Все"

    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolderStats {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item_stats, parent, false)
        return StudentViewHolderStats(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolderStats, position: Int) {
        val student = students[position]
        val attendance = attendanceMap[student.ID_STUDENT] ?: 0
        val totalPairs = if (currentDiscipline == "Все") totalPairsMap.values.sum() else totalPairsMap[attendanceMap.keys.first()] ?: 0
        holder.bind(position + 1, student, attendance, totalPairs)
    }

    override fun getItemCount(): Int = students.size

    fun updateList(newStudents: List<Student>, newAttendanceMap: Map<Int, Int>, newTotalPairsMap: Map<Int, Int>, discipline: String) {
        students = newStudents
        attendanceMap = newAttendanceMap
        totalPairsMap = newTotalPairsMap
        currentDiscipline = discipline
        notifyDataSetChanged()
    }

    class StudentViewHolderStats(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewIndexStats: TextView = itemView.findViewById(R.id.textViewIndexStats)
        private val textViewStudentStats: TextView = itemView.findViewById(R.id.textViewStudentStats)
        private val textViewAttendanceStats: TextView = itemView.findViewById(R.id.textViewAttendanceStats)

        fun bind(index: Int, student: Student, attendance: Int, totalPairs: Int) {
            textViewIndexStats.text = index.toString()
            textViewStudentStats.text = ". ${student.SURNAME} ${student.NAME} ${student.PATRONYMIC}"
            textViewAttendanceStats.text = "$attendance / $totalPairs"
        }
    }

    fun getItem(position: Int): Student {
        return students[position]
    }
}
