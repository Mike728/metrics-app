# MetricsApp

Live: http://elasticbeanstalk-eu-central-1-423953161034.s3-website.eu-central-1.amazonaws.com

Aplikacja, która cyklicznie pobiera kursy dwóch walut z api NBP i zapisuje je do bazy danych. Celem aplikacji jest obserwacja jak 
największej ilości danych istotnych z punktu widzenia developera, przechowywanie ich w bazie danych oraz prezentacja w przystępnej 
formie na wykresie.

- Java 8
- Spring WebFlux (https://spring.io/blog/2016/07/28/reactive-programming-with-spring-5-0-m1)
- Reactive MongoDB
- Amazon Cloud
- Vue.js
