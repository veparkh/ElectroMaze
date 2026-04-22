<h1 align="center">ElectroMaze</h1>
<p align="center">
  <b>Android Bluetooth client for the maze-solving system</b><br>
  <sub>Companion app for <a href="https://github.com/veparkh/thesisRaspberry">thesisRaspberry</a> — my BSc thesis project</sub>
</p>

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat&logo=kotlin&logoColor=white">
  <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat&logo=jetpackcompose&logoColor=white">
  <img alt="Android" src="https://img.shields.io/badge/Android-34A853?style=flat&logo=android&logoColor=white">
  <img alt="Bluetooth" src="https://img.shields.io/badge/Bluetooth%20RFCOMM-0082FC?style=flat&logo=bluetooth&logoColor=white">
</p>

---

> Part of a 3-repo thesis project alongside **[thesisRaspberry](https://github.com/veparkh/thesisRaspberry)** (vision & control software) and **[ElectroMazeSTM](https://github.com/veparkh/ElectroMazeSTM)** (STM32 firmware).

## What it does

An Android app that acts as the user-facing remote and display for the maze-solving system. The host PC does the vision and control; the phone gives the operator a live view and a way to steer manually.

- Connects to the host PC over **Bluetooth RFCOMM**.
- Receives and displays the live overhead camera view.
- Lets the user choose between **three modes**:
  - **Manual** — reads the phone's orientation sensor and streams roll / pitch angles back to the host, which forwards them to the tilt-stage firmware ([ElectroMazeSTM](https://github.com/veparkh/ElectroMazeSTM)) over UART.
  - **Auto** — the host computes the path; the phone just displays progress with the planned trajectory overlaid.
  - **Map building** — scaffold for capturing a new maze layout.

The phone never talks to the STM32 directly — it's a companion UI for the PC, not a bridge.

## Demo

### Manual control
The phone's orientation drives the tilt stage in real time — roll/pitch angles from the accelerometer are sent over Bluetooth and translated into mechanical tilt.

https://github.com/user-attachments/assets/1e232227-23a1-45f9-9fb5-989013c440c5


## Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Architecture:** MVVM
- **Concurrency:** Kotlin Coroutines + Flow
- **I/O:** Bluetooth RFCOMM
- **Sensors:** Android accelerometer (for manual mode)

## Screens

- Device discovery and pairing
- Mode choice (manual / auto / map-building)
- Live camera stream with overlaid planned path
- Manual tilt control driven by device orientation

## Companion projects

- **[thesisRaspberry](https://github.com/veparkh/thesisRaspberry)** — the vision and control side: computer vision, pathfinding, PI controller.
- **[ElectroMazeSTM](https://github.com/veparkh/ElectroMazeSTM)** — tilt-stage firmware (STM32F767 + ChibiOS). Takes angle setpoints from the host and drives the mechanics.

## Status

BSc thesis project — feature-complete and defended (2024).
