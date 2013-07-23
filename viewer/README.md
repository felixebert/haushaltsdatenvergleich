# Viewer

Alle HTML, JavaScript und CSS-Dateien befinden sich im Verzeichnis src/. Zum Einsatz kommen die JavaScript-Frameworks Leaflet, underscore.js und jQuery

Unit-Tests für die JavaScript-Dateien befinden sich im Verzeichnis test/ (testacular-Tests)

Im Verzeichnis scripts/ befindet sich ein einfacher node.js Server, der zum Entwickeln verwendet werden kann.

Im Verzeichnis src/data befinden sich die .json-Dateien des converters.

## Build

Für das Erstellen einer gepackten Version kommt grunt zum Einsatz.
Der Befehl

	grunt build

erstellt im Verzeichnis dist/ eine lauffähige Version des Viewers mit komprimierten und optimierten Resourcen (JavaScript / CSS / Bilder).

Um grunt ausführen zu können, muss node.js auf ihrem Rechner installiert sein und einmalig in diesem Verzeichnis der Befehl

	npm install

ausgeführt werden. 
