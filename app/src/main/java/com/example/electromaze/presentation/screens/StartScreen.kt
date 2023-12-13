package com.example.electromaze.presentation.screens

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.electromaze.R
import com.example.electromaze.presentation.screens.events.StartScreenEvents
import com.example.electromaze.ui.theme.CardColor
import com.example.electromaze.ui.theme.textColor

@SuppressLint("MissingPermission")//TODO сделать проверку на разрешение BLUETOOTH_CONNECT
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenConnect(isBluetoothEnabled: Boolean, pairedDevices:Set<BluetoothDevice>, scannedDevices:Set<BluetoothDevice>, startScreenEvent:(StartScreenEvents)->Unit, onClickBluetoothIcon:()->Unit) {
    Log.d("TAG", "screenConnect: Recomposed")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "ElectroMaze", color = textColor) },colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = CardColor),
                actions = {
                    IconButton(
                        onClick = {
                            onClickBluetoothIcon()
                            Log.d("d", "screenConnect: ")
                        }) {
                        Icon(
                            painter = painterResource(
                                id = if (isBluetoothEnabled) {
                                    R.drawable.baseline_bluetooth_24
                                } else {
                                    R.drawable.baseline_bluetooth_disabled_24})
                            , contentDescription = "blue_status", tint = textColor
                        )
                    }
                }
            )
        }) {
        Column(
            Modifier.fillMaxSize().background(CardColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(it.calculateTopPadding()))
            Text(
                text = "Сопряженные устройства",
                Modifier.padding(top = 6.dp, bottom = 6.dp),
                color = textColor
            )
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f),
                ) {
                items(pairedDevices.size) { i->
                    ListItem(pairedDevices.elementAt(i).name,pairedDevices.elementAt(i).address){
                        startScreenEvent(StartScreenEvents.onDeviceClick(pairedDevices.elementAt(i)))
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Добавить устройство", color = textColor)
                IconButton(onClick = {
                    startScreenEvent(StartScreenEvents.onSeacrhIconClick)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_manage_search_24),
                        contentDescription = "search for conn",
                        Modifier.size(30.dp),
                        tint = textColor
                    )
                }
            }
            LazyColumn(
                Modifier
                    .fillMaxSize()

            ) {
                items(scannedDevices.size){ i->
                    ListItem(name = if(scannedDevices.elementAt(i).name!=null)
                    {scannedDevices.elementAt(i).name}
                    else{"Noname"}
                        , mac = scannedDevices.elementAt(i).address){
                        startScreenEvent(StartScreenEvents.onDeviceClick(scannedDevices.elementAt(i)))
                    }
                }
            }
        }
    }
}