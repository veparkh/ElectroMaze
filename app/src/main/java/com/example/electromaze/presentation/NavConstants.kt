package com.example.electromaze.presentation

object NavConstants {
    const val DEVICE_SCREEN = "device_screen"
    const val CONNECTING_SCREEN = "connecting_screen"
    const val MODE_CHOICE_SCREEN = "MODE_SCREEN"
    const val MANUAL_CONTROL_SCREEN = "manual_control_screen"
    const val AUTO_CONTROL_SCREEN = "auto_control_screen"
    const val MAP_BUILDING_SCREEN = "map_building_screen"
}

sealed interface Screens{
    data object DEVICE_SCREEN:Screens
    data object CONNECTING_SCREEN:Screens
    data object MODE_CHOICE_SCREEN:Screens
    data object MANUAL_CONTROL_SCREEN:Screens
    data object AUTO_CONTROL_SCREEN:Screens
    data object MAP_BUILDING_SCREEN:Screens

}
