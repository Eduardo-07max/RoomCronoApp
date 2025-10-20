package com.example.roomcronoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomcronoapp.model.Cronos

/*
La clase abstracta es una superclase que no puede ser instanciada pero sus metodos si
 */
/*
El @Database le dice a room que esta sera nuestra base de datos pricipal donde con ayuda de:

entities = [Cronos::class] le indicamos al programa que nuestra base de datos tendra sus entidades
o tablas de la clase Cronos que ya definimos.

version = 1 nos ayuda a indicar que es la primer tabla o modelo de datos que hemos creado donde si
queremos agregar mas campos a mnuestra tabla dberemos cambiar a la version por 2 y posteriormente si
nuevamente agregamos mas campos o tablas agregaremos version 3

exportSchema es para saber si queremos que se nos genere un archivo Json de la base de datos pero en
este caso al ser un proyecto relativamente pequeño no lo haremos

abstract class CronosDatabase:RoomDatabase() con esta linea indicamos que vamos a heredar de la clase
RoomDataBase la cual se usa para mejorar internamente las conexiones y acceso a la base de datos y el
abstrac significa que no podemos crear objetos en esta clase
 */
@Database(entities = [Cronos::class], version = 1, exportSchema = false)
abstract class CronosDatabase :RoomDatabase(){
    /*
    ok con la linea  abstract fun cronosDao(): CronosDatabaseDao definimos una funcion abstracta
    que nos dara acceso a las operaciones CRUD que definimos en CronosDatabaseDao
     */
    abstract fun cronosDao(): CronosDatabaseDao
}