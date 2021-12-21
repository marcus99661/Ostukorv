# Ostukorv

## E-pood

Asub - "/"

Header ja Footer on alus, lisab mujalt fragmendina keskmise osa.

Hindade valuutat saab vahetada?

### Header

 - E-poe nimi
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

## Konto

Konto - "/konto"

## Admin page

Asub - "/admin"

### Audentimine

 - Keset ekraani on väike logimis koht 
 - Adminitel on eraldi DB
 - Kui kasutaja ja password on õige, siis saad JWT
 - Võib lisada brute-force vastase asja

### Toodete lisamine

 - Saab valida hinna
 - Saab valida hulga
 - Piirata palju saab üks konto/IP osta

## Otsingu funktsioon

Asub Headeris

Teeb andmebaasi kutse ja tagastab score järgi vasted (score oleneb mitu sõna on sama)