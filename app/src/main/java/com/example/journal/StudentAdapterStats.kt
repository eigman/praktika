package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapterStats(private var students: List<StudentWithAttendance>) : RecyclerView.Adapter<StudentAdapterStats.StudentViewHolderStats>() {

    private lateinit var listener: OnItemClickListener

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
        val studentWithAttendance = students[position]
        holder.bind(position + 1, studentWithAttendance)
    }

    override fun getItemCount(): Int = students.size

    fun updateList(newStudents: List<StudentWithAttendance>) {
        students = newStudents
        notifyDataSetChanged()
    }

    class StudentViewHolderStats(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewIndexStats: TextView = itemView.findViewById(R.id.textViewIndexStats)
        private val textViewStudentStats: TextView = itemView.findViewById(R.id.textViewStudentStats)
        private val textViewAttendanceStats: TextView = itemView.findViewById(R.id.textViewAttendanceStats)

        fun bind(index: Int, studentWithAttendance: StudentWithAttendance) {
            textViewIndexStats.text = index.toString()
            textViewStudentStats.text = ". ${studentWithAttendance.student.SURNAME} ${studentWithAttendance.student.NAME} ${studentWithAttendance.student.PATRONYMIC}"
            textViewAttendanceStats.text = studentWithAttendance.attendanceCount.toString()
        }
    }
}
