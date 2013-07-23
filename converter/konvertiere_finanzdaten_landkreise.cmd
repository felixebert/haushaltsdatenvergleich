@echo off
echo "Konvertiere Jahr 2009..."
java -Dpretty.json=true -cp target\converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.CountyConverter "data\finanzrechnungsstatistik-einnahmen-2009.csv" "data\finanzrechnungsstatistik-ausgaben-2009.csv" "data\Zensus Einwohnerzahlen.xls" "data\Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "..\viewer\src\data\2009\landkreise"
echo "Konvertiere Jahr 2010..."
java -Dpretty.json=true -cp target\converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.CountyConverter "data\finanzrechnungsstatistik-einnahmen-2010.csv" "data\finanzrechnungsstatistik-ausgaben-2010.csv" "data\Zensus Einwohnerzahlen.xls" "data\Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "..\viewer\src\data\2010\landkreise"
echo "Konvertiere Jahr 2011..."
java -Dpretty.json=true -cp target\converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.CountyConverter "data\finanzrechnungsstatistik-einnahmen-2011.csv" "data\finanzrechnungsstatistik-ausgaben-2011.csv" "data\Zensus Einwohnerzahlen.xls" "data\Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "..\viewer\src\data\2011\landkreise"
