package com.ledokol.thebestproject.ui.components.atoms.textfields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PhoneNumberOld(){
    val focusManager = LocalFocusManager.current

    var phone0 by remember{ mutableStateOf("+7")}
    var phone1 by remember{ mutableStateOf("")}
    var phone2 by remember{ mutableStateOf("")}
    var phone3 by remember{ mutableStateOf("")}
    var phone4 by remember{ mutableStateOf("")}

    fun onPhone0Change(text: String){
        phone0 = text
    }

    fun phoneChange(phone:String, text: String, size:Int): Boolean{
        if(phone.length>=size&&text.length>=size){
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
            return false
        }
        return true
    }

    fun onPhone1Change(text: String){
        if(phoneChange(phone1,text,3)) {
            phone1 = text
            if (phone1.length >= 4) {
                focusManager.moveFocus(
                    focusDirection = FocusDirection.Next,
                )
            }
        }
    }
    fun onPhone2Change(text: String){
        if(phoneChange(phone2,text,3)){
            phone2 = text
            if (phone2.length>=3) {
                focusManager.moveFocus(
                    focusDirection = FocusDirection.Next,
                )
            }
        }
    }

    fun onPhone3Change(text: String){
        if(phoneChange(phone3,text,2)) {
            phone3 = text
            if (phone3.length >= 2) {
                focusManager.moveFocus(
                    focusDirection = FocusDirection.Next,
                )
            }
        }
    }
    fun onPhone4Change(text: String){
        if(phoneChange(phone4,text,2)) {
            phone4 = text
            if (phone4.length >= 2) {
                focusManager.moveFocus(
                    focusDirection = FocusDirection.Next,
                )
            }
        }
    }

    var textStyle = MaterialTheme.typography.body2
    textStyle = textStyle.copy(
        textAlign = TextAlign.Center
    )

    val modifier:Modifier = Modifier.padding(end = 8.dp)
//        .requiredWidth(size.width+16.dp)
//        .offset(x=(-8).dp)
        .drawBehind {
            val strokeWidth = 2 * density
            val y = size.height - strokeWidth / 2

            drawLine(
                Color.LightGray,
                Offset(0f, y),
                Offset(size.width, y),
                strokeWidth
            )
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        TextField(
            text = phone0,
            onValueChange = { onPhone0Change(it) },
            modifier = Modifier
                .padding(0.dp)
                .weight(8f)
                .then(modifier)
            ,
            enabled = false,
        )
        TextField(
            text = phone1,
            onValueChange = { onPhone1Change(it) },
            modifier = Modifier
                .padding(0.dp)
                .weight(10f)
                .align(CenterVertically)
                .then(modifier)
            ,
            textStyle = textStyle,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
        )
        TextField(
            text = phone2,
            onValueChange = { onPhone2Change(it) },
            modifier = Modifier
                .weight(8f)
                .then(modifier)
            ,
            textStyle = MaterialTheme.typography.body2,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
        )
        TextField(
            text = phone3,
            onValueChange = { onPhone3Change(it) },
            modifier = Modifier
                .weight(7f)
                .then(modifier)
            ,
            textStyle = MaterialTheme.typography.body2,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
        )
        TextField(
            text = phone4,
            onValueChange = { onPhone4Change(it) },
            modifier = Modifier
                .weight(7f)
                .then(modifier)
            ,
            textStyle = MaterialTheme.typography.body2,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
        )
    }

}
