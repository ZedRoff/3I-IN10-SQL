#!/bin/bash

# === CONFIG ===
SRC_DIR="src"
MAIN_FILE="Main.java"
OUT_DIR="out"
JDBC_JAR="./JDBC/mysql-connector-j-9.3.0.jar"

# === COMPILE ===
echo "Compiling Java files..."
mkdir -p "$OUT_DIR"

javac -cp "$JDBC_JAR" -d "$OUT_DIR" "$SRC_DIR"/*.java "$MAIN_FILE"

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# === RUN ===
echo "Running Main..."
java -cp "$OUT_DIR:$JDBC_JAR" Main
