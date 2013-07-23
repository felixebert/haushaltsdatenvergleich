# Converter

Möchte man aktualisierte Daten einspielen muss dieses Java-Projekt
kompiliert werden. Anschließend können verschiedene Konverter gestartet werden.
Diese Schritte werden im Folgenden beschrieben.

## Projekt kompilieren

	mvn clean package

Dadurch wird das Java Projekt kompiliert und zu einer JAR Datei zusammen
mit allen Abhängigkeiten gepackt.

## Daten konvertieren

Bitte beachten Sie die fertigen Konverter-Skripte namens konvertiere_*.sh (Unix) / konvertiere_*.cmd (Windows)

Die konvertierten Daten müssen in das Verzeichnis /viewer/src/data geschrieben werden.

Im Einzelnen verwendete Konverter-Klassen:

*	Konverter für die Haushaltsdaten der Gemeinden

	de.ifcore.hdv.converter.Converter <Einnahmen-Datei> <Ausgaben-Datei> <Einwohnerzahl-Datei> <Flaechen-Datei> <Ausgabe-Verzeichnis>

*	Konverter für die Haushaltsdaten der Landkreise

	de.ifcore.hdv.converter.CountyConverter <Einnahmen-Datei> <Ausgaben-Datei> <Einwohnerzahl-Datei> <Flaechen-Datei> <Ausgabe-Verzeichnis>

*	Konverter für die Bilanzdaten

	de.ifcore.hdv.converter.BalanceConverter <Bilanzdaten-Datei> <Ausgabe-Verzeichnis>


