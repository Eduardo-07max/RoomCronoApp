package com.example.roomcronoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomcronoapp.model.Cronos
import kotlinx.coroutines.flow.Flow

/*
Esta interface llamada CronosDatabaseDao con el @Dao le decimos al programa que aqui tendremos las funciones
que interacturan directamente con la base de datos
 */

// Interface -> Repositorio -> ViewModel -> View
@Dao // Data Access Observer
interface CronosDatabaseDao {

    //CRUD
    /*
    Esta funcion getCronos nos mostrara toda la lista de registros que exista en la base de datos
    esto gracias a SELECT * FROM cronos donde le estamos diciendo que seleccione y muestre todos los
    registros de la tabla cronos que definimos en la clase Cronos esta en minuscula es cierto pero
    no importa si es mayuscula o minuscula el programa entiende que te refieres a la tabla Cronos pero
    por las dudas es mejor escribirlo en minuscula pero solo aqui en el dao, por ultimo la funcion get
    cronos nos retornara una lista de cronos de tipo flow donde flow nos servira para recargar nuestra
    pantalla de forma automatica o en tiempo real deacuerdo a los cambios en los registros y es de tipo list
    ya que son varios registros o podrian ser varios registros.
     */
    @Query("SELECT * FROM cronos")
    fun getCronos(): Flow<List<Cronos>>
/*
Esta funcion nos ayudara a a medinate un SELECT * FROM cronos selecionar y mostrar los registros pero
where en base a un id en especifico donde la funcion getCronosById nos retornara un solo registro ya que
le pedimos al invocarlo un parametro llamado id que nos ayudara a buscar el regsitro con ese id en especifico
y nos retornara ese registro de tipo flow que cambiara si hay alguna modiicacion en la base de datos y que nos
ayuda a ejecutarlo dentro de una corrutina al ser del tipo flow
 */
    @Query("SELECT * FROM cronos WHERE id = :id")
    fun getCronosById(id: Long): Flow <Cronos>
/*
Esta funcion con @Insert le dice a android studio que aqui vamos a insertar un nuevo registro y en caso
de que haya un conflito donde se repita en este caso un id simplemnete lo remplace con el nuevo registro
tambien notamos que es una funcion suspendida por lo que sera necesario invocarla dentro de una corrutina
esto con el fin de que se ejecute en un hilo diferente al principal y evitar errores como que se cierre la app
 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (crono: Cronos)
/*
la funcion update con el @Update le dice a adroid estudio que editaremos un registro ya existente y le
agregamos OnConflictStartegy.replace en caso de un error o colicion de regsistros igual que en el insert,
el paramtero crono nos indica que necesitaremos un parametro de tipo Cronos o sea que ncesitaremos un registro
 */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update (crono: Cronos)
/*
con el @Delete le decimos al programa que eliminaremos un registro donde gracias al parametro crono
le decimos que queremos eliminar un registro de tipo Cronos
 */
    @Delete
    suspend fun delete(crono: Cronos)

/*
NOTA: aqui en el dao es una interface donde definimos que queremos hacer osea insertar,mostrar, eliminar
y actualizar pero no le decimos como hacerlo
 */
}