package edu.iesam.teslaapp.feature.carslist.domain

interface CarRepository {
    fun getAll(): List<Car>
    fun getCarById(id: String): Car?
}