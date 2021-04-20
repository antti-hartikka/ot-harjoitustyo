# Prima vista trainer
_Ohjelmistotekniikka-harjoitustyö_

---

Sovellus tulee olemaan työkalu soittotaidon kehittämiseen. Musiikkitermi _prima vista_ tarkoittaa soittamista ensi näkemältä.
Käyttäjälle generoidaan soitettavaa ja nuotit ruudulle saatuaan käyttäjä soittaa annetun "kappaleen" ja sovellus antaa palautetta.
Sovellus generoi tällä hetkellä nuotteja käyttäjälleen C-duurissa ja käyttäjä voi edetä nuoteissa painamalla näppäimistöstä a-näppäintä.

## Dokumentaatio

---

[Vaativuusmäärittely](/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

---

Ohjelman voi käynnistää komennolla

```
mvn compile exec:java -Dexec.mainClass=trainerapp.Main
```

Testit saa suoritettua komennolla
```
mvn test
```
Testikattavuusraportin saa komennolla
```
mvn test jacoco:report
```
