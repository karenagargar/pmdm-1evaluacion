package edu.iesam.teslaapp.feature.carslist.data

import edu.iesam.teslaapp.feature.carslist.data.local.CarXmlLocalDataSource
import edu.iesam.teslaapp.feature.carslist.data.remote.CarApiRemoteDataSource
import edu.iesam.teslaapp.feature.carslist.domain.Car
import edu.iesam.teslaapp.feature.carslist.domain.CarRepository

class CarDataRepository (private val carXmlLocalDataSource: CarXmlLocalDataSource, private val carApiRemoteDataSource: CarApiRemoteDataSource) : CarRepository{
    override fun getAll(): List<Car> {
        //si local existe cojo la local, si no llamo a la api y la guaardo en local para la próx
        val localList: List<Car> = carXmlLocalDataSource.findAll()
        if (localList.isEmpty()) {
            val remoteList = carApiRemoteDataSource.getAll()
            carXmlLocalDataSource.save(remoteList)
            return remoteList
        } else {
            return localList
        }
    }

    override fun getCarById(id: String): Car? {
        return carXmlLocalDataSource.findById(id)
    }
}