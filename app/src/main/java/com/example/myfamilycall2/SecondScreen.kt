package com.example.myfamilycall2

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfamilycall2.database.MyUsers
import com.example.myfamilycall2.database.MyUsersViewModel
import com.example.myfamilycall2.ui.theme.testColor

@ExperimentalMaterialApi
@Composable
fun SecondScreen(navHostController: NavHostController){
    val myUsersViewModel:MyUsersViewModel = viewModel()
    val myContext = LocalContext.current
    val contactList = listOf<MyUsers>(
        MyUsers(0,R.drawable.fireman,"Fire assistance","101"),
        MyUsers(0,R.drawable.pol,"Police","102"),
        MyUsers(0,R.drawable.med,"Ambulance","103")
    )

    Scaffold(
        bottomBar = { MyBottomAppBar(myViewModel = myUsersViewModel, navHostController = navHostController)},
        content = {MyLazyColumn2(alertList = contactList)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {Toast.makeText(myContext,"Нельзя добавить экстренную службу",Toast.LENGTH_LONG).show()},
                backgroundColor = testColor
                ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    )



}

@Composable
private fun MyBottomAppBar(
    myViewModel: MyUsersViewModel,
    navHostController: NavHostController
){
    val myContext = LocalContext.current
    BottomAppBar(
        backgroundColor = testColor,
        cutoutShape = CircleShape
    ) {
        IconButton(
            onClick = {navHostController.navigate(Screen.ContactScreen.route)}
        ) {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f,true))
        IconButton(
            onClick = {Toast.makeText(myContext,"Нельзя удалить или редактировать экстренную службу",Toast.LENGTH_LONG).show()}
        ) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

//Lazy Column
@ExperimentalMaterialApi
@Composable
fun MyLazyColumn2(alertList:List<MyUsers>){
    val myContext = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(alertList.size){
            index -> Card(
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            onClick = {
                val myIntent = Intent(Intent.ACTION_DIAL) //ACTION_CALL выбрасывает ошибку
                val number = alertList[index].number
                myIntent.setData(Uri.parse("tel:$number"))
                myContext.startActivity(myIntent)
            }
        ) {
            Row() {
                Image(
                    painter = painterResource(id = alertList[index].image),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Column() {
                    Text(
                        text = alertList[index].name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = alertList[index].number,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp, top = 1.dp)
                    )
                }
            }
            }
        }
    }
}