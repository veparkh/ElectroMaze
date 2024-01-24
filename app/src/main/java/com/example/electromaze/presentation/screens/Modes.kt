package com.example.electromaze.presentation.screens

sealed interface Modes {
    data object ManualControl: Modes
    data object AutoControl: Modes
    data object MapBuilding: Modes
}