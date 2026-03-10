## Instalacija Serverless plugin-a
```
serverless plugin install --name ime_plugin-a
```
Instalaciju plugina je dovoljno uraditi u istom folderu gde se nalazi serverless.yml ili u njegovom nadfolderu. Instalacija plugina se svodi na instaliranje JS biblioteke i kreiranje node_modules fodlera.

## Kreiranje Serverless servisa na osnovu templejta
```
serverless create --template ime_templejta --name ime_servisa
```
Upotrebom komande:
```
serverless create --help
```
Bice izlistani svi dostupni templejti.

## Pokretanje izvršavanja Step funkcije
```
serverless invoke stepf --name ime_funkcije --data '{"example":"data"}'
```
U slučaju izvršavanja komande na Windows OS-u neophodno je eskejpovati dvostruke navodnike.
