package com.example.roomcronoapp.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.example.roomcronoapp.room.CronosDatabase
import com.example.roomcronoapp.room.CronosDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
Este arichivo nos permite tener acceso a las operaciones CRUD mediante la funcion providesCronosDao
que conecta con el archivo CronosDatabaseDao.

con la funcion providesoCronosDataBase creamos una sola vez la base de datos
 */

//@Module le dice a HILT como crear objetos importantes que vas a necesitar(Como la base de datos)
/*
@Installin(SingletonComponent) es para decirle al programa que las cosas que crearemos aqui van a durar
toda la vida de la app.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /*
    Nuesta funcion providesCronosDao tiene un parametro llamado cronoDatabase del tipo CronosDatabase y nos
    retornara un objeto de tipo CronosDatabaseDao donde con ayuda de return cronoDatbase.cronosDao accedemos
    a la funcion que contiene los metdodos CRUD para interacurar con la base de datos

    Con @Provides le decimos a HILT que esta funcion le explicara como obtener un objeto del tipo
    CronosDatabaseDao

    Con @Singleton le decimos que solo queremos una unica copia de este objeto para toda la app
     */
    @Singleton
    @Provides
    fun providesCronosDao(cronoDatabase: CronosDatabase): CronosDatabaseDao{
        return cronoDatabase.cronosDao()
    }

    /*
    Esta funcion providesCronosDatabase  tiene un @ApplicationContext que le dice a Hilt que use el
    contexto de la app necesario para construir la base de datos.

    La funcion tambien nos retornara un objeto de tipo CronosDatabase importante ya que ahi viene especicado
    la entidad o tabla que usaremos asi como su version y su conexion con el dao importante para la creacion
    de la base de datos

    Posteriormente nos retornara la base de datos ya creada con ayuda de Room.databaseBuilder() que necesita
    que le pasemos un contexto que en este caso lo toma del parametro, con CronosDababase es la clase que define
    la base de datos y incluso le damos un nombre al archivo que tendra donde se guardara la base de datos en el
    telefono
     */
    @Singleton
    @Provides
    fun providesCronosDatabase(@ApplicationContext context: Context): CronosDatabase{
        return Room.databaseBuilder(
            context,
            CronosDatabase::class.java,"cronos_db"
        ).fallbackToDestructiveMigration()// esta linea en caso de no crear una migracion borra y vuleve a crear la base de datos para evitar errorres
            .build()//este punto build crea la base de datos
    }
}