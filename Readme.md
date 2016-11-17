# Air Chitecture

## Qu'est-ce que c'est
Une petite web app pour acheter des billets d'avion.

## Installer Node JS
1. Installer Node JS en fonction de votre OS:

    * SUr Windows : http://nodejs.org/download/
    * Sur Mac : http://nodejs.org/download/
    * Sur Ubuntu 14.04 (Voir node Linux pour d'autres distributions)
        <code>sudo apt-get update</code>
        <code>sudo apt-get install nodejs</code>
        <code>sudo apt-get install npm</code>

2. Vérifier que NodeJS est correctement installer en utilisant cette commande:
    <code>node -v</code>

3. Vérifier que Node Package Manager (npm) est correctement installer en utilisant cette commande:
    <code>npm -v</code>
4. Installer bower
    <code>npm install -g bower</code>

## Comment l'utiliser
* Se rendre au répertoire `/src/main/webapp` et effectuer la commande `bower install` pour installer les dépendances client
* Revenir à la racine du projet
* Exécuter `mvn clean install` pour installer le projet
* Exécuter `mvn exec:java` pour lancer le serveur
* L'application est alors disponible au `localhost:8081`
* L'API est disponible au `localhost:8081/api`
* `mvn test` pour faire rouler les tests
* Les tests d'intégrations utilisent le port `8080`

## API   
   
### Search

**Route** : `/api/search/flights`   
**Query params** :
- `from` (airport ID)
- `to` (airport ID)
- `datetime` (ISO local datetime format)
   
**Exemple 1** : `http://localhost:8081/api/search/flights?from=YQB&to=DUB` 
``` 
[
  {
    "flightNumber": "AF0001",
    "departureAirport": "YQB",
    "arrivalAirport": "DUB",
    "departureDate": "2017-04-23T20:15",
    "airlineCompany": "AirFrenette",
    "availableSeats": 100
  }
]
```

**Exemple 2** : `http://localhost:8081/api/search/flights?from=YUL&to=OSL&datetime=2018-06-14T21:00`
``` 
[
  {
    "flightNumber": "AF0003",
    "departureAirport": "YUL",
    "arrivalAirport": "OSL",
    "departureDate": "2018-06-14T21:00",
    "airlineCompany": "AirFrenette",
    "availableSeats": 42
  }
]
```

### Login

**Route** : `/api/auth/login`
**Form params(url-encoded)** :
- `email`
- `password`

**Exemple 1** : `http://localhost:8081/api/auth/login`
```
{
    "email":"bob@test3.com",
    "token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJib2JAdGVzdDMuY29tIiwiZXhwIjoxNDc1ODY1OTEwLCJpYXQiOjE0NzU3Nzk1MTB9.4sAjY41_LopDvem72qlJVFVWoajq7vh55912QYWRv-M"
}
```

### Signup

**Route** : `/api/auth/signup`
**Form params(url-encoded)** :
- `email`
- `password`

**Exemple 1** : `http://localhost:8081/api/auth/signup`
```
204 - No Content
```

### Weight Detector

 **Route** : `api/weightDetection`   
 
 **Exemple** : `http://localhost:8081/api/weightDetection`   
 ```
 {
     "luggageWeight": 45.5
 }
