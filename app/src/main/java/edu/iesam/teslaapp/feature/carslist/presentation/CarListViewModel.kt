package edu.iesam.teslaapp.feature.carslist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.iesam.teslaapp.feature.carslist.domain.Car
import edu.iesam.teslaapp.feature.carslist.domain.GetCarUseCase
import edu.iesam.teslaapp.feature.carslist.domain.GetCarsUseCase

class CarListViewModel(
    private val getCarUseCase: GetCarUseCase,
    private val getCarsUseCase: GetCarsUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    data class UiState(
        val isLoading: Boolean = false,
        val hasError: Boolean = false,
        val car: Car? = null,
        val cars: List<Car>? = null
    )

    fun loadCars() {
        _uiState.value = UiState(isLoading = true)
        _uiState.value = UiState(cars = getCarsUseCase())
    }

    fun loadCarById(id: String) {
        _uiState.value = UiState(isLoading = true)
        _uiState.value = UiState(car = getCarUseCase(id))
    }

    companion object {
        fun factory(getCarUseCase: GetCarUseCase, getCarsUseCase: GetCarsUseCase) =
            viewModelFactory { initializer { CarListViewModel(getCarUseCase, getCarsUseCase) } }
    }

}