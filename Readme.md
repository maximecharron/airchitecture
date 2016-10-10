# Air Chitecture

## Qu'est-ce que c'est
Une petite web app pour acheter des billets d'avion.

## Comment l'utiliser
* Se rendre au répertoire `/src/main/webapp` et effectuer la commande `bower install` pour installer les dépendances client
* Exécuter `mvn exec:java` pour lancer le serveur
* L'application est alors disponible au `localhost:8081`
* L'API est disponible au `localhost:8081/api`
* `mvn exec:java` pour faire tourner le serveur web sur le port `8081`
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

### Weight Detector   
    
 **Route** : `api/weightDetection`   
 
 **Exemple** : `http://localhost:8081/api/weightDetection`   
 ```
 {
     "weight": 45.5
 }
