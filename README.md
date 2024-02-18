# Weather app
Aplicativo android que exibe informações climaticas com base nas informações fornecidas pela api do OpenWeather, utlilizando retrofit, padrão de arquitetura MVVM, injeção de dependencia utilizando koin e jetpack compose

## Funcionamento
O aplicativo solicita a localização do usuário, caso seja concedida, faz uma requisição via Retrofit para a API, informando a cidade do usuário a fim de receber os dados climaticos daquela região. Em seguida, ele aciona um handler que repete o procidimento de cinco em cinco minutos, o handler é interrompido quando o aplicativo vai para segundo plano utilizando os metodos onResume e onStop, e reacionado quando o aplicativo volta a primeiro plano. Enquanto a requisição é feita uma barra de progresso é exibida na tela, e ao receber os dados o aplicativo exibe algumas das informações climaticas disponibilizadas pela api na tela em um layout.

## Principais frameworks e bibliotecas utilizadas
- retrofit
- okhttp3
- jetpack compose
- koin
- play-services-location

## Features
- Responsividade no modo landscape
- Interrupção de task quando o aplicativo esta em segundo plano
- Uso da localização do dispositivo para exibir informações com base na localização do usuário
- Atualização automatica dos dados
- Async image que muda dependendo da condição climatica e periodo do dia

## Arquitetura
Foi utilizado padrão de arquitetura MVVM com LiveData 

## Api utilizada
O aplicativo utiliza a api do OpenWeather

## Screenshots
<img src="https://github.com/JoaoViniciusLima/WeatherApp/assets/87715417/72c7c8fe-1850-4cc2-90be-4aa9a9f4d8be" alt="portrait" width="350"/>
<img src="https://github.com/JoaoViniciusLima/WeatherApp/assets/87715417/c0a95b56-d58e-491e-af99-3a231579db6e" alt="landscape" width="700" />





