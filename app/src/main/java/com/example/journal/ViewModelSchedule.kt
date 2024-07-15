package com.example.journal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

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
        return schedules.groupBy { it.DATE_PAIR }.map { (date, schedulesForDate) ->
            val disciplinesForDate = schedulesForDate.map { schedule ->
                disciplines.first { it.ID_DISCIPLINE == schedule.ID_DISCIPLINE }
            }
            date to disciplinesForDate
        }
    }

    fun insert(schedule: Schedule) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertSchedule(schedule)
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
