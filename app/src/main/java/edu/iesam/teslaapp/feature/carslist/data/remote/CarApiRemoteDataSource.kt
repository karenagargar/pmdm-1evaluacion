package edu.iesam.teslaapp.feature.carslist.data.remote

import edu.iesam.teslaapp.feature.carslist.domain.Car

class CarApiRemoteDataSource {


    fun getAll(): List<Car> {
        return listOf(
            Car("1", "Tesla", "Terror1", "4.0L V8 Biturbo", "198.500"),
            Car("2", "Tesla", "Terror2", "4.0L V8 Biturbo", "198.500"),
            Car("3", "Tesla", "Terror3", "4.0L V8 Biturbo", "198.500"),
            Car("4", "Tesla", "Terror4", "4.0L V8 Biturbo", "198.500"),
            Car("5", "Tesla", "Terror5", "4.0L V8 Biturbo", "198.500"),
            Car("6", "Tesla", "Terror6", "4.0L V8 Biturbo", "198.500")
        )
    }

}