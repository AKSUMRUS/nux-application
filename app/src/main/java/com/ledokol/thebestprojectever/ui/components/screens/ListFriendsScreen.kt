package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList
import java.time.format.TextStyle

@Composable
fun ListFriendsScreen(navController: NavController){
    val friends = remember{ mutableStateListOf<String>("Петя", "Вася", "Анатолий","Георгий","Петя", "Вася", "Анатолий","Георгий","Петя", "Вася", "Анатолий","Георгий","Петя", "Вася", "Анатолий","Георгий")}

    fun onClick(navController: NavController){
        navController.navigate("friend_screen") {
            popUpTo("friend_screen")
            launchSingleTop = true
        }
    }

    val textInSearch = remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier,
        contentPadding = PaddingValues(top = 120.dp, start = 20.dp, end = 20.dp),
        content = {
            item(){
                Column(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                ){
                    HeadlineH4(
                        text = stringResource(id = R.string.nav_friends),
                        fontWeight = FontWeight.W700,
                    )
                    Body1(
                        text = "Выберите игру, в которую вы пойдете играть",
                        type = "background"
                    )
                }
            }

            item(){
                TextField(
                    value = textInSearch.value,
                    onValueChange = {textInSearch.value = it},
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 17.sp),
                    leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Gray) },
                    maxLines = 1,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFE7F1F1), RoundedCornerShape(16.dp)),
                    placeholder = { Text(text = "Bun") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        placeholderColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.DarkGray
                    )
                )
            }
            items(items = friends.filter{ it.lowercase().contains(textInSearch.value.lowercase()) }) { friend ->
                FriendInList(name = friend, onClick = { onClick(navController = navController) })
            }
        },
    )
}