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
- Imagem que muda dependendo da condição climatica e periodo do dia

## Arquitetura
Foi utilizado padrão de arquitetura MVVM com LiveData 

## Api utilizada
O aplicativo utiliza a api do OpenWeather

## Screenshots
<img src="https://github.com/JoaoViniciusLima/Weather-App-jetpack-compose-retrofit-koin-mvvm/assets/87715417/8c25e835-49cf-4d4c-85b2-5916dcb3d4c8" alt="portrait" width="350"/>
<img src="https://github.com/JoaoViniciusLima/Weather-App-jetpack-compose-retrofit-koin-mvvm/assets/87715417/8b382dc5-805c-44b3-abbf-b0c03da8c078" alt="landscape" width="700" />





