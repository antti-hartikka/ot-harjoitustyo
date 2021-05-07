# Vaatimusmäärittely


## Sovelluksen tarkoitus


Sovellus auttaa käyttäjäänsä kehittämään musiikkinuottien lukutaitoa ja suoraan nuoteista soittamista _prima vista_ -tyylisesti. 
Sovellus antaa käyttäjälleen nuotteja soitettavaksi ja ottaa soittajalta syötteen joko usb-midi-soittimelta tai näppäimistöltä.
Sovellus antaa palautetta ja tallentaa sen paikallisesti soittotaidon kehityksen seuraamiseksi.

## Käyttöliittymä


Käyttöliittymä on graafinen ja sisältää neljä eri näkymää:
* Aloitusvalikko
* Asetusvalikko
* Tilastonäkymä
* Soittonäkymä

## Toiminnallisuudet

* Käyttäjän kirjautuminen
 * Vanha käyttäjä valitsee itsensä alasvetolaatikosta
 * Uusi käyttäjä luo itselleen käyttäjätunnuksen
* Harjoittelutapahtuma
 * Käyttäjälle annetaan järjestelmän generoimat nuotit soitettavaksi
  * Käyttäjä pääsee asetusvalikossa vaikuttamaan nuottien määrään ja sävellajiin
 * Käyttäjä soittaa annetun kappaleen
  * Vaihtoehtona joko näppäimistö tai midi-laite
* Tilastonäkymä
 * Tilostonäkymässä käyttäjä näkee oman kehityksensä viivakaaviona
 * Yksikkönä soittotarkkuudessa on käytetty soittotapahtuman keskimääräistä soittovirhettä puolisävelaskeissa mitattuna
* Asetusvalikko
 * Nuottien lukumäärä harjoitustapahtumassa
 * Sävellaji, mihin nuotit generoidaan

## Näkymiä sovelluksen tulevaisuudesta

* Sovelluksessa voisi olla audio-ominaisuus, joka soittaisi soitetut ja generoidut nuotit
* Tilastonäkymässä voisi olla eri käyttäjien välisiä vertailuja
* Tilastonäkymässä voisi olla valitsin tietylle ajanjaksolle
* Datan poistaminen onnistuu tällä hetkellä vain poistamalla tietokantatiedoston tai manipuloimalla sitä ulkoisesti, tähän voisi tehdä toiminnon sovelluksessa
 
