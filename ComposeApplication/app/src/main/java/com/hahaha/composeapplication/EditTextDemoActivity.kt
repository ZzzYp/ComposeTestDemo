package com.hahaha.composeapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * TextField相当于原生的EditText
 *
 */
class EditTextDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                showEditText()
            }
        }
    }

    /**
     * TextField相当于原生的EditText
     */
    @Composable
    private fun showEditText() {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(value = text, onValueChange = {
            text = it
            Toast.makeText(this, "TextField + $it", Toast.LENGTH_SHORT).show()
        })
    }
}
