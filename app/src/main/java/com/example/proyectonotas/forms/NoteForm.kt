package com.example.proyectonotas.forms

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt

@Composable
fun NoteForm(
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    backgroundColor: String,
    onBackgroundColorChange: (String) -> Unit,
    primaryButtonText: String,
    primaryButtonIconId: Int,
    primaryButtonColor: Color = MaterialTheme.colorScheme.primary,
    primaryButtonAction: () -> Unit,
    secondaryButtonText: String,
    secondaryButtonIconId: Int,
    secondaryButtonColor: Color = MaterialTheme.colorScheme.primary,
    secondaryButtonAction: () -> Unit,
    disableAllFields: Boolean = false,
    modifier: Modifier = Modifier
) {

    var invalidTitleErrorMsg by remember { mutableStateOf("") }
    var invalidContentErrorMsg by remember { mutableStateOf("") }

    var context = LocalContext.current

    Box(modifier = Modifier.background(if (backgroundColor.isNotBlank()) Color(backgroundColor.toColorInt()) else MaterialTheme.colorScheme.background)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(36.dp)) {
            Field(label = "Titulo", value = title, onValueChange = { onTitleChange(it) }, errorMessage = invalidTitleErrorMsg,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize, disabled = disableAllFields)
            Field(label = "Contenido", value = content, onValueChange = { onContentChange(it) }, errorMessage = invalidContentErrorMsg, textArea = true,
                fontSize = MaterialTheme.typography.titleLarge.fontSize, disabled = disableAllFields)
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
                    onValueChange = { onBackgroundColorChange(it) },
                    disabled = disableAllFields
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = primaryButtonText, style = MaterialTheme.typography.titleMedium)
                    },
                    onClick = {
                        // hacer validaciones antes de hacer la accion del formulario, como agregar
                        // una nota o editarla

                        // resetear los errores cada que se hace click en el boton de accion primaria
                        invalidTitleErrorMsg = ""
                        invalidContentErrorMsg = ""

                        // flag para ver si hay errores
                        var thereAreErrors = false

                        if (title.isBlank()) {
                            invalidTitleErrorMsg = "El titulo es requerido"
                            thereAreErrors = true
                        }

                        if (content.isBlank()) {
                            thereAreErrors = true
                            invalidContentErrorMsg = "El contenido es requerido"
                        }

                        if (thereAreErrors) {
                            Toast.makeText(context, "Por favor, corrige los campos erróneos", Toast.LENGTH_SHORT).show()
                        } else {
                            primaryButtonAction()
                        }
                    },
                    icon = { Image(painterResource(id = primaryButtonIconId), contentDescription = primaryButtonText, modifier = Modifier.size(32.dp)) },
                    containerColor = primaryButtonColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .height(70.dp)
                )
                ExtendedFloatingActionButton(
                    text = {
                        Text(text = secondaryButtonText, style = MaterialTheme.typography.titleMedium)
                    },
                    onClick = secondaryButtonAction,
                    icon = { Image(painterResource(id = secondaryButtonIconId), contentDescription = secondaryButtonText, modifier = Modifier.size(32.dp)) },
                    containerColor = secondaryButtonColor,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .height(70.dp)
                )
            }
        }
    }
}

@Composable
fun Field(label: String, value: String, onValueChange: (String) -> Unit, errorMessage: String, textArea: Boolean = false, fontSize: TextUnit, disabled: Boolean = false, modifier: Modifier = Modifier) {
    // si hay un mensaje de error, es porque hay un error
    val isError = errorMessage.isNotBlank()

    TextField(
        value = value,
        label = { Text(label) },
        onValueChange = onValueChange,
        readOnly = disabled,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        },
        trailingIcon = {
            if (isError) {
                Icon(Icons.Filled.Info,"error", tint = MaterialTheme.colorScheme.error)
            }
        },
        textStyle = TextStyle(
        fontSize = fontSize
    ),
        modifier = if (textArea) modifier
            .height(400.dp)
            .fillMaxWidth() else modifier.fillMaxWidth())
}


@Composable
fun ColorSelector(label: String, selectableColors: List<String>, selectedColor: String, onValueChange: (String) -> Unit, disabled: Boolean = false){
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
            onClick = {
                if (!disabled) {
                    isExpanded = !isExpanded
                }
            },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(60.dp)
                .onGloballyPositioned { coordinates ->
                    // esto es para que el dropdown tenga el mismo ancho que el boton que lo activa
                    size = coordinates.size.toSize()
                },
        ) {
            if (selectedColor.isNotBlank()) {
                Text("$label: $selectedColor", fontSize = MaterialTheme.typography.labelLarge.fontSize, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            } else {
                Text(label, textAlign = TextAlign.Center, fontSize = MaterialTheme.typography.labelLarge.fontSize, modifier = Modifier.weight(1f))
            }
            if (!disabled) {
                Icon(icon,"Icono de selector",
                    Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )
            }
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
                        if (!disabled) {
                            onValueChange(color)
                            isExpanded = false
                        }
                    },
                    text = { Text(text = color, fontSize = MaterialTheme.typography.labelLarge.fontSize) },
                    trailingIcon = { Icon(Icons.Default.Star, "Icono de estrella", tint = Color(color.toColorInt())) })
            }
        }
    }
}