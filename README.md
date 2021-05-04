# Prima vista trainer
_Ohjelmistotekniikka-harjoitustyö_

---

Sovellus tulee olemaan työkalu soittotaidon kehittämiseen. Musiikkitermi _prima vista_ tarkoittaa soittamista ensi näkemältä.
Käyttäjälle generoidaan soitettavaa ja nuotit ruudulle saatuaan käyttäjä soittaa annetun "kappaleen" ja sovellus antaa palautetta.
Sovellus generoi tällä hetkellä nuotteja käyttäjälleen C-duurissa ja käyttäjä voi edetä kappaleessa soittamalla nuotteja joko midi-soittimella tai näppäimistöllä.
Mikäli haluat käyttää midi-soitinta, riittää että kytket soittimesi tietokoneeseen: harjoitusmoodiin siirryttäessä ohjelma kuuntelee kaikkia löytämiään midi-sisääntuloja.
Tietokoneen näppäimistölläkin voi soittaa. Nuoteille on asetettu loogiset näppäimet: A, S, D, F, G, H, J -näppäimet vastaavat pianon valkoisia koskettimia C, D, E, F, G, A, B sekä näppäimet W, E, T, Y, U vastaavat mustia koskettimia C#/Db, D#/Eb, F#/Gb, G#/Ab, A#/Bb. Harjoittelun voi keskeyttää escape-näppäimellä.

## Dokumentaatio

---

[Vaativuusmäärittely](/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](/dokumentaatio/kayttoohje.md)

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

Checkstylen voi suorittaa komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```

JavaDocin generointi suoritetaan komennolla
```
mvn javadoc:javadoc
```

Jar-tiedoston luonti onnistuu komennolla 
```
mvn package
```
Huomaa, että sovellus vaatii toimiakseen oikeat fontit hakemistoon `resources/fonts`, jotka löytyvät tästä repositoriosta.


