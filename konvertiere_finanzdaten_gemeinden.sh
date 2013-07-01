#!/bin/sh
echo "Konvertiere Jahr 2009..."
java -cp converter/target/converter-1.0-SNAPSHOT-jar-with-dependencies.jar de.ifcore.hdv.converter.Converter "converter/data/finanzrechnungsstatistik-einnahmen-2009.csv" "converter/data/finanzrechnungsstatistik-ausgaben-2009.csv" "converter/data/Zensus Einwohnerzahlen.xls" "converter/data/Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "viewer/web/data/2009/gemeinden"
echo "Konvertiere Jahr 2010..."
java -cp converter/target/converter-1.0-SNAPSHOT-jar-with-dependencies.jar de.ifcore.hdv.converter.Converter "converter/data/finanzrechnungsstatistik-einnahmen-2010.csv" "converter/data/finanzrechnungsstatistik-ausgaben-2010.csv" "converter/data/Zensus Einwohnerzahlen.xls" "converter/data/Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "viewer/web/data/2010/gemeinden"
echo "Konvertiere Jahr 2011..."
java -cp converter/target/converter-1.0-SNAPSHOT-jar-with-dependencies.jar de.ifcore.hdv.converter.Converter "converter/data/finanzrechnungsstatistik-einnahmen-2011.csv" "converter/data/finanzrechnungsstatistik-ausgaben-2011.csv" "converter/data/Zensus Einwohnerzahlen.xls" "converter/data/Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "viewer/web/data/2011/gemeinden"
