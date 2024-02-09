package com.example.temperature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.temperature.repositores.MainRepository
import com.example.temperature.viewModel.MainViewModelFactory
import com.example.temperature.api.RetrofitService
import com.example.temperature.viewModel.MainViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale
import android.os.Handler
import android.os.Looper
import com.example.temperature.ui.MainScreen
import android.widget.Toast

class MainActivity : ComponentActivity() {

    private val REQUEST_CODE_LOCATION_PERMISSION = 100
    private lateinit var viewModel: MainViewModel
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 300 * 1000

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_CODE_LOCATION_PERMISSION
        )
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private fun getLastLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()

        } else{
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        getCityFromLocation(latitude, longitude)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Erro ao acessar localisação",
                        Toast.LENGTH_SHORT
                    ).show()

                }

        }

    }


    private fun startHandler(cityName: String) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewModel.getWeatherData(cityName)
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }

    private fun getCityFromLocation(latitude: Double, longitude: Double) {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        val cityName = addresses?.get(0)?.subAdminArea.toString()
        val estateName = addresses?.get(0)?.adminArea.toString()
        viewModel.setLocation(cityName, estateName)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isHandlerStarted = false

        val retrofitService = RetrofitService.getInstance()
        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        )

        viewModel.cityName.observe(this, Observer { cityName ->
            viewModel.getWeatherData(cityName)
            if (!isHandlerStarted) {
                startHandler(cityName)
                isHandlerStarted = true
            }
        })

        getLastLocation()

        setContent { MainScreen(viewModel)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(
                    this,
                    "Permissão de localização necessária",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}






