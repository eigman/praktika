package com.example.journal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScheduleRepository
    val allDisciplines: LiveData<List<String>>

    init {
        val dao = MainDb.getDb(application).getDao()
        repository = ScheduleRepository(dao)
        allDisciplines = repository.getAllDisciplines()
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

