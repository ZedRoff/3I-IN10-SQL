#!/bin/bash

# === CONFIG ===
SRC_DIR="src"
DAO_DIR="src/DAO"
MAIN_FILE="Main.java"
OUT_DIR="out"
JDBC_JAR="./JDBC/mysql-connector-j-9.3.0.jar"

# === COMPILE ===
echo "Compiling Java files..."
mkdir -p "$OUT_DIR"

javac -cp "$JDBC_JAR" -d "$OUT_DIR" "$SRC_DIR"/*.java "$SRC_DIR"/DAO/*.java "$SRC_DIR"/model/*.java "$SRC_DIR"/Provider/*.java "$SRC_DIR"/service/*.java "$MAIN_FILE"

if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# === RUN ===
echo "Running Main..."
java -cp "$OUT_DIR:$JDBC_JAR" Main
