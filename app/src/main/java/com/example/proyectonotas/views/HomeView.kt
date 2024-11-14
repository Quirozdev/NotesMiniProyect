package com.example.proyectonotas.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.proyectonotas.R

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectonotas.navigation.NotesList

@Composable
fun HomeView(navController: NavController, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround, modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.logo_unison), contentDescription = "Logo", modifier = Modifier.size(200.dp))
            Text(text = "Apunta!", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall)
        }
        ExtendedFloatingActionButton(
            text = {
                Text(text = "Ver Mis Notas", style = MaterialTheme.typography.titleMedium)
                   },
            onClick = {
                navController.navigate(NotesList)
            },
            icon = { Image(painter = painterResource(id = R.drawable.notas), contentDescription = "Lista de notas", modifier = Modifier.size(28.dp)) },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.width(300.dp).height(70.dp)
        )
        Text(text = "Quiroz Osuna Luis Daniel", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
    }
}