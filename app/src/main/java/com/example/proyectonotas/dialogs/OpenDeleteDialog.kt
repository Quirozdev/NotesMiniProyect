package com.example.proyectonotas.dialogs

import androidx.compose.runtime.Composable

@Composable
fun OpenDeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    SimpleDialog(
        onDismissRequest = { onDismissRequest() },
        onConfirmation = { onConfirmation() },
        dialogTitle = "¿Borrar nota?",
        dialogText = "Esta acción es irreversible",
        confirmationText = "Confirmar",
        dismissText = "Cancelar"
    )
}