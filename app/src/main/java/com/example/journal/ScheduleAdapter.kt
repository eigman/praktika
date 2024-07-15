package com.example.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScheduleAdapter(
    private var groupedSchedules: List<Pair<String, List<Discipline>>>
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val (date, disciplines) = groupedSchedules[position]
        holder.bind(date, disciplines)
    }

    override fun getItemCount(): Int = groupedSchedules.size

    fun updateList(newGroupedSchedules: List<Pair<String, List<Discipline>>>) {
        groupedSchedules = newGroupedSchedules
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

        fun bind(date: String, disciplines: List<Discipline>) {
            textViewDate.text = date
            val adapter = DisciplineAdapterSecond(disciplines)
            recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }
}