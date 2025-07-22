package com.example.test1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.test1.ui.theme.Test1Theme
import androidx.compose.runtime.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.toSize
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FormUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FormUI(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false)}
    val genders = listOf("Female", "Male", "Other")
    var selectedGender by remember { mutableStateOf("")}
    var textFieldSize by remember { mutableStateOf(Size.Zero)}
    val hobbies = listOf("Mountaineering", "Photography", "Coding", "Eating")
    val selectedHobbies = remember {
        hobbies.associateWith { mutableStateOf(false) }
    }
    val experienceLevel = listOf("Beginner", "Intermediate", "Advanced")
    var selectedExperienceLevel by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    val showPassword = remember { mutableStateOf(false)}

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .selectableGroup()
    ) {
        Text(
            text = "Gamoras Code Challenge 4",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter your name:")
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter your Email:")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter your Message:")
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5,
            maxLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        val icon = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Text("Select your Gender:")
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {selectedGender = it},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = {Text("Select Gender")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable{expanded = !expanded})
            },
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            genders.forEach {label ->
                DropdownMenuItem(
                    text = {Text(text = label)},
                    onClick = {
                        selectedGender = label
                        expanded = false
                    }
                )}
            }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select your Hobbies:")

        selectedHobbies.forEach { (hobby, checkedState) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { isChecked ->
                        checkedState.value = isChecked
                    }
                )
                Text(
                    text = hobby,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select your experience level:")
        experienceLevel.forEach{ exp ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedExperienceLevel == exp,
                        onClick = {selectedExperienceLevel = exp},
                        role = Role.RadioButton
                    )
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedExperienceLevel == exp,
                    onClick = null
                )

                Text(
                    text = exp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Enter your Password:")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation =
                if (!showPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                val passwordIcon = if (showPassword.value)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    showPassword.value = !showPassword.value
                }) {
                    Icon(imageVector = passwordIcon, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
        )
    }
}
