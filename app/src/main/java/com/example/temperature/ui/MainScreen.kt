package com.example.temperature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import coil.compose.AsyncImage
import com.example.temperature.R
import com.example.temperature.models.WeatherData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

@Composable
fun DataCard(weatherCard: WeatherCard) {
    Card (modifier = Modifier
        .width(120.dp)
        .height(120.dp)
        .padding(10.dp),colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor("#3CF1EBF1"))),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = weatherCard.image), contentDescription = null, modifier = Modifier
                .size(25.dp),colorFilter = ColorFilter.tint(Color.White)
            )
            Text(text = weatherCard.name, color = Color.White, fontSize = 14.sp)
            Text(text = weatherCard.data, color = Color.White, fontSize = 14.sp)
        }

    }

}


fun dataWeatheritens(weatherData: WeatherData?): List<WeatherCard> {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT-3")
    val dataWeatheritens = listOf<WeatherCard>(
        WeatherCard(
            image = R.drawable.sunrise,
            name = "nascer do sol",
            data = dateFormat.format(Date(weatherData?.sunrise!! * 1000))

        ),
        WeatherCard(
            image = R.drawable.wind,
            name = "vento",
            data = weatherData?.wind + "m/s"

        ),
        WeatherCard(
            image = R.drawable.sunset,
            name = "por do sol",
            data = dateFormat.format(Date(weatherData?.sunset!! * 1000))

        ),
        WeatherCard(
            image = R.drawable.pressure,
            name = "pressão",
            data = weatherData?.pressure.toString()

        ),
        WeatherCard(
            image = R.drawable.humidity,
            name = "humidade",
            data = weatherData?.humidity.toString() + "%"

        )
    )

    return dataWeatheritens
}


fun getImageUrl(weatherCondicion: String?): String {

    val timeZone = TimeZone.getTimeZone("GMT-3")

    val calendar = Calendar.getInstance(timeZone)

    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

    val periodCodes = mapOf(
        "night" to "n",
        "day" to "d"
    )
    val periodCode = if (hourOfDay >= 18 || hourOfDay < 6 ){
        periodCodes["night"]
    } else {
        periodCodes["day"]
    }

    val weatherConditionsCodes = mapOf(
        "ceu limpo" to "01",
        "algumas nuvens" to "02",
        "nuvens dispersas" to "03",
        "nublado" to "04",
        "chuva leve" to "09",
        "chuva" to "10",
        "trovoada" to "11",
        "neve" to "13",
        "misto" to "50"
    )
    val weatherConditionCode = weatherConditionsCodes[weatherCondicion]
    val urlImage = "https://openweathermap.org/img/wn/$weatherConditionCode$periodCode@2x.png"

    return urlImage

}


@Composable
fun MainScreen(viewModel: MainViewModel) {
    val weatherData by viewModel.weatherData.observeAsState()
    val networkState by viewModel.networkState.observeAsState()
    val cityName by viewModel.cityName.observeAsState()
    val estateName by viewModel.estateName.observeAsState()

    ConstraintLayout (modifier = Modifier
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
    ){
        val(location, weatherImage, centerColumn, bottomGrid, progressiveBar) = createRefs()

        if(networkState == "success"){
            Text(text = "$cityName, $estateName", color = Color.White, fontSize = 21.sp, modifier = Modifier
                .constrainAs(location) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)


                } )

            AsyncImage(
                model = getImageUrl(weatherData?.description),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(weatherImage) {
                        bottom.linkTo(centerColumn.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(location.bottom)
                    }
                    .size(250.dp)

            )


            Column(modifier = Modifier
                .constrainAs(centerColumn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = weatherData?.description?.replaceFirstChar { it.uppercase() }.toString()
                , color = Color.White, fontSize = 18.sp)
            Text(text = weatherData?.temperature?.roundToInt().toString() + "°C", color = Color.White, fontSize = 90.sp)

        }

        LazyVerticalGrid(modifier = Modifier
            .constrainAs(bottomGrid) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

            },contentPadding = PaddingValues(0.dp) ,columns = GridCells.Fixed(3)){
            itemsIndexed(dataWeatheritens(weatherData)){
                    _, dataCardItem -> DataCard(dataCardItem)
            }

        }

        }else if(networkState == "error"){
            Text(text = "Erro de conexão!",
                modifier = Modifier.constrainAs(progressiveBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)

            },fontSize = 30.sp,color = Color.White
            )

        }else{
            CircularProgressIndicator(modifier = Modifier.constrainAs(progressiveBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)

            }, color = Color.White
            )

        }
}
}