# Testausdokumentti

## Yksikkötestaus

Sovelluksen yksikkötesteihin on käytetty JUnitia. JUnitilla on testattu, että tiedon pysyväistallennus toimii niinkuin kuuluu, sekä domain-paketissa
olevalle sovelluslogiikalle on kirjoitettu testit, jotka varmistavat sovelluksen toimivan loogisesti oikein. Testausta varten on luotu luokat
FakeUserDao sekä FakeSessionDao, jotka simuloivat tietokantaa keskusmuistissa. Näillä luokilla on mahdollista luoda testejä varten DataService-olion, 
jonka konstruktori ottaa parametreikseen kyseisen luokan oliot. 

## Testauskattavuus

Sovelluksen testauksella on 88 % rivikattavuus, sekä 73 % haaraumakattavuus. Testausta kehittäessä voisi kiinnittää erityistä huomiota luokkien
TrainerSession sekä Score testaukseen. 

## Järjestelmätestaus

Järjestelmä on testattu manuaalisesti siten, että sovellusta on käytetty käyttäjän roolissa ja käyty käyttöliittymä läpi. 
Käyttäjän syötettä ottaviin paikkoihin on annettu erilaisia syötteitä ja varmistuttu, ettei syötteet aiheuta käsittelemättömiä poikkeustilanteita
ajon aikana. 
