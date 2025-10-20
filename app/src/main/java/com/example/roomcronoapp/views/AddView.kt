package com.example.roomcronoapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomcronoapp.R
import com.example.roomcronoapp.components.CircleButton
import com.example.roomcronoapp.components.FloatButton
import com.example.roomcronoapp.components.MainIconButton
import com.example.roomcronoapp.components.MainTextField
import com.example.roomcronoapp.components.MainTitle
import com.example.roomcronoapp.components.formatTiempo
import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.viewModels.CronometroViewModel
import com.example.roomcronoapp.viewModels.CronosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(navController: NavController, cronometroVM: CronometroViewModel, cronosVM: CronosViewModel){
    Scaffold (topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "ADD CRONO") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(Icons.Default.ArrowBack) {

                        navController.popBackStack()

                    }
                }
            )

        }
    ){
        ContentAddView(it, navController,cronometroVM,cronosVM)
    }
}

@Composable
fun ContentAddView(it: PaddingValues, navController: NavController, cronometroVM: CronometroViewModel,cronosVM: CronosViewModel){

    val state = cronometroVM.state
/*
este launchedEffect activara nuestro cronometro en caso de que la val cronometroActivo sea true
 */
    LaunchedEffect(state.cronometroActivo) {
        cronometroVM.cronos()
    }
    /*
    Agregamos esta funcion para limpiar nuestras variables o restablecerlas para que todo inicie desde cero
     */
    LaunchedEffect(Unit) {
        cronometroVM.clearForNew()
    }
/*
Colocamos un colum para que nuestros elementos esten ordenados de arriba hacia abajo de forma vertical
 */
    Column (
        modifier = Modifier.padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        /*
        Agregamos este text donde mandamos llamar a nuestra funcion Format Tiempo para que nos muestre el tiempo
        en formato de horas, minutos y segundos y le pasamos la variable tiempo para que pueda mostrar el tiempo
        que tenemos guardado en esta variable.
         */
Text(text = formatTiempo(cronometroVM.tiempo),
    fontSize = 50.sp,
    fontWeight = FontWeight.Bold
    )
        /*
        Ahora dentro de una Row Colocamos nuestros botones para que se agrupen de forma horizontal
         */
        Row (horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
            ){
            //Boton Iniciar
            /*
            Invocamos a nuestra funcion CircleButton donde le pasamos la imagen que tendra con id con enable
            indicamos que este boton solo lo podremos pulsar si cronometroActivo esta en false y lo que hara el
            boton sera cambiar la variable cronometroActivo a true lo cual iniciara la funcion crono y comenzara
            a contar el tiempo
             */
            CircleButton(
                painterResource(id = R.drawable.play),
                enable = !state.cronometroActivo) {
                cronometroVM.iniciar()
            }

            //Pausar
            /*
            invocamos de nuevo a nuestra funcion Circle boton le agregamos la imagen que tendra con enable le indicamos
            que solo se podra pulsar cuando la variable cronometroActivo sea true y lo que hara nuestro boton sera llamar
            a nuestra funcion pausar que cambiara la variable cronometroActivo a false y showSaveButton para mostrar el boton
            guardar
             */
            CircleButton(
                painterResource(id = R.drawable.pausa),
                enable = state.cronometroActivo) {
                cronometroVM.pausar()
            }

            //Detener
            /*
            Mandamos llamar a nuestra funcion Circle buton le agregamos su icono y posteriormente mandamos llamar a la
            funcion detener que nos permitira detener el cronometro y limpiar nuestras variables de tiempo y detiene el crono
            job ademas de colocar la variables boleanas en false
             */
            CircleButton(
                painterResource(id = R.drawable.stop),
                enable = !state.cronometroActivo) {
                cronometroVM.detener()
            }

            //mostrar guardar
/*
aqui invamos de nuevo la funcion CircleButton donde le agregamos el icono que tendra con enable le decimos al
programa que solo lo podremos pulsar si la variable showSaveButton esta en true y lo que el boton hara sera
cambiar el valor showTextField a true lo que hara que aparesca el textfield
 */
            CircleButton(
                painterResource(id = R.drawable.save),
                enable = state.showSaveButton) {
                cronometroVM.showTextField()
            }
        }
        /*
        Con este bloque if hacemos que si la variable showTextField es true nos mostrara el text field para
        guardar el titulo de nuestro cronometro donde este titulo sera guardado en state.title y tambien nos mostrara
        el boton guardar donde gracias a la funcion addCrono se guardara un registro donde el registro cronos tomara
        el titulo de la funcion state.title, gracias a crono guardaremos el tiempo en el registro tomando el valor
        de cronometroVM.tiempo
         */
        if(state.showTextField){
            MainTextField(value = state.title,
                onValueChange ={ cronometroVM.onValue(it)} ,
                label ="Titulo" )

            Button(onClick = {
                cronosVM.addCrono(
                Cronos(
                    title = state.title,
                    crono = cronometroVM.tiempo
                )
            )
                /*
                Ademas detenemos el cronometro y volvemos a homeView
                 */
            cronometroVM.detener()
                navController.popBackStack()
            }) {
                Text("Guardar")
            }

        }
    }
}