package com.example.roomcronoapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.roomcronoapp.components.MainIconButton
import com.example.roomcronoapp.components.MainTextField
import com.example.roomcronoapp.components.MainTitle
import com.example.roomcronoapp.components.formatTiempo
import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.viewModels.CronometroViewModel
import com.example.roomcronoapp.viewModels.CronosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(navController: NavController,
             cronometroVM: CronometroViewModel,
             cronosVM: CronosViewModel,
             id: Long){
    Scaffold (topBar = {
        CenterAlignedTopAppBar(
            title = { MainTitle(title = "EDIT CRONO") },
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
        ContentEditView(it, navController,cronometroVM,cronosVM,id)
    }
}

@Composable
fun ContentEditView(it: PaddingValues,
                    navController: NavController,
                    cronometroVM: CronometroViewModel,
                    cronosVM: CronosViewModel,
                    id: Long
){
/*
Con esta val tenemos acceso al viewModel CronometroViewModel y asu vez con .state obtenemos acceso a las variables
guardadas en .state que asu vez guardan la data class CronoState que contiene las variables cronometroActivo,
showSaveButton, showTextField que todas estas son de tipo bolean y nos servira para mostrar los botones o textfiles
o activar el cronometro y tambien contiene title de tipo string para guardar el titulo
 */
    val state = cronometroVM.state
/*
ejecutamos un launchedEffect para que se ejecute esta tarea en una corrutina cada que la vaariable cronometroactivo
cambie de true a false o de false a true y esta ejecutara o iniciara el cronometro de nuevo sumando mas tiempo al
que ya tenemos
 */
    LaunchedEffect(state.cronometroActivo) {
        cronometroVM.cronos()
    }
/*
Este bloque lo ejecutamos solo 1 vez gracias al Unit y se ejeucutara cada vez que entremos a la vista editar
y dentro con cronometroVM.getCronoById mostraremos o obtendremos un solo registro en base a un id donde
la funcion getCronoById se encargara de almanecnar el registro de tiempo en la variaable tiempo y el titulo de
ese registro
 */
    LaunchedEffect(Unit) {
        cronometroVM.getCronoById(id)
    }

    Column (
        modifier = Modifier.padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        /*
        Este text nos ayudara a mostrar el tiempo que hay guardado en ese registro ya la varible tiempo
        guardara el tiempo que hay en ese registro y lo hara con la funcion format para mostrarlo en
        formato de horas, minutos y segundos
         */
        Text(text = formatTiempo(cronometroVM.tiempo),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )


/*
Posteriormente colocamos un Row para agrupar nuestros botones horizontalmente
 */
        Row (horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ){
            //Boton Iniciar
            /*
            Mandamos llamar a nuestra funcion circle button donde le pasamos los parametros requeridos por la
            funcion que es id = que es igual a la imagen que guardamos para play y con enable colacamos que solo
            se puede tocar tocar el boton si esta corriendo y posteriormente accedemos a la funcion iniciar que lo
            que hace esta funcion es cambiar el valor de la variable cronometroActivo a true lo cual inicia la
            funcion crono encargada de sumar mas tiempo a la val tiempo
             */
            CircleButton(
                painterResource(id = R.drawable.play),
                enable = !state.cronometroActivo) {
                cronometroVM.iniciar()
            }

            //Pausar
            /*
            volvemos a invocar nuestra funcion CircleButton donde en el painterResource le pasamos el icono que queremos
            que tenga nuestro boton y con enable le decimos que este boton solo se podra presionar si la varibale cronometroActivo
            es true y posteriormente en la accion que realizara nuestro boton colocamos que mandara llamar a la funcion la cual
            cambiara el valor de la variable cronometroActivo a false y la variable showSaveButton a true lo que hara
            que el boton guardar se pueda tocar y que el crononometro se pare
             */
            CircleButton(
                painterResource(id = R.drawable.pausa),
                enable = state.cronometroActivo) {
                cronometroVM.pausar()
            }


            //mostrar guardar
            /*
            Volvemos a invocar a nustra funcion criclebutton y le agregamos su icono correspondiente que en este
            caso es el de guardar donde para poder tocarlo establecemos en enable que la variable showSaveButton
            debe estar en true para poder ser presionado y posteriormente llama a la funcion showTextField que
            hace que nos muestre el textfiel cambiando la variable showTexField a true
             */
            CircleButton(
                painterResource(id = R.drawable.save),
                enable = state.showSaveButton) {
                cronometroVM.showTextField()
            }
        }

/*
Con ayuda de este textfield guardaremos en la val tittle el titulo que se escriba en este textfiel donde por defecto
le asignamos el texto o el valor que esta en nuestro registro con state.title que hara que al entrar a la vista editar
tenga el titulo que posee nuestro registro a editar
 */
            MainTextField(value = state.title,
                onValueChange ={ cronometroVM.onValue(it)} ,
                label ="Titulo" )
/*
Ahora con este boton mandamos llamar a nuestra funcion updateCrono que dentro le psamos un nuevo registro crono
donde en base al id actual modicaremos ese registro donde title lo cambiamos por el nuevo valor guardado en el
textfield y el crono osea el tiempo el nuevo valor de tiempo y al pulsarlo actualizara estos registros y con
navController.popBackStack volveremos al homeView
 */
            Button(onClick = {
                cronosVM.updateCrono(
                    Cronos(
                        id = id,
                        title = state.title,
                        crono = cronometroVM.tiempo
                    )
                )

                navController.popBackStack()
            }) {
                Text("Guardar")
            }
/*
Ahora este DisposableEffect con ayuda de Unit ayuda a que se ejecute solo una vez detectando el ciclo de vida
de un activity donde permite ejecutar codigo despues de dejar de usar la vista donde una vez que dejes de usar
la vista ejecutes OnDispose que nos permitira ejecutar una linea de codigo en este caso si dejas el cronometro
activo y lo abandonas sin actualizar nada el cronosmentro se detiene y con la funcion detenr limpia nuestra variables
o en este caso dejando nuestra variables boleanas en false y nuestro tiempo en 0
 */
        DisposableEffect(Unit) {
            onDispose {
                cronometroVM.detener()
            }
        }


    }
}