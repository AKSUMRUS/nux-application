package com.ledokol.thebestprojectever.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.molecules.FriendInList

@Composable
fun ListFriendsScreen(){
    var friends = remember{ mutableStateListOf<String>("Петя", "Вася", "Анатолий","Георгий","Петя", "Вася", "Анатолий","Георгий","Петя", "Вася", "Анатолий","Георгий","Петя", "Вася", "Анатолий","Георгий")}

    LazyColumn(content = {
        items(friends){
            friend -> FriendInList(name = friend)
        }
    },
//        verticalArrangement = Arrangement.SpaceAround,
        )
}