# tax-service
 * Guice 4.2: https://github.com/google/guice
 * Gson: https://github.com/google/gson
 * SparkJava: http://sparkjava.com/
 * Pseudo Strategy Pattern: https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
 
### GET: fetch all taxes in the database
```
curl -X GET \
  http://localhost:4567/taxes \
  -H 'cache-control: no-cache' \
  -H 'postman-token: ab7f7998-a156-fadf-dce5-15264ec44cd4'
```
#### Response (status = 200):
```
[
    {
        "id": "PIS",
        "value": 0.01,
        "createdAt": "2018-05-30T21:25:44.295"
    },
    {
        "id": "COFINS",
        "value": 0.01,
        "createdAt": "2018-05-30T21:25:44.296"
    },
    {
        "id": "ITBI",
        "value": 0.04,
        "createdAt": "2018-05-30T21:25:44.295"
    },
    {
        "id": "ISS",
        "value": 0.07,
        "createdAt": "2018-05-30T21:25:44.295"
    },
    {
        "id": "ICMS",
        "value": 0.17,
        "createdAt": "2018-05-30T21:25:44.295"
    }
]
```

### POST: create a new tax

```
curl -X POST \
  http://localhost:4567/taxes \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 7a80722b-658b-9044-07bf-2abffdab94d8' \
  -d '{	"id": "NEW_TAX", "value": "0.19" }'
```

####
Response (status = 201):
```
{
    "id": "NEW_TAX",
    "value": 0.19,
    "createdAt": "2018-05-30T21:18:59.669",
    "message": "New tax created successfully"
}
```

### POST: calculates a tax value
```
curl -X POST \
  http://localhost:4567/taxes/COFINS \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: aa5e4362-97de-8d8d-7ae1-36033bec2f65' \
  -d '{	"value": "31700.90" }'
```
Response (status = 201):
```
{
    "taxName": "COFINS",
    "taxValue": 317.009,
    "value": 0.01
}
```