package com.example.proyectonotas.dialogs

import androidx.compose.runtime.Composable

@Composable
fun CancelDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    SimpleDialog(
        onDismissRequest = { onDismissRequest() },
        onConfirmation = { onConfirmation() },
        dialogTitle = "¿Cancelar?",
        dialogText = "Se perderán los cambios que hayas hecho",
        confirmationText = "Si",
        dismissText = "No"
    )
}