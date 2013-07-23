#!/bin/sh
echo "Konvertiere Bilanz..."
java -cp target/converter-1.0-jar-with-dependencies.jar de.ifcore.hdv.converter.BalanceConverter "data/Bilanz.txt" "../viewer/src/data/bilanz"
