package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(
    private var scheduleList: List<Schedule>,
    private var disciplineList: List<Discipline>
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]
        holder.bind(schedule, disciplineList)
    }

    override fun getItemCount(): Int = scheduleList.size

    fun updateList(newScheduleList: List<Schedule>, newDisciplineList: List<Discipline>) {
        scheduleList = newScheduleList
        disciplineList = newDisciplineList
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

        fun bind(schedule: Schedule, disciplineList: List<Discipline>) {
            textViewDate.text = schedule.DATE_PAIR
            val disciplinesForDate = disciplineList.filter { it.ID_DISCIPLINE == schedule.ID_DISCIPLINE }
            val adapter = DisciplineAdapterSecond(disciplinesForDate)
            recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }
}
