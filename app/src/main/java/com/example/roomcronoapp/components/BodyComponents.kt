package com.example.roomcronoapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomcronoapp.R

@Composable
fun MainTitle(title: String) {
    Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
}


@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)

    )
}
/*
Nuestra funcion formatTiempo nos ayuda a convertir nuestros milisegundos a un formato mas legible para el
usuario donde gracias a la variable o parametro tiempo obtenemos la cantidad de milisegundos posteriormente
para convertir los milisegundos a segundos dividimos el tiempo entre 1000 convirtiendo el tiempo a segundos
una vez hecho esto con el operador % nos ayudara a dividir nuestros segundos entre 60 donde divimos entre
60 para saber la cantidad de de minutos competlos que tenemos y lo que nos importanta cuantos segundos sobran

ahora con la val minutos convertimos el tiempo de igual forma de milisegundos a segundos posteriormente dividmos
entre 60 para saber la cantidad de minutos completos que tenemos y de nuevos entre 60 para obtener la cantidad
de horas completas que tenemos pero lo mas importante con el resto la cantidad de minutos sobrantes

Posteriormente en la val horas convertimos los milisegundos a segundos y despues dividimos el tiempo entre 3600
que es la cantidad de segundos en una hora para saber cuantas horas completas tenemos.
 */
@Composable
fun formatTiempo(tiempo: Long): String{
    val segundos = (tiempo/1000) % 60
    val minutos = (tiempo/1000/60) %60
    val horas = tiempo / 1000 / 3600
/*
Por ultimo retornaremos un valor de tipo string donde gracias a String.format nos permitira agregar las posiones
o numeros que desemos en este caso 3 donde % indica que insertaremos un valor, 02 indica que el numero debe de
tener 2 posicones por lo menos donde y si solo tiene una se rellena con 0 y d indica que el valor es entero decimal
y posteriormente agregamos nuestra variables en su posicion
 */
    return String.format("%02d:%02d:%02d", horas,minutos,segundos)
}


@Composable
fun CronCard(titulo: String, crono: String, onClick: ()-> Unit){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .clickable { onClick() }
    ){
        Column (
            modifier = Modifier.padding(15.dp)
        ){
            Text(text = titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )

            Row {
                Icon(painter = painterResource(id = R.drawable.time), contentDescription ="",
                    tint = Color.Gray
                    )

                Text(text = crono, fontSize = 20.sp)
            }
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}





