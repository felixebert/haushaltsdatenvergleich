Haushaltsdatenvergleich NRW
===========================

Das Projekt besteht aus zwei Modulen - einem Konverter und einem Viewer.

Der Konverter ist ein Java Programm, welches die verschiedenen Datenquellen in ein 
einheitliches JSON-Format für den Viewer aufbereitet.

Der Viewer ist eine JavaScript/HTML5 Web App, welche die Daten im Browser darstellt 
und dem Benutzer verschiedene Möglichkeiten zum Vergleich der Daten bietet.

Weitere Informationen zu den Modulen erhalten sie in den README-Dateien der Modul-Verzeichnisse.

Datenquellen
-----------

Alle verwendeten Rohdaten befinden sich im Verzeichnis converter/data
Die aus den Rohdaten erzeugten JSON-Dateien befinden sich im Verzeichnis viewer/src/data

Folgende Daten wurden in diesem Projekt verwendet:

* kommunale Finanzdaten: [Landesdatenbank NRW](https://www.landesdatenbank.nrw.de/) Tabellencodes: 71147-05i und 71147-15i

* Einwohnerzahlen auf Basis des Zensus 2011: [Gemeindeverzeichnis-Informationssystem (GV-ISys)](https://www.destatis.de/DE/ZahlenFakten/LaenderRegionen/Regionales/Gemeindeverzeichnis/Administrativ/AdministrativeUebersicht.html) des Statistischen Bundesamts

* Fläche in km² je Gemeinde - Stand 31.03.2013: [Gemeindeverzeichnis-Informationssystem (GV-ISys)](https://www.destatis.de/DE/ZahlenFakten/LaenderRegionen/Regionales/Gemeindeverzeichnis/Gemeindeverzeichnis.html) des Statistischen Bundesamts

* Bilanzdaten von 2009: IT.NRW - bezogen von [piratenfraktion-nrw.de](http://www.piratenfraktion-nrw.de/2012/12/transparenzerfolg-finanzdaten-der-kommunen-veroffentlicht/)

* Gemeindeflächen Geodaten: [Ministerium für Inneres und Kommunales des Landes Nordrhein-Westfalen](http://www.mik.nrw.de/themen-aufgaben/buergerbeteiligung-wahlen/wahlen/auf-allen-ebenen/landtagswahl/wahl-2012/wahlkreiskarten.html)

* Landkreisflächen Geodaten [Bundesamt für Kartographie und Geodäsie](http://www.geodatenzentrum.de/geodaten/gdz_rahmen.gdz_div?gdz_spr=deu&gdz_akt_zeile=5&gdz_anz_zeile=1&gdz_unt_zeile=17&gdz_user_id=0)
