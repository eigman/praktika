package com.example.journal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.flowOn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScheduleRepository
    val allDisciplines: LiveData<List<String>>
    val groupedSchedules: MediatorLiveData<List<Pair<String, List<Discipline>>>>

    init {
        val dao = MainDb.getDb(application).getDao()
        repository = ScheduleRepository(dao)
        allDisciplines = repository.getAllDisciplines()

        val schedulesLiveData = repository.getAllSchedules().flowOn(Dispatchers.IO).asLiveData()
        val disciplinesLiveData = repository.getAllDisciplineEntities().flowOn(Dispatchers.IO).asLiveData()

        groupedSchedules = MediatorLiveData<List<Pair<String, List<Discipline>>>>()

        groupedSchedules.addSource(schedulesLiveData) { schedules ->
            val disciplines = disciplinesLiveData.value
            if (disciplines != null) {
                groupedSchedules.value = groupSchedulesByDate(schedules, disciplines)
            }
        }

        groupedSchedules.addSource(disciplinesLiveData) { disciplines ->
            val schedules = schedulesLiveData.value
            if (schedules != null) {
                groupedSchedules.value = groupSchedulesByDate(schedules, disciplines)
            }
        }
    }

    private fun groupSchedulesByDate(
        schedules: List<Schedule>,
        disciplines: List<Discipline>
    ): List<Pair<String, List<Discipline>>> {
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        return schedules.groupBy { it.DATE_PAIR }.toSortedMap(compareBy { dateFormat.parse(it) }).map { (date, schedulesForDate) ->
            val disciplinesForDate = schedulesForDate.map { schedule ->
                disciplines.first { it.ID_DISCIPLINE == schedule.ID_DISCIPLINE }
            }
            date to disciplinesForDate
        }
    }

    fun insert(schedule: Schedule) = viewModelScope.launch(Dispatchers.IO) {
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        val initialDate = dateFormat.parse(schedule.DATE_PAIR)!!
        val calendar = Calendar.getInstance()
        calendar.time = initialDate

        for (i in 0 until 9) { 
            val newSchedule = schedule.copy(
                ID_PAIR = null,
                DATE_PAIR = dateFormat.format(calendar.time)
            )
            repository.insertSchedule(newSchedule)
            calendar.add(Calendar.DAY_OF_YEAR, 14)
        }
    }

    fun getDisciplineIdByName(name: String): LiveData<Int> {
        val result = MutableLiveData<Int>()
        viewModelScope.launch(Dispatchers.IO) {
            val disciplineId = repository.getDisciplineIdByName(name)
            result.postValue(disciplineId)
        }
        return result
    }
}
