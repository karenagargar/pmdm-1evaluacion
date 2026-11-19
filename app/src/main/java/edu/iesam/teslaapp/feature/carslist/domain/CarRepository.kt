package edu.iesam.teslaapp.feature.carslist.domain

interface CarRepository {
    fun getAll(): List<Car>
}