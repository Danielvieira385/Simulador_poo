package com.example.Vila
import com.example.Menu.Arma.Companion.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File


class Loja(val objeto: String) {

    val CAMINHODATA = "./src/main/resources/data"

    fun mostrarTodosObjetos(): List<Loja> {
        val listaObjetos = File("${CAMINHODATA}/armazemLoja.json").readText()
        if (listaObjetos.isEmpty()) {
            return emptyList()
        }
        return json.decodeFromString<List<Loja>>(listaObjetos)
    }

    fun comprarObjeto(objeto: String) {

    }

    fun venderObjeto(objeto: String) {

    }


    }
