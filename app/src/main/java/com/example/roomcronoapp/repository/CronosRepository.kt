package com.example.roomcronoapp.repository

import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.room.CronosDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*
Primeramente nuestra clase CronosRepository maneja la comunicacion entre las funeste de comunicacion
que en este caso es la base datos local pero puede ser tambien una api y sirve para que el viewModel
no tenga que hablar directamente con la base de datos

a nuestra clase con ayuda de @Inject le indica a Hilt que debe de inyectar automaticamente el objeto
que se le pasa al constructor que en este caso es un Objeto del tipo CronosDatbaseDao gracias a una varibale
privadata
 */
class CronosRepository @Inject constructor(private val cronosDatabaseDao: CronosDatabaseDao){

    /*
    Ahora creamos una funcion suspend llamada addCrono y es de tipo suspend ya que al conectar con la
    base de datos para crear un registro es necesario invocarla dentro de una corrutina y seleccionar
    un hilo diferente al principal para mejorar el rendimiento
     */
    suspend fun addCrono(crono: Cronos) = cronosDatabaseDao.insert(crono)
    /*
    Ahora creamos otra funcion suspend para de igual forma ejecutarla en un hilo diferente al principal
    en este caso la funcion se llama updateCrono que nos servira para actualizar un registro, es importante
    notar que la funcion necesita un parametro de tipo Crono que aqui es donde definimos los campos que vamos
    a guardar como tiempo id y titulo donde posteriormente gracias a la inyecccion de dependencias con la ayuda
    de la val cronosDatabaseDao accedemos a las opereciones definidadas para el acceso a la base de datos o
    CronosDatabaseDao.update
     */
    suspend fun updateCrono(crono: Cronos) = cronosDatabaseDao.update(crono)
    /*
    Ahora creamos otra funcion suspend para ejecutarla en un hilo diferente al principal donde le pasamos un
    parametro del tipo Cronos y la igualamos con cronosDabaseDao.Delete para acceder a la funcion eliminar del
    CronosDatabaseDao
     */
    suspend fun deleteCrono(crono: Cronos) = cronosDatabaseDao.delete(crono)
    /*
    Ahora con la funcion getAllCronos nos retornara un Flow<List<Cronos>> lo cual Flow es para que los cambios
    se vean reflejados en tiempo real y list ya que sera una lista de datos del tipo Cronos, donde igualamos a
    la funcion cronosDatabseDao.getCronos para acceder a la funcion de mostrar definida en el CronosDatabseDao
    y flowOn(Dispachers.IO) le indicamos al programa que se ejecutara en el hilo IO y con .conflate evita emitir
    datos viejos si llegan nuevos muy rapido osea optimiza el rendimiento
     */
    fun getAllCronos(): Flow<List<Cronos>> = cronosDatabaseDao.getCronos().flowOn(Dispatchers.IO).conflate()
    /*
    Ahora con la funcion getCronoById con el parametro id: de tipo Long obtendremos un solo registro en base a
    ese id que nos retornara un Flow<Cronos> el flow como diismos es para que sea vean reflejados los cambios
    de manera rapida esta funciona la igualamos a la que esta en CronosDatbaseDao que se ejecutara en el hilo principal
     */
    fun getCronoById(id: Long): Flow<Cronos> = cronosDatabaseDao.getCronosById(id)

}