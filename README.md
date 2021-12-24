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
 - Ostukorvi ikoon

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

 - error - vale pass, ei tohi lahter tühi olla
 - alert - (Peate sisselogima, et seda funktsiooni kasutada)
 - redirect=/supportchat

## Konto

Konto - "/konto"

 - Kontode tellimused jäetakse meelde (hiljem saab lisada review)

## Otsingu funktsioon

Asub Headeris

Teeb andmebaasi kutse ja tagastab score järgi vasted (score oleneb mitu sõna on sama)




## Admin page

Asub - "/admin"
Lingid igasse kohta


### Audentimine

 - ~~Keset ekraani on väike logimis koht~~
 - ~~Adminitel on eraldi DB~~
 - ~~Kui kasutaja ja password on õige, siis saad JWT~~
 - Võib lisada brute-force vastase asja

## Toodete leht
 - Nupp millega saab lisada tooteid

 - Pikk nimekiri kus on kõik tooted (võetud toodete andmebaasist)
 - igal real on eraldi toode
 - iga toote juures on pilt, nimi, hind(euro), kogus, muutmise nupp, kustutamise nupp
   - alaosa kus on saab toote juurde lisada ajastatud allahindlus

## Toodete muutmis leht

 - saab muuta pilti (upload), nime, hinda, descriptioni
 - Eraldi koht kus saab vaadata/lisada/eemaldada allahindlusi (algusaeg, lõpuaeg, protsent)

## Tellimused

 - Võtab tellimuste andmebaasist
 - Suurem kast
 - Kirjas toote nimi, kogus, tüki hind ja koguhind, notes, 
 - Võimalik tellimust tühistada (koos confirm nuppuga (Olete kindel, et tahate tühistada tellimust?))

## Support chat
 - Väga suured kastid
 - Näitab hetkel kõiki hetkel aktiivseid 
 - Valge värviga on aktiivsed chatid, millele keegi hetkel ei vasta
 - Rohelise värviga on aktiivsed chatid, millele juba keegi teine admin vastab
 - Võimalik vaadata arhiivi (kes vastas, mis kell, mis kuupäev, )
 - Vastuse pakkumised ("Kuidas ma saan teid aidata?")





flash deals

ostu kombo
personalized deals (Tooted mida on varem vaadatud)
