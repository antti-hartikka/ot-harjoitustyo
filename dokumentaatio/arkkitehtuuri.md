# Arkkitehtuurikuvaus

## Ohjelmiston rakenne

Ohjelmisto koostuu kolmesta eri pakkauksesta: 
* trainerapp.ui – sisältää graafisen käyttöliittymän
* trainerapp.domain – sisältää sovelluksen datankäsittelylogiikan
* trainerapp.dao – sisältää yhteyden tiedon pysyväistalletukseen

## Käyttöliittymä

Graafinen käyttöliittymä pitää sisällään useita näkymiä joita hallinoidaan UserInterface-luokasta. 
Aluksi käyttäjä kohtaa valikon, jossa häntä pyydetään joko valitsemaan aiemmin luotu käyttäjä tai vaihtoehtoisesti luomaan uusi.
Käyttäjän valinnan jälkeen avautuu ohjelman päävalikko, jossa on neljä erilaista polkuvalintaa: training, statistics, settings tai exit.
Näkymien välinen vaihto on toteutettu siten, että training vaihtaa koko Scene-olion Training-luokalta haettuun olioon, kun taas settings
ja statistics vaihtavat BorderPane-olion sisältöä uuteen. 

Training-näkymään mentäessä sovellus aloittaa kaikkien saatavilla olevien midi-sisääntulojen kuuntelun käyttäen luokkia MidiService 
ja MidiInputReceiver, jonka lisäksi käyttäjä voi myös halutessaan käyttää omaa näppäimistöään nuottien soittamiseen. Sovellus pyytää Trainer-luokkaa 
aloittamaan harjoittelun, ja Trainer-luokka kutsuu ScoreDrawer-luokkaa piirtämään nuotit Canvas-luokkaa käyttäen. Note-luokka edustaa yksittäisen
nuotin tietoja. Nuotiston vieritys on toteutettu ScrollPane-luokan avulla, jokainen eteneminen vierittää näkymää hieman eteenpäin.

## Tiedonkäsittelylogiikka

Sovelluksen logiikka on pakkauksen domain vastuulla. Pakkauksen luokka DataService vastaa tiedon pysyväistalletuksesta, Score-luokka vastaa 
nuottien generoinneista, Session yksittäisen harjoitustapahtuman tiedoista sekä TrainerSession harjoitustilan syötteen käsittelystä ja harjoituksen päätyttyä sen tallentamisesta.

## Tiedon tallentaminen

Käyttäjätiedot sekä harjoitustapahtumien tiedot tallennetaan pakkauksen trainerapp.dao luokilla DBSessionDao sekä DBUserDao, jotka toteuttavat rajapinnat
SessionDao sekä UserDao. Tiedot tallennetaan SQLite-tietokantatiedostoon, jossa on taulut Users ja Sessions. 

## Kaavioita

### Luokkakaavio
![](/dokumentaatio/kuvat/arkkitehtuuri.png)

### Sekvenssikaavio, kun käyttäjä lopettaa harjoitustapahtuman ennen nuottien loppumista
![](/dokumentaatio/kuvat/abort_training.png)

