import requests
import mysql.connector
from flask import Flask, jsonify

app = Flask(__name__)

@app.route("/saludo", methods=["GET"])
def saludo():
    return jsonify({
        "mensaje": "Hola desde la API Flask"
    })

@app.route("/archivo-error", methods=["GET"])
def archivo_error():
    try:
        with open("archivo_que_no_existe.txt", "r", encoding="utf-8") as f:
            contenido = f.read()
        return jsonify({"mensaje": contenido})
    except FileNotFoundError:
        return jsonify({
            "error": True,
            "tipo": "FILE_ERROR",
            "mensaje": "No se pudo abrir el archivo solicitado"
        }), 500

@app.route("/db-error", methods=["GET"])
def db_error():
    try:
        raise Exception("Error simulado de base de datos")
    except Exception:
        return jsonify({
            "error": True,
            "tipo": "DB_ERROR",
            "mensaje": "No se pudo acceder a la base de datos"
        }), 500

@app.route("/db-ok", methods=["GET"])
def db_ok():
    try:
        conexion = mysql.connector.connect(
            host="host.docker.internal",
            port=3506,
            user="root",
            password="eneas2805",
            database="basicosd"
        )

        cursor = conexion.cursor()
        cursor.execute("SELECT username, email FROM user LIMIT 1")
        resultado = cursor.fetchone()

        cursor.close()
        conexion.close()

        if resultado:
            return jsonify({
                "error": False,
                "mensaje": "Consulta a base de datos realizada correctamente",
                "username": resultado[0],
                "email": resultado[1]
            })

        return jsonify({
            "error": False,
            "mensaje": "La base de datos respondió, pero no hay usuarios"
        })
    except mysql.connector.Error:
        return jsonify({
            "error": True,
            "tipo": "DB_ERROR",
            "mensaje": "No se pudo acceder correctamente a la base de datos"
        }), 500

@app.route("/pokemon/<nombre>", methods=["GET"])
def pokemon(nombre):
    try:
        url = f"https://pokeapi.co/api/v2/pokemon/{nombre}"
        respuesta = requests.get(url, timeout=5)

        if respuesta.status_code == 404:
            return jsonify({
                "error": True,
                "tipo": "POKEMON_NOT_FOUND",
                "mensaje": "No se encontró el Pokémon solicitado"
            }), 404

        respuesta.raise_for_status()
        datos = respuesta.json()

        return jsonify({
            "error": False,
            "nombre": datos["name"],
            "id": datos["id"],
            "altura": datos["height"],
            "peso": datos["weight"]
        })
    except requests.RequestException:
        return jsonify({
            "error": True,
            "tipo": "POKEMON_API_ERROR",
            "mensaje": "No se pudo consultar la API de Pokémon"
        }), 500

@app.route("/pokemon-error", methods=["GET"])
def pokemon_error():
    try:
        url = "https://pokeapi.co/api/v2/pokemon/noexiste123"
        respuesta = requests.get(url, timeout=5)

        if respuesta.status_code == 404:
            return jsonify({
                "error": True,
                "tipo": "POKEMON_NOT_FOUND",
                "mensaje": "No se encontró el Pokémon solicitado"
            }), 404

        respuesta.raise_for_status()
        datos = respuesta.json()

        return jsonify({
            "error": False,
            "nombre": datos["name"],
            "id": datos["id"]
        })
    except requests.RequestException:
        return jsonify({
            "error": True,
            "tipo": "POKEMON_API_ERROR",
            "mensaje": "No se pudo consultar la API de Pokémon"
        }), 500


@app.route("/db-error-real", methods=["GET"])
def db_error_real():
    try:
        conexion = mysql.connector.connect(
            host="host.docker.internal",
            port=3506,
            user="root",
            password="eneas2805",
            database="basicosd"
        )

        cursor = conexion.cursor()
        cursor.execute("SELECT username, email FROM tabla_que_no_existe")
        resultado = cursor.fetchone()

        cursor.close()
        conexion.close()

        return jsonify({
            "error": False,
            "resultado": resultado
        })
    except mysql.connector.Error:
        return jsonify({
            "error": True,
            "tipo": "DB_ERROR_REAL",
            "mensaje": "Se produjo un error real al consultar la base de datos"
        }), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)