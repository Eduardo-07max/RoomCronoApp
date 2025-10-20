package com.example.roomcronoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.roomcronoapp.navigation.NavManager
import com.example.roomcronoapp.ui.theme.RoomCronoAppTheme
import com.example.roomcronoapp.viewModels.CronometroViewModel
import com.example.roomcronoapp.viewModels.CronosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cronometroVM : CronometroViewModel by viewModels()
        val cronosVM: CronosViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            RoomCronoAppTheme {
                /*
                Invocamos nuestra funcion NavManager donde la pasamos el parametro cronometroVM para tener
                acceso a las variables de tiempo titulo y nuestros metodos para iniciar o parar el cronometro y
                con cronosVM obtemos acceso a la funcion mostrar todos los registros la funcion actualizar, elimiar
                y insertar
                 */
               NavManager(cronometroVM, cronosVM)
            }
        }
    }
}





