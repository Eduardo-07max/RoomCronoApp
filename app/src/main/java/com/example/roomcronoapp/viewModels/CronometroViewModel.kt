package com.example.roomcronoapp.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcronoapp.repository.CronosRepository
import com.example.roomcronoapp.state.CronoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
/*
esta clase CronometroViewModel hereda las proiedades de vielModel y le inyectamos la clase CronosRepository
para tener acceso a sus metdodos y variables.
 */
@HiltViewModel
class CronometroViewModel @Inject constructor(private val repository: CronosRepository) : ViewModel() {

    /*
    Esta variable state nos permite o nos da acceso a las variables que se encuentran en la dataClass
    y utilizamos el privade set para que no pueden ser modificadas desde fuera de esta clase
     */
    var state by mutableStateOf(CronoState())
        private set
/*
La variable cronoJob es de tipo mutable ideal para que la interfaz note los cambios y usamos esta variable
de tipo Job ya que la podemos Iniciar, Pausar y detener
 */
    var cronoJob by mutableStateOf<Job?>(null)
        private set
    /*
    nuestra variable tiempo de tipo mutable que como ya mencionamos es para que nos permita guardar en el tiempo
    en milisegundos
     */
    var tiempo by mutableStateOf(0L)
        private set

/*
La funcion getCronoById tiene un parametro id del tipo Long la cual nos ayudara a obtener un unico registro
de la base de datos local mediante este id, dentro de la funcion tenemos una corrutina  donde la trea a ejecutar
lo hara fuera del hilo principal gracias a Dispachers.IO dentro de la corrutina accedemos a la funcion getCronosById
que se encuentra en el repositorio esta funcion en el repositorio devuelve un flujo de datos del tipo flow y collect
sirve para recibir este tipo de datos

posteriormente item toma el valor del objeto cronos donde dada la condicion de que si el objeto cronos no
esta vacio pues la variable tiempo guardara el tiempo que tenga el registro item.crono y posteriormente el
titulo sera guardado en base a lo que tenga ese registro que sera item.title

por ultimo si no existe ese registro con ese id se muestra el mensaje de error "El objeto crono es nulo"
 */
    fun getCronoById(id: Long){
viewModelScope.launch(Dispatchers.IO) {
    repository.getCronoById(id).collect{ item ->
        if (item!= null){
            tiempo = item.crono
            state = state.copy(title = item.title)
        }else{
            Log.d("Error","El objeto crono es nulo")
        }

    }
}
    }
/*
La funcion crearForNew nos permitira limpiar los campos de tiempo
y las variables que estan dentro de CronoState como title de suma importancia para que no aparesca un titulo
anterior y cambiar el valor de la val showTextField a false para no mostrar el textefiled y cancelar el
cronometro o cualquier cronometro que este activo
 */
    fun clearForNew() {
        cronoJob?.cancel()
        tiempo = 0L
        state = CronoState()
    }

/*
Esta funcion on value sirve para guardar lo que el usuario escribe en el campo de texto
 */
    fun onValue(value:String){
        state = state.copy(title = value)
    }
/*
Esta funcion iniciar nos ayuda a iniciar el cronometro cambiando el estado de la variable de false a true
 */
    fun iniciar(){
        state = state.copy(
            cronometroActivo = true
        )
    }
/*
Esta funcion pausar nos ayuda a pausar el cronometro cambiando el estado de la variable cronometroActivo
de true a false y a cambiar la variableSaveButton de false a true para mostrar el boton de guardar
 */
    fun pausar(){
        state = state.copy(
            cronometroActivo = false,
            showSaveButton = true
        )
    }
/*
Nuestra funcion detener nos ayuda a cancelar primeramente el proceso de conteo del cronometro con cronoJob.cancel
y reinicio la variable de tiempo a 0 y detiene el cronometro cambiando la variable cronometro activo a false
tambien con showSaveButton quita el boton de guardar al ponerlo en false y tambien quita el textfiel poniendo
la variable showTextFiel en false
 */
    fun detener(){
        cronoJob?.cancel()
        tiempo = 0
        state = state.copy(
            cronometroActivo = false,
            showSaveButton = false,
            showTextField = false
        )
    }
/*
La funcion showTextField nos ayuda a cambiar el valor de la variable showTextField a true para mostar
nuestro textfiel
 */
    fun showTextField(){
        state = state.copy(
            showTextField = true
        )
    }
/*
la funcion cronos nos permitira iniciar el cronometro donde mediante el bloque if comprobamos que si la
variable cronometroActivo es true hacemos lo siguiente:
primero cancelamos cualquier cronometro activo que este o estuviera corriendo.
posteriormente la varibale cronoJob le asignamos una corrutina y dentro un bloque while infinito gracias al true
que este bucle lo que hara es que cada segundo que pase le sumara 1000 milisegundos a tiempo osea le sumara un
segundo y en caso contrario que cronometroActivo sea false detiene cronoJob osea de sumar mas tiempo
esto sirve ya que si pausamos el cronometro se qudara con el tiempo que acumulo y si lo volvemos a iniciar
activara de nuevo cronoJob pero le sumara el tiempo a lo que ya tenemos osea no lo reinicira el tiempo.
 */
    fun cronos(){
        if (state.cronometroActivo){
            cronoJob?.cancel()
            cronoJob = viewModelScope.launch {
                while (true){
                    delay(1000)
                    tiempo += 1000
                }
            }
        }else{
            cronoJob?.cancel()
        }
    }

}