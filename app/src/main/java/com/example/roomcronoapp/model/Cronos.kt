package com.example.roomcronoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
Como primer paso creamos una entidad que es lo mismo que una tabla, por lo que puedo decir que el crear
una tabla en la base de datos es el primer paso, en esta tabla es importante agregar los campos que se
guardaran en nuestra base de datos, en este caso los campos son un id, un titulo y crono que sera para
el tiempo.
 */

//Entidad = Tabla, Atributo = Campo

/*
@Entity nos sirve para convertir nuestra dataclass en una tabla o entidad que son lo mismo, dentro
de @Entity colocamos un tableName = Cronos que nos servira para darle un nombre a nuestro registro o
nuestra tabla, sino le dieramos nombre android studio tomario de nombre por defecto el nombre del dataclass
 */
@Entity(tableName = "Cronos")
data class Cronos(
    /*
    PrimaryKey nos sirve para indicar que el val id sera una clave unica que no se repetira y con autoGenerate
    el programa de forma auotmatica generara un id distinto para cada registro en la val id colocamos el
    dato de tipo long ya que no sabemos cuantos registros tendremos por lo que seleccionamos un tipo de dato
    grande por asi decirlo y lo inicializamos en 0 ya que despues la app lo remmplazara automaticamente por el
    registro pertinente(esto gracias a autoGenerate)
     */
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    /*
    ColumInfo lo agregamos para decir que este sera un campo de texto simple con el nombre title
    y despues definimos la variable de tipo string donde se guardara nuestro titulo o descripcion
     */
    @ColumnInfo(name = "title")
    val title : String,
    /*
    Lo mismo hacemos aqui solo que el dato que guardara sera de tipo long y servira para guardar el tiempo
     */
    @ColumnInfo(name = "crono")
    val crono : Long
)
