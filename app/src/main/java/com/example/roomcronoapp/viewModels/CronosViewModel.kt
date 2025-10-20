package com.example.roomcronoapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.repository.CronosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/* Con HiltViewModel le decimos al sistema de inyeccion de dependencias Hilt que esta clase es un ViewModel
y que puede recibir cosas inyectadas automaticamente y con :ViewModel() heredamos las propiedades de la
clase ViewModel

Posteriormente con @Inject contructor le inyectamos la clase CronosRepository que contiene los metodos
para acceder a la base de datos y actua como intermediario entre la base de datos y el viewModel
 */
@HiltViewModel
class CronosViewModel @Inject constructor(private val repository: CronosRepository): ViewModel() {
    /*
    Primero con la val privada llamada _cronoList guardaremos una lista mutable de tipo flow para que los
    cambios se vean reflejados en la interfaz del tipo Cronos y la definimos como una lista vacia con
    emptyList.

    Posteriormente con la val cronosList le agregamos el valor de la variable privada _cronosList esto para
    poder acceder al valor de esta variable sin modificar nada desde la vista ya que cronosList la usaremos
    desde la vista y con ayuda de .asStateFlow para que Compose observe los cambios en tiempo real
     */
    private val _cronosList = MutableStateFlow<List<Cronos>>(emptyList())
    val cronosList = _cronosList.asStateFlow()
/*
Ahora necesitamos usar el bloque Init ya que este nos ayudara a que cada que se crea el ViewModel como
primer tarea despues de eso debe de cargar los datos y con ayuda de viewModelScope.launch(Dispachers.IO)
ejecutamos esta tarea en un hilo diferente al principal para evitar que la app llegue a trabarse.

Posteriormente con repository.getAllCronos.collect escucha los datos que vienen del repositorio con collect
significa escucha los datos cada vez que cambian y despues con el lambda item tomara el valor de cada resgistro
para mostrarlo auque segun la condicion en nuestro bloque if esto pasara solo si hay elementos ya que si estan
vacios solo le pondra el valor de vacio a la val _cronosAllCronos pero si si es que hay _cronosList si tomara
el valor de item o de cada elemento que haya en la lista o base de datos
 */
    init {
viewModelScope.launch (Dispatchers.IO){
    repository.getAllCronos().collect{item->
        if(item.isNullOrEmpty()){
            _cronosList.value = emptyList()

        }else{
            _cronosList.value = item
        }

    }
}
    }
/*
Aqui colocamos entonces estas funciones que necesitan un parametro cronos del tipo Cronos que tomara el valor
de las funciones con mismo nombre qu estan claro en el repositorio se utiliza tambien el viewModelScope.launch
{} para ejecutar estas funciones en un hilo deifrente al principal donde gracias a la configuracion del CronosDabaseDao
no tenemos que especificar el hilo ya que Room ya lo hace por nosotros donde escoje el IO con esto hcaemos que la app
se ejecute de forma optima
 */
    fun addCrono(cronos: Cronos) = viewModelScope.launch { repository.addCrono(cronos) }
    fun updateCrono(cronos: Cronos) = viewModelScope.launch { repository.updateCrono(cronos) }
    fun deleteCrono(cronos: Cronos) = viewModelScope.launch { repository.deleteCrono(cronos) }

}