package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapterStats(private var students: List<Student>) : RecyclerView.Adapter<StudentAdapterStats.StudentViewHolderStats>() {

    private lateinit var listener: OnItemClickListener
    private var attendances: Map<Int, Triple<Int, Int, Int>> = emptyMap()

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
        val attendanceData = attendances[student.ID_STUDENT] ?: Triple(0, 0, 0)
        holder.bind(position + 1, student, attendanceData)
    }

    override fun getItemCount(): Int = students.size

    fun updateList(newStudents: List<Student>, newAttendances: Map<Int, Triple<Int, Int, Int>>) {
        students = newStudents
        attendances = newAttendances
        notifyDataSetChanged()
    }

    class StudentViewHolderStats(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewIndexStats: TextView = itemView.findViewById(R.id.textViewIndexStats)
        private val textViewStudentStats: TextView = itemView.findViewById(R.id.textViewStudentStats)
        private val textViewAttendanceStats: TextView = itemView.findViewById(R.id.textViewAttendanceStats)
        private val textViewHoursStats: TextView = itemView.findViewById(R.id.textViewHoursStats)
        private val textViewSkipsStats: TextView = itemView.findViewById(R.id.textViewSkipsStats)
        private val textViewYes: TextView = itemView.findViewById(R.id.t88)

        fun bind(index: Int, student: Student, attendanceData: Triple<Int, Int, Int>) {
            textViewIndexStats.text = index.toString()
            textViewStudentStats.text = "${student.SURNAME} ${student.NAME} ${student.PATRONYMIC}"
            textViewAttendanceStats.text = (attendanceData.first - attendanceData.third).toString() // Посещений
            textViewHoursStats.text = ((attendanceData.first - attendanceData.third) *2).toString() // Часы
            textViewSkipsStats.text = attendanceData.first.toString() // Пропуски
            textViewYes.text = attendanceData.third.toString()
        }
    }

    fun getItem(position: Int): Student {
        return students[position]
    }
}
