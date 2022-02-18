# Ostukorv

## E-pood

Asub - "/"

Header ja Footer on alus, lisab mujalt fragmendina keskmise osa.

Hindade valuutat saab vahetada?

### Header

- ~~E-poe nimi~~
- Keele vahetus funktsioon (Hetkel inglise ja eesti, jätab cookina meelde)
- Otsingu lahter
- Konto link
- Ostukorvi ikoon (saab lisada mitu toodet on ostukorvis)

- Toodete kategooriad, max 8 ()

### Footer

- kooli nimi
- address
- telefon (+372 5123456789)
- About us link

## ERROR

Asub - "/error"

## Ostukorv

Asub - "/cart"

## Login

- ~~error - vale pass, ei tohi lahter tühi olla~~
- alert - (Peate sisselogima, et seda funktsiooni kasutada)
- redirect=/supportchat

## Konto

Konto - "/konto"

- Kontode tellimused jäetakse meelde (hiljem saab lisada review)

## Toote leht

- Asub - "/toode/"
- pluss ja miinus nupp ei tööta
- Toode mis on otsas "Lisa korvi" nupp ei tööta

## Kategooria leht

- Kõik tooted ühel lehel - "/kategooria"
- Kui selles kategoorias ei ole ühtegi toodet siis näitab silti "Ühtegi toodet pole selles kategoorias"

- Asub - "/kategooria/it", "/kategooria/vaba-aeg"
- Teha korda page numbrid
- Toode mis on otsas "Lisa korvi" nupp ei tööta

## Otsingu funktsioon

Asub Headeris

Teeb andmebaasi kutse ja tagastab score järgi vasted (score oleneb mitu sõna on sama)



## Sooduskupongid

- protsent
- ajavahemik kuna töötab
- jälgib palju on kasutatud
- saab kasutada piiratud kordi


## Admin page

Asub - "/admin"
Teha sammuti fragmendiga

## Header

- Ligipääs igalepoole
- Logout nupp

### Audentimine

- ~~Keset ekraani on väike logimis koht~~
- ~~Adminitel on eraldi DB~~
- ~~Kui kasutaja ja password on õige, siis saad JWT~~
- Võib lisada brute-force vastase asja

## Toodete leht
- ~~Nupp millega saab lisada tooteid~~

- ~~Pikk nimekiri kus on kõik tooted (võetud toodete andmebaasist)~~
- ~~igal real on eraldi toode~~
- ~~iga toote juures on nimi, hind(euro), kogus, muutmise nupp, kustutamise nupp~~
    - alaosa kus on saab toote juurde lisada ajastatud allahindlus
- Kui tootekoodil on /n järel siis ei saa toodet muuta ega eemaldada
- Kui toode on otsas siis on hall

## Toodete muutmis leht

- Kontrollib, et pilt ei ületa limiiti
- Kontrollib, et price ei sisaldaks tähti
- Kontrollib, et pildi nimi ei ole "default"
- Kontrollib, et kõik lahtrid on täidetud
- ~~saab muuta pilti (upload), nime, hinda, descriptioni~~
- Eraldi koht kus saab vaadata/lisada/eemaldada allahindlusi (algusaeg, lõpuaeg, protsent)

## Tellimused

- Võtab tellimuste andmebaasist
- Suurem kast
- Kirjas toote nimi, kogus, tüki hind ja koguhind, notes,
- Võimalik tellimust tühistada (koos confirm nuppuga (Olete kindel, et tahate tühistada tellimust?))

## Support chat
- Iga aktiivne support request on jaotatud kastideks
- Valge värviga on aktiivsed chatid, millele keegi hetkel ei vasta
- Rohelise värviga on aktiivsed chatid, millele juba keegi teine admin vastab
- Võimalik vaadata arhiivi (kes vastas, mis kell, mis kuupäev, )
- Vastuse pakkumised ("Kuidas ma saan teid aidata?")


flash deals

ostu kombo
personalized deals (Tooted mida on varem vaadatud)
