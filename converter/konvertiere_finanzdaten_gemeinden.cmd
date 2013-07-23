@echo off
echo "Konvertiere Jahr 2009..."
java -Xmx3G -Dpretty.json=true -cp target\converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.Converter "data\finanzrechnungsstatistik-einnahmen-2009.csv" "data\finanzrechnungsstatistik-ausgaben-2009.csv" "data\Zensus Einwohnerzahlen.xls" "data\Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "..\viewer\src\data\2009\gemeinden"
echo "Konvertiere Jahr 2010..."
java -Xmx3G -Dpretty.json=true -cp target\converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.Converter "data\finanzrechnungsstatistik-einnahmen-2010.csv" "data\finanzrechnungsstatistik-ausgaben-2010.csv" "data\Zensus Einwohnerzahlen.xls" "data\Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "..\viewer\src\data\2010\gemeinden"
echo "Konvertiere Jahr 2011..."
java -Xmx3G -Dpretty.json=true -cp target\converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.Converter "data\finanzrechnungsstatistik-einnahmen-2011.csv" "data\finanzrechnungsstatistik-ausgaben-2011.csv" "data\Zensus Einwohnerzahlen.xls" "data\Gemeinde Einwohnerzahlen Flaechen 31-03-2013.xls" "..\viewer\src\data\2011\gemeinden"
