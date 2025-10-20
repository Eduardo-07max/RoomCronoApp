package com.example.roomcronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomcronoapp.viewModels.CronometroViewModel
import com.example.roomcronoapp.viewModels.CronosViewModel
import com.example.roomcronoapp.views.AddView
import com.example.roomcronoapp.views.EditView
import com.example.roomcronoapp.views.HomeView
/*
Primeramente aqui creamos una funcion Composable que nos ayudara a la navegacion entre pantallas
 */
@Composable
fun NavManager(cronometroVM: CronometroViewModel, cronosVM: CronosViewModel){
    /*
    Creamos primero el objeto navController que nos servira para la navegacion entre pantallas
     */
    val navController = rememberNavController()
    /*
    Posteriormente con NavHost le pasamos el paramatro navController, con starDestination definimos cual sera la
    primera pantalla que se mostrara que en este caso sera "Home"
     */
    NavHost(navController = navController, startDestination = "Home"){
        /*
        con cada composable definimos la ruta la primera dentro de composable ponemos "Home" y dentro colocamos
        la funcion HomeView con sus respectivos parametros donde ya definimos la primer ruta que aparecera por
        defecto lo cual hicimos anteriormente
         */
        composable("Home"){
            HomeView(navController, cronosVM)
        }
        /*
        Aqui definimos otra ruta en este caso la ruta a addView y invocamos la respectiva funcion AddView
        y le pasamos sus parametros
         */
        composable("AddView"){
            AddView(navController,cronometroVM, cronosVM)
        }
        /*
        Por ultimo en la ruta EditView agregamos el /{id} que dice que ademas de viajar a la ruta espera un
        parametro dinamico, con arguments listOf es una lista que declara los elementos que espera, navArgument("id")
        crea la definicon del argumento llamado id, type = NavType.LongType le dice al sistema que el argumento debe
        ser de tipo Long
         */
        composable("EditView/{id}", arguments = listOf(
            navArgument("id"){ type = NavType.LongType}
        )){
            /*
            el val id = it.arguments guarda los elementos enviados en la navagacion y .getLong lee y convierte el
            argumento que guardamos a Long y el operador ?: 0 indica que si lo que esta en la izquierda es null, entonces
            mejor usa lo de la derecha
             */
            val id = it.arguments?.getLong("id")?: 0
            /*
            por ultimo si colocamos a que vista iremos invocando a la funcion EditView pasandole los parametros
            navController, cronometroVM y el ID
             */
            EditView(navController, cronometroVM, cronosVM, id)
        }
    }
}