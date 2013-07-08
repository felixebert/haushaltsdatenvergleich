#!/bin/sh
echo "Konvertiere Bilanz..."
java -cp ../converter/target/converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.BalanceConverter "../converter/data/Bilanz.txt" "../viewer/src/data/bilanz"
