package edu.iesam.teslaapp.feature.carslist.domain

class GetCarUseCase(private val repository: CarRepository) {
    operator fun invoke(id: String): Car? {
        return repository.getCarById(id)
    }
}