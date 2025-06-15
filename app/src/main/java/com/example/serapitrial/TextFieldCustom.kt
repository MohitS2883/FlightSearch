package com.example.serapitrial

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldCustom(
    text: MutableState<String>,
    onChange: (String) -> Unit,
    direction: String
) {
    OutlinedTextField(
        value = text.value,
        onValueChange = onChange,
        label = { Text("Enter the $direction Airport") },
        placeholder = { Text("BLR") },
        singleLine = true,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    TextFieldCustom(
        text = remember { mutableStateOf("ABC") },
        onChange = {},
        direction = "Departure"
    )
}