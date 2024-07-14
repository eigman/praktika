package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapterStats (private var students: List<Student>) : RecyclerView.Adapter<StudentAdapterStats.StudentViewHolderStats>() {

    private lateinit var listener: OnItemClickListener
    private var attendances: Map<Int, Int> = emptyMap()

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
        val attendanceCount = attendances[student.ID_STUDENT] ?: 0
        holder.bind(position + 1, student, attendanceCount)
    }

    override fun getItemCount(): Int = students.size

    fun updateList(newStudents: List<Student>, newAttendances: Map<Int, Int>) {
        students = newStudents
        attendances = newAttendances
        notifyDataSetChanged()
    }

    class StudentViewHolderStats(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewIndexStats: TextView = itemView.findViewById(R.id.textViewIndexStats)
        private val textViewStudentStats: TextView = itemView.findViewById(R.id.textViewStudentStats)
        private val textViewAttendanceStats: TextView = itemView.findViewById(R.id.textViewAttendanceStats)

        fun bind(index: Int, student: Student, attendanceCount: Int) {
            textViewIndexStats.text = index.toString()
            textViewStudentStats.text = ". ${student.SURNAME} ${student.NAME} ${student.PATRONYMIC}"
            textViewAttendanceStats.text = attendanceCount.toString()
        }
    }

    fun getItem(position: Int): Student {
        return students[position]
    }
}
