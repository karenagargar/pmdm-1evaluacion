package edu.iesam.teslaapp.feature.carslist.domain

class GetCarsUseCase(private val repository: CarRepository) {
    operator fun invoke(): List<Car> {
        return repository.getAll()
    }
}