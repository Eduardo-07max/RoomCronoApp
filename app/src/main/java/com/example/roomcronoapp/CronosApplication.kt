package com.example.roomcronoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
/*
Aqui nuestra clase CronosApplication: que hereda de Application que nos sirve para iniciar antes que cualquier
otra cosa en la app, despues con HiltAndroipApp crea un contenedor de depencias global para toda la app
en resumen este archivo es esencial ya que nos permite inyectar cosas en cualquier lugar de nuestra app

Nota: es necesario agregar esta linea en el android manifest  android:name=".CronosApplication"
 */
@HiltAndroidApp
class CronosApplication: Application() {

}