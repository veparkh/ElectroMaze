package com.example.electromaze.presentation.screens

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.electromaze.ui.theme.CardColor
import com.example.electromaze.ui.theme.textColor

@Composable
fun ListItem(name:String,mac:String,onDeviceClick:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onDeviceClick()
            }
            .padding(start = 8.dp, bottom = 5.dp, end = 8.dp,top = 5.dp),
        shape = RoundedCornerShape(5.dp), colors = CardDefaults.cardColors(containerColor = CardColor)

    ) {

            Column(
                Modifier.padding(start = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = name,color = textColor)
                Text(text = mac,color = textColor)
            }
    }
}