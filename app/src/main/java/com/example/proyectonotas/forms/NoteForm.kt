package com.example.proyectonotas.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.proyectonotas.R
import com.example.proyectonotas.model.Note
import com.example.proyectonotas.navigation.NotesList
import com.example.proyectonotas.viewmodels.NoteViewModel

@Composable
fun NoteForm(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    backgroundColor: String,
    onBackgroundColorChange: (String) -> Unit,
    textColor: String,
    onTextColorChange: (String) -> Unit,
    onSubmit: () -> Unit,
    submitButtonText: String,
    submitButtonIconId: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.background(if (backgroundColor.isNotBlank()) Color(backgroundColor.toColorInt()) else MaterialTheme.colorScheme.background)) {
        Column(verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(36.dp)) {
            Field(label = "Titulo", value = title, onValueChange = { onTitleChange(it) }, errorMessage = "",
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontColor = (if (textColor.isNotBlank()) Color(textColor.toColorInt()) else MaterialTheme.colorScheme.onPrimaryContainer))
            Field(label = "Contenido", value = content, onValueChange = { onContentChange(it) }, errorMessage = "", textArea = true,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontColor = (if (textColor.isNotBlank()) Color(textColor.toColorInt()) else MaterialTheme.colorScheme.onPrimaryContainer))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ColorSelector(label = "Color de fondo",
                    selectableColors = listOf(
                        "#4c662b", "#586249", "#386663", "#ba1a1a",
                        "#cdeda3", "#dce7c8", "#bcece7", "#ffdad6",
                        "#dadbd0", "#f9faef", "#f9faef", "#2f312a",
                    ),
                    selectedColor = backgroundColor,
                    onValueChange = { onBackgroundColorChange(it) }
                )
                ColorSelector(label = "Color de letra",
                    selectableColors = listOf(
                        "#ffffff", "#102000", "#151e0b", "#410002",
                        "#1a1c16", "#44483d", "#75796c", "#c5c8ba",
                        "#75796c", "#c5c8ba", "#f1f2e6", "#2f312a",
                    ),
                    selectedColor = textColor,
                    onValueChange = { onTextColorChange(it) }
                )
            }
            ExtendedFloatingActionButton(
                text = {
                    Text(text = submitButtonText, style = MaterialTheme.typography.titleMedium)
                },
                onClick = onSubmit,
                icon = { Image(painterResource(id = submitButtonIconId), contentDescription = submitButtonText, modifier = Modifier.size(32.dp)) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .width(260.dp)
                    .height(70.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Field(label: String, value: String, onValueChange: (String) -> Unit, errorMessage: String, textArea: Boolean = false, fontSize: TextUnit, fontColor: Color) {
    TextField(value = value, label = { Text(label) }, onValueChange = onValueChange, textStyle = TextStyle(
        fontSize = fontSize,
        color = fontColor
    ),
        modifier = if (textArea) Modifier
            .height(320.dp)
            .fillMaxWidth() else Modifier.fillMaxWidth())
}


@Composable
fun ColorSelector(label: String, selectableColors: List<String>, selectedColor: String, onValueChange: (String) -> Unit){
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var isExpanded by remember { mutableStateOf(false) }

    var size by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column() {
        OutlinedButton(
            onClick = { isExpanded = !isExpanded },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(60.dp)
                .width(180.dp)
                .onGloballyPositioned { coordinates ->
                    // esto es para que el dropdown tenga el mismo ancho que el boton que lo activa
                    size = coordinates.size.toSize()
                },
        ) {
            if (selectedColor.isNotBlank()) {
                Text("$label: $selectedColor", fontSize = MaterialTheme.typography.labelSmall.fontSize, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            } else {
                Text(label, textAlign = TextAlign.Center, fontSize = MaterialTheme.typography.labelSmall.fontSize, modifier = Modifier.weight(1f))
            }
            Icon(icon,"Icono de selector",
                Modifier.clickable { isExpanded = !isExpanded })
        }


        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
                // esto es para que el dropdown tenga el mismo ancho que el boton que lo activa
                .width(with(LocalDensity.current){size.width.toDp()})
        ) {
            selectableColors.forEach { color ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(color)
                        isExpanded = false
                    },
                    text = { Text(text = color, fontSize = MaterialTheme.typography.labelSmall.fontSize) },
                    trailingIcon = { Icon(Icons.Default.Star, "Icono de estrella", tint = Color(color.toColorInt())) })
            }
        }
    }
}