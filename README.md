Haushaltsdatenvergleich NRW
===========================

Das Projekt besteht aus zwei Modulen. Zum einen aus einem Konverter und zum anderen aus einem Viewer.
Der Konverter ist ein Java Programm, welches die verschiedenen Datenquellen in ein 
einheitliches JSON-Format für den Viewer aufbereitet.
Der Viewer ist eine JavaScript/HTML5 Web App, welche die Daten im Browser darstellt 
und dem Benutzer verschiedene Möglichkeiten zum Vergleich der Daten bietet.

Der Viewer befindet sich im viewer/web Verzeichnis und kann mit der Datei index.html geöffnet werden. 
Alle Daten sind bereits konvertiert und in dem Verzeichnis viewer/web/data aufbereitet.

Möchte man das Projekt verändern oder aktualisierte Daten verwenden muss das Java-Projekt 
kompiliert und verschiedene Konverter gestartet werden. 
Diese Schritte werden im Folgenden beschrieben.

Projekt kompilieren
-------------------
Im Verzeichnis "converter" 

"mvn clean package"

ausführen.

Dadurch wird das Java Projekt kompiliert und zu einer JAR Datei zusammen 
mit allen Abhängigkeiten gepackt.

Daten konvertieren
------------------

Im Projekt-Verzeichnis folgende Skripte ausführen:

"konvertiere_bilanz" 
"konvertiere_finanzdaten_gemeinden" 
"konvertiere_finanzdaten_landkreise"

Alterntaiv kann das Skript "konvertiere_alles" ausgeführt werden.

Die konvertierten Daten werden im Verzeichnis viewer\web\data erstellt.

Datenquellen
------------

Für dieses Projekt werden folgende Daten verwendet:

Finanzdaten für die Jahre 2009 und 2010
Quelle: 

Einwohnerzahlen Zensus 2012
Quelle: 

Gemeinde Flächen Stand 31.03.2013:
Quelle:

Gemeinde Bilanzdaten Stand ???
Quelle:

Gemeindeflächen Geodaten
Quelle:
Ministerium für Inneres und Kommunales des Landes Nordrhein-Westfalen
http://www.mik.nrw.de/themen-aufgaben/buergerbeteiligung-wahlen/wahlen/auf-allen-ebenen/landtagswahl/wahl-2012/wahlkreiskarten.html

Landkreisflächen Geodaten
Quelle:
Bundesamt für Kartographie und Geodäsie
http://www.geodatenzentrum.de/geodaten/gdz_rahmen.gdz_div?gdz_spr=deu&gdz_akt_zeile=5&gdz_anz_zeile=1&gdz_unt_zeile=17&gdz_user_id=0
