package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private var students: List<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(position + 1, student)
    }

    override fun getItemCount(): Int = students.size

    fun updateList(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    fun deleteStudent(position: Int) {
        // Удаляем студента из списка по позиции
        students = students.filterIndexed { index, _ -> index != position }
        notifyDataSetChanged()
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewIndex: TextView = itemView.findViewById(R.id.textViewIndex)
        private val textViewStudent: TextView = itemView.findViewById(R.id.textViewStudent)

        fun bind(index: Int, student: Student) {
            textViewIndex.text = index.toString()
            textViewStudent.text = ". ${student.SURNAME} ${student.NAME} ${student.PATRONYMIC}"
        }
    }


}