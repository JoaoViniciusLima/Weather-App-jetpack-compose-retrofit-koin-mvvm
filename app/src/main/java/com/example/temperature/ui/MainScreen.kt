package com.example.temperature.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.temperature.models.WeatherCard
import com.example.temperature.viewModel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import com.example.temperature.R
import com.example.temperature.models.MainScreenVariableSizes
import com.example.temperature.models.WeatherData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@Composable
fun WeatherCard(weatherCard: WeatherCard, screenVariableSizes: MainScreenVariableSizes) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .height(screenVariableSizes.cardSize)
        .padding(10.dp),colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor("#3CF1EBF1"))),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = weatherCard.image), contentDescription = null, modifier = Modifier
                .size(screenVariableSizes.cardImageSize),colorFilter = ColorFilter.tint(Color.White)
            )
            Text(text = weatherCard.name, color = Color.White, fontSize = screenVariableSizes.cardTextSize)
            weatherCard.data?.let { Text(text = it, color = Color.White, fontSize = screenVariableSizes.cardTextSize) }
        }

    }

}

fun weatherCardItems(weatherData: WeatherData?): List<WeatherCard> {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT-3")
    val dataWeatheritens = listOf<WeatherCard>(
        WeatherCard(
            image = R.drawable.sunrise,
            name = "nascer do sol",
            data = weatherData?.sunrise

        ),
        WeatherCard(
            image = R.drawable.wind,
            name = "vento",
            data = weatherData?.wind + "m/s"

        ),
        WeatherCard(
            image = R.drawable.sunset,
            name = "por do sol",
            data = weatherData?.sunset

        ),
        WeatherCard(
            image = R.drawable.pressure,
            name = "pressão",
            data = weatherData?.pressure
        ),
        WeatherCard(
            image = R.drawable.humidity,
            name = "humidade",
            data = weatherData?.humidity + "%"

        )
    )

    return dataWeatheritens
}


fun getConditionImageUrl(weatherId: Int): Int {

    val timeZone = TimeZone.getTimeZone("GMT-3")

    val calendar = Calendar.getInstance(timeZone)

    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

    val periodCode = if (hourOfDay >= 18 || hourOfDay < 6) {
        "night"
    } else {
        "day"
    }

    val weatherConditionsImages = mapOf(
        "thunderstorm_day" to R.drawable.thunderstorm,
        "thunderstorm_night" to R.drawable.thunderstorm,
        "rain_day" to R.drawable.rain,
        "rain_night" to R.drawable.rain,
        "snow_day" to R.drawable.snow,
        "snow_night" to R.drawable.snow,
        "clear_sky_day" to R.drawable.sun,
        "clear_sky_night" to R.drawable.moon,
        "few_clouds_day" to R.drawable.cloudy_day,
        "few_clouds_night" to R.drawable.cloudy_night,
        "cloud_day" to R.drawable.cloud,
        "cloud_night" to R.drawable.cloud
    )

    val weatherConditions = mapOf(
        200..232 to "thunderstorm",
        300..321 to "rain",
        500..531 to "rain",
        600..622 to "snow",
        511 to "snow",
        800 to "clear_sky",
        801 to "few_clouds",
        802..804 to "cloud",
    )

    val weatherCondition = weatherConditions[weatherId] ?:
    weatherConditions.entries.firstOrNull { (range, _) ->
        when (range) {
            is IntRange -> weatherId in range
            else -> false
        }
    }?.value

    return weatherConditionsImages[weatherCondition + "_$periodCode"]!!
}

@Composable
fun WeatherImage(modifier: Modifier, weatherId: Int?){
    Image(painter = painterResource(id = getConditionImageUrl(weatherId!!)), contentDescription = null, modifier = modifier

    )

}

@Composable
fun TemperatureText(size: TextUnit,temperature: String){
    Text(
        text = "$temperature°C",
        color = Color.White,
        fontSize = size
    )
}

@Composable
fun WeatherConditionText(description: String){
    Text(text = description.replaceFirstChar { it.uppercase() },
        color = Color.White,
        fontSize = 25.sp)
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val weatherData by viewModel.weatherData.observeAsState()
    val networkState by viewModel.networkState.observeAsState()
    val cityName by viewModel.cityName.observeAsState()
    val estateName by viewModel.estateName.observeAsState()
    val mainScreenVariableSizes: MainScreenVariableSizes

    val configuration = LocalConfiguration.current
    mainScreenVariableSizes = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
        MainScreenVariableSizes(70.dp,20.dp,10.sp,30.sp,100.dp)
    } else{
        MainScreenVariableSizes(130.dp,40.dp,14.sp,90.sp,280.dp)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(android.graphics.Color.parseColor("#122259")),
                        Color(
                            android.graphics.Color.parseColor("#9561a1")
                        )
                    )
                )
            )
            .padding(20.dp),
    ) {
        val (location, weatherImage, centerInfo, bottomGrid, progressiveBar, errorMessage) = createRefs()

        fun centerConstraint(constrainName: ConstrainedLayoutReference): Modifier {
            return Modifier
                .constrainAs(constrainName) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        }

        when (networkState) {
            "success" -> {
                Text(text = "$cityName, $estateName",
                    color = Color.White,
                    fontSize = 21.sp,
                    modifier = Modifier
                        .constrainAs(location) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })

                if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Row(
                        modifier = Modifier
                            .constrainAs(centerInfo) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(bottomGrid.top)
                                top.linkTo(location.bottom)

                            },verticalAlignment = Alignment.CenterVertically
                    ) {
                        WeatherImage(Modifier.size(mainScreenVariableSizes.weatherImageSize),weatherData?.weatherId)

                        Column(
                        ) {

                            WeatherConditionText(weatherData?.description!!)
                            TemperatureText(mainScreenVariableSizes.temperatureTextSize,
                                weatherData?.temperature!!)
                        }
                    }
                } else{

                    WeatherImage(Modifier.constrainAs(weatherImage) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(location.bottom)
                        bottom.linkTo(centerInfo.top)

                    }.size(mainScreenVariableSizes.weatherImageSize),weatherData?.weatherId)

                    Column(
                        modifier = Modifier
                            .constrainAs(centerInfo) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(location.bottom, 240.dp)
                                bottom.linkTo(bottomGrid.top)

                            }, horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        WeatherConditionText(weatherData?.description!!)
                        TemperatureText(mainScreenVariableSizes.temperatureTextSize,
                            weatherData?.temperature!!
                        )

                    }

                }

                LazyVerticalGrid(modifier = Modifier
                    .constrainAs(bottomGrid) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)

                    }, contentPadding = PaddingValues(0.dp), columns = GridCells.Fixed(3)
                ) {
                    itemsIndexed(weatherCardItems(weatherData)) { _, dataCardItem ->
                        WeatherCard(dataCardItem, mainScreenVariableSizes)
                    }

                }

            }

            "error" -> {
                Text(
                    text = "Erro de conexão!",
                    modifier = centerConstraint(errorMessage), fontSize = 30.sp, color = Color.White
                )

            }

            else -> {
                CircularProgressIndicator(
                    modifier = centerConstraint(progressiveBar), color = Color.White
                )

            }
        }
    }
}