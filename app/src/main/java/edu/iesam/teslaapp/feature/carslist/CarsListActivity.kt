package edu.iesam.teslaapp.feature.carslist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.iesam.marvelapp.core.serialization.GsonJsonSerializer
import edu.iesam.teslaapp.R
import edu.iesam.teslaapp.feature.carslist.data.CarDataRepository
import edu.iesam.teslaapp.feature.carslist.data.local.CarXmlLocalDataSource
import edu.iesam.teslaapp.feature.carslist.data.remote.CarApiRemoteDataSource
import edu.iesam.teslaapp.feature.carslist.domain.Car
import edu.iesam.teslaapp.feature.carslist.domain.GetCarUseCase
import edu.iesam.teslaapp.feature.carslist.domain.GetCarsUseCase
import edu.iesam.teslaapp.feature.carslist.presentation.CarListViewModel

class CarsListActivity : AppCompatActivity() {
    lateinit var getCarsUseCase: GetCarsUseCase
    lateinit var getCarUseCase: GetCarUseCase
    val viewModel: CarListViewModel by viewModels {
        CarListViewModel.factory(getCarUseCase, getCarsUseCase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getCarsUseCase = GetCarsUseCase(
            CarDataRepository(
                CarXmlLocalDataSource(
                    this,
                    GsonJsonSerializer.create()
                ), CarApiRemoteDataSource
                    ()
            )
        )
        getCarUseCase = GetCarUseCase(
            CarDataRepository(
                CarXmlLocalDataSource(
                    this,
                    GsonJsonSerializer.create()
                ), CarApiRemoteDataSource
                    ()
            )
        )
        initObserver()
        initListeners()
        //cargo aquí los coches para que se pueda probar, con que se cargaran la primera vez valdría porque se estan persistiendo los datos
        viewModel.loadCars()
    }

    private fun initListeners() {
        val labelCarId = findViewById<EditText>(R.id.input_car_id)
        val actionFind = findViewById<Button>(R.id.action_find)
        actionFind.setOnClickListener {
            buttonFindClicked(labelCarId.text.toString())
        }
    }

    private fun initObserver() {
        viewModel.uiState.observe(this) { uiState ->
            bind(uiState)
        }
    }

    private fun bind(uiState: CarListViewModel.UiState) {
        //isLoading y hasError no los implementamos porque no tenemos aun

        uiState.cars?.let {
            renderCarSize(it)
        }
        if (uiState.car == null) {
            renderNoCar()
        } else {
            uiState.car.let {
                renderCarCard(it)
            }
        }
    }

    private fun renderCarCard(it: Car) {
        findViewById<TextView>(R.id.no_car).visibility = View.GONE
        findViewById<TextView>(R.id.content_brand).text = it.brand
        findViewById<TextView>(R.id.content_model).text = it.model
        findViewById<TextView>(R.id.content_engine).text = it.engine
        findViewById<TextView>(R.id.content_price).text = it.price
        findViewById<CardView>(R.id.car_card).visibility = View.VISIBLE
    }

    private fun renderNoCar() {
        findViewById<CardView>(R.id.car_card).visibility = View.GONE
        findViewById<TextView>(R.id.no_car).visibility = View.VISIBLE
    }

    private fun renderCarSize(cars: List<Car>) {
        findViewById<TextView>(R.id.size_text).text = cars.size.toString()
    }


    private fun buttonFindClicked(carId: String) {
        //el usuario ha pulsado el botón.
        Log.d(CarsListActivity::class.java.simpleName, carId)
        viewModel.loadCarById(carId)
    }
}