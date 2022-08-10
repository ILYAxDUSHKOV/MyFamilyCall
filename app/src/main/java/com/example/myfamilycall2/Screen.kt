package com.example.myfamilycall2

sealed class Screen(val route:String){
    object ContactScreen:Screen(route = "contact_screen")
    object AlertScreen:Screen(route = "alert_screen")
}
