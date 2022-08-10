package com.example.myfamilycall2

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myfamilycall2.database.MyUsers
import com.example.myfamilycall2.database.MyUsersViewModel
import com.example.myfamilycall2.ui.theme.testColor

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MyHomeScreenn(navHostController: NavHostController){
    val myViewModel:MyUsersViewModel = viewModel()
    val userList = myViewModel.getAll.observeAsState(listOf()).value
    Scaffold(
        bottomBar = {MyBottomAppBar(myViewModel, navHostController)},
        content = {MyLazyColumn(myList = userList, myViewModel)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {myViewModel.addDialogState.value=true},
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

    if(myViewModel.addDialogState.value){
        MyAddDialog(viewModel = myViewModel)
    }
    if(myViewModel.updateDialogState.value){
        MyUpdateDialog(myViewModel = myViewModel)
    }

}

//BottomAppBar
@Composable
private fun MyBottomAppBar(
    myViewModel: MyUsersViewModel,
    navHostController: NavHostController
){
    BottomAppBar(
        backgroundColor = testColor,
        cutoutShape = CircleShape //отступ вокруг ActionButton
    ) {
        IconButton(
            onClick = {navHostController.navigate(route = Screen.AlertScreen.route)}
        ) {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = null,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f,true))
        IconButton(
            onClick = {myViewModel.visable.value=!myViewModel.visable.value}
        ) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

//Item for LazyColumn
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MyItem(
    users: MyUsers,
    myViewModel: MyUsersViewModel
){
    val myContext = LocalContext.current
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = {
            val myIntent = Intent(ACTION_DIAL) //ACTION_CALL выбрасывает ошибку
            val number = users.number
            myIntent.setData(Uri.parse("tel:$number"))
            myContext.startActivity(myIntent)
        }
    ) {
        Row() {
            Image(
                painter = painterResource(id = users.image),
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
            Column() {
                Text(
                    text = users.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = users.number,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 10.dp, top = 1.dp)
                )
            }
        }
        Box(
            contentAlignment = Alignment.CenterEnd
        ){
            Row() {
                AnimatedVisibility(
                    visible = myViewModel.visable.value,
                    enter = fadeIn(0f),
                    exit = fadeOut(100f)
                ) {
                    FloatingActionButton(
                        onClick = {
                            myViewModel.currentUser.value=users
                            myViewModel.updateDialogState.value=true
                        },
                        modifier = Modifier
                            .size(40.dp),
                        backgroundColor = Color(221, 127, 98)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                AnimatedVisibility(
                    visible = myViewModel.visable.value,
                    enter = fadeIn(0f),
                    exit = fadeOut(100f)
                ) {
                    FloatingActionButton(
                        onClick = {myViewModel.deleteUser(users)},
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .size(40.dp),
                        backgroundColor = Color(221, 127, 98)
                    ) {
                        Icon(imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

//Lazy Column
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun MyLazyColumn(myList:List<MyUsers>, myViewModel: MyUsersViewModel){
    LazyColumn(
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(myList.size){
            index -> MyItem(users = myList[index], myViewModel)
        }
        item{
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp))
        }
    }
}

//Add Dialog
@Composable
fun MyAddDialog(viewModel: MyUsersViewModel){

    val imageList = listOf(
        R.drawable.pic1,
        R.drawable.pic2,
        R.drawable.pic3,
        R.drawable.pic4,
        R.drawable.pic5,
        R.drawable.pic6,
        R.drawable.pic7,
        R.drawable.pic8,
        R.drawable.pic9,
        R.drawable.pic10,
        R.drawable.pic11,
        R.drawable.pic12,
        R.drawable.pic13,
        R.drawable.pic14,
        R.drawable.pic15
    )

    var nameValue = remember{mutableStateOf("")}
    var numberValue = remember{mutableStateOf("")}
    var imageValueSelected = remember{mutableStateOf(imageList[0])}

    val myContext = LocalContext.current

    Dialog(
        onDismissRequest = { viewModel.addDialogState.value=false }
    ) {
        Card(
            modifier = Modifier.size(300.dp,440.dp),
            shape = RoundedCornerShape(20.dp)
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value = nameValue.value,
                    onValueChange = {value -> nameValue.value=value},
                    shape = CircleShape,
                    modifier = Modifier.size(100.dp, 60.dp),
                    label = {Text(text = "Name")}
                )
                LazyRow(
                    modifier = Modifier.padding(top = 20.dp)
                ){
                    items(imageList){
                        image ->
                        val selected = imageValueSelected.value==image
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = null,
                            modifier = Modifier
                                .selectable(
                                    selected = selected,
                                    onClick = { imageValueSelected.value = image }
                                )
                                .size(
                                    width = if (selected) {
                                        90.dp
                                    } else {
                                        80.dp
                                    }, height = if (selected) {
                                        130.dp
                                    } else {
                                        120.dp
                                    }
                                )
                        )
                    }
                }
                OutlinedTextField(
                    value = numberValue.value,
                    onValueChange = {value -> numberValue.value=value},
                    shape = CircleShape,
                    modifier = Modifier
                        .size(200.dp, 60.dp),
                    label = { Text(text = "Number")},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                FloatingActionButton(
                    onClick = {

                        try {
                            val y = numberValue.value.toLong() //При exception код дальше выполняться не должен

                            val insertUser = MyUsers(
                                id = 0, //autogenerate
                                image = imageValueSelected.value,
                                name = nameValue.value,
                                number = numberValue.value
                            )
                            viewModel.insertUser(insertUser)
                            viewModel.addDialogState.value=false
                        } catch (e:Exception){
                            Toast.makeText(myContext,"Введите корректный номер",Toast.LENGTH_LONG).show()
                        }

                    },
                    shape = CircleShape,
                    backgroundColor = Color(221, 127, 98),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(50.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                }
            }
        }
    }
}

//Update Dialog
@Composable
fun MyUpdateDialog(
    myViewModel: MyUsersViewModel
){

    val imageList = listOf(
        R.drawable.pic1,
        R.drawable.pic2,
        R.drawable.pic3,
        R.drawable.pic4,
        R.drawable.pic5,
        R.drawable.pic6,
        R.drawable.pic7,
        R.drawable.pic8,
        R.drawable.pic9,
        R.drawable.pic10,
        R.drawable.pic11,
        R.drawable.pic12,
        R.drawable.pic13,
        R.drawable.pic14,
        R.drawable.pic15
    )

    var idValue = remember{ mutableStateOf(myViewModel.currentUser.value.id)}
    var nameValue = remember{mutableStateOf(myViewModel.currentUser.value.name)}
    var numberValue = remember{mutableStateOf(myViewModel.currentUser.value.number)}
    var imageValueSelected = remember{mutableStateOf(myViewModel.currentUser.value.image)}

    val myContext = LocalContext.current

    Dialog(
        onDismissRequest = { myViewModel.updateDialogState.value=false }
    ) {
        Card(
            modifier = Modifier.size(300.dp,440.dp),
            shape = RoundedCornerShape(20.dp)
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    value = nameValue.value,
                    onValueChange = {value -> nameValue.value=value},
                    shape = CircleShape,
                    modifier = Modifier.size(100.dp, 60.dp),
                    label = {Text(text = "Name")}
                )
                LazyRow(
                    modifier = Modifier.padding(top = 20.dp)
                ){
                    items(imageList){
                            image ->
                        val selected = imageValueSelected.value==image
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = null,
                            modifier = Modifier
                                .selectable(
                                    selected = selected,
                                    onClick = { imageValueSelected.value = image }
                                )
                                .size(
                                    width = if (selected) {
                                        90.dp
                                    } else {
                                        80.dp
                                    }, height = if (selected) {
                                        130.dp
                                    } else {
                                        120.dp
                                    }
                                )
                        )
                    }
                }
                OutlinedTextField(
                    value = numberValue.value,
                    onValueChange = {value -> numberValue.value=value},
                    shape = CircleShape,
                    modifier = Modifier
                        .size(200.dp, 60.dp),
                    label = { Text(text = "Number")},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                FloatingActionButton(
                    onClick = {
                        try{
                            val x = numberValue.value.toLong()

                            val updateUser = MyUsers(
                                id = idValue.value,
                                image = imageValueSelected.value,
                                name = nameValue.value,
                                number = numberValue.value
                            )
                            myViewModel.updateUser(updateUser)
                            myViewModel.updateDialogState.value=false
                        } catch (e:Exception){
                            Toast.makeText(myContext,"Введите корректный номер", Toast.LENGTH_LONG).show()
                        }
                    },
                    shape = CircleShape,
                    backgroundColor = Color(221, 127, 98),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(50.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = null)
                }
            }
        }
    }
}