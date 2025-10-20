package com.example.roomcronoapp.state
/*
Aqui en nuestra data class definiremos algunas val la primera cronomteroActivo de tipo Boolean la tendremos
como false y nos servira para activar el cronometro.

showSaveButton tambien de tipo Boolean inicialasada en false nos permitira guardar

showTextField de tipo Boolean inicializado en false nos servira para mostrar el texfiel

title que nos servira para guardar el titulo en el textfiel.
 */
data class CronoState (
    val cronometroActivo : Boolean = false,
    val showSaveButton: Boolean = false,
    val showTextField: Boolean = false,
    val title : String = ""
)