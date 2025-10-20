package com.example.roomcronoapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomcronoapp.components.CronCard
import com.example.roomcronoapp.components.FloatButton
import com.example.roomcronoapp.components.MainTitle
import com.example.roomcronoapp.components.formatTiempo
import com.example.roomcronoapp.viewModels.CronosViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
/*
Para crear nuestra vista HomeView lo primero que hacemos sera agregar un Scaffold donde con topBar agregamos
una barra centrada con el titulo CRONO APP donde tambien definimos el color que previamnet modificamos en Color
y en Theme para que sea de color naranja
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, cronosVM: CronosViewModel){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "CRONO APP") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        /*
        Agregamos nuestro floatingActionButton donde con ayuda del NavController agregamos la ruta para ir
        a la vista de addView y poder agregar un registro desde ahi.
         */
        floatingActionButton = {
            FloatButton {
                navController.navigate("AddView")
            }
        }
    ){
        /*
        Aqui agregamos nuestra funcion ContenHomeView la cual tendra el cuerpo de nuestra y en este
        caso contendra los padding values con it, el nanController, y el cronosVM
         */
        ContentHomeView(it, navController, cronosVM)
    }
}
/*
Esta funcion ContentHomeView nos servira para el cuerpo de nuestra app y es donde estaran o se mostraran
nuestros registros con su respectivo tiempo y titulo.

Nuestra funcion contiene los parametros it que guardaran los padding values esto para que nuestros elementos
no se encondan detras del topbar, el navController para viajar entre vistas y el cronosVM del tipo CronosViewModel
para acceder a las funciones de esta clase view model
 */
@Composable
fun ContentHomeView(it: PaddingValues, navController: NavController, cronosVM: CronosViewModel){
    //Primeramente colocamos un colum para agrupar nuestros elementos verticalmente y le pasamos los paddin values con it
    Column (
        modifier = Modifier.padding(it)
    ){

        /*
        Aqui con la val cronosList accedemos a la lista de registros de la base de datos donde escuchamos sus
        cambios con collectAsState para que en los elementos composable se vean reflejados estos cambios
         */
        val cronosList by cronosVM.cronosList.collectAsState()
/*
dentro de nuestro LazyColum que es una lista colocaremos un items(cronoList)->item donde item tomara el valor
de cada registro dentro del cronosList donde mandaremos llamar a nuestra funcion CronCard para que cada
registro que haya se muestre en nuestra lazyColum con el formato de CrondCard que nos mostrara en pantalla nuestro
registro
 */
        LazyColumn {
            items(cronosList){item ->
/*
Para cada item es necesario agregar el swipe para eliminar un elemento donde con icon agregamos el icono eliminar
y lo colocamos dentro del items para que cada elemento tenga esta proiedad, tambien agregamos el color rojo
al swipe para hacer alucion a eliminar, es importante agregar lo que haya el swipe que en este caso es eliminar
y con ayuda de onSwipe accedemos a la funcion cronosVM.deleteCrono(item) para eliminar nuestro registro.
 */
                val delete = SwipeAction(
                    icon = rememberVectorPainter(Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = {cronosVM.deleteCrono(item)}
                )
/*
ahora creamos un SwipeableActionBox que nos servira para que los elementos que se encuentren dentro de el se
puedan hacer swipe, tambien agregamos el endActions = listOf(delete) que indica que al deslizar aparecera la
accioneliminar esta accion eliminar la definimos antes en la val delete donde podemos agregar mas actiones
como si fuera una lista por eso el lisfOf, y el swipeThreshold tambien nos ayudara a indicaran hasta que punto
 o que tanto debemos deslizar para ejecutar la accion que en este caso es eliminar.
 */
                SwipeableActionsBox (endActions = listOf(delete), swipeThreshold = 240.dp){

                    /*
                    Ahora dentro de nuestro swipe mandamos llamar a nuestra funcion CronCard que ya tiene un
                    formato definido para mostrar la informacion que le pasamos mediante el parametro item.title
                    ,item.crono y una accion que nos enviara al hacer clic sobre el elemento a la vista editView
                    pero nos enviara a editar al elemento que seleccionamos gracias al item.id
                     */
                    CronCard(titulo = item.title, crono = formatTiempo(item.crono) ) {
                        navController.navigate("EditView/${item.id}")
                    }
                }



            }
        }

    }
}