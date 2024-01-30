package com.example.electromaze.presentation.screens

import android.graphics.Bitmap
import android.graphics.Point
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electromaze.data.bluetooth.Angles
import com.example.electromaze.ui.theme.CardColor
import com.example.electromaze.ui.theme.ElectroMazeTheme

@Preview(showBackground = true)
@Composable
fun ModeChoiceScreen(backButtonPressed: () -> Unit = {}, modePressed: (mode: Modes) -> Unit = {}) {
    BackHandler(enabled = true) {
        backButtonPressed()
    }
    ElectroMazeTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = CardColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ButtonMode({ modePressed(Modes.ManualControl) }, "Ручное управление")
            Spacer(modifier = Modifier.size(50.dp))
            ButtonMode({ modePressed(Modes.AutoControl) }, "Автопрохождениие")
            Spacer(modifier = Modifier.size(50.dp))
            ButtonMode({ modePressed(Modes.MapBuilding) }, "Построение карты")
        }
    }
}

@Composable
fun ButtonMode(clickEvent: () -> Unit, text: String) {
    Button(
        modifier = Modifier.fillMaxWidth(0.7f),
        onClick = { clickEvent() },
        elevation = ButtonDefaults.elevatedButtonElevation(pressedElevation = 0.dp, defaultElevation = 5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CardColor)
    ) {
        Text(text = text)
    }
}

@Composable
fun AutoModeScreen(image: State<Bitmap?>, coord:State<Point?>, backButtonPressed: () -> Unit = {}) {
    BackHandler(enabled = true) {
        backButtonPressed()
    }
    image.value?.also { it ->
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "table picture",
            //contentScale = ContentScale.Fit,
        )
        Canvas(Modifier.fillMaxSize(), onDraw = {
            //drawImage(it.asImageBitmap())
            coord.value?.let {coord->
                drawCircle(
                    color = Color.Green,
                    radius = 30F,
                    center = Offset(x = coord.x.toDp().toPx(), y = coord.y.toDp().toPx())
                )
            }

        })
    }
}

@Composable
fun ManualModeScreen(angles: State<Angles?>, backButtonPressed: () -> Unit = {}) {
    BackHandler(enabled = true) {
        backButtonPressed()
    }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Здесь пока ничего нет, но что-то обязательно появится")
        Text(text = "${angles.value?.pitch}   ${angles.value?.roll}")
    }
}

@Composable
fun MapBuildingModeScreen(backButtonPressed: () -> Unit = {}) {
    BackHandler(enabled = true) {
        backButtonPressed()
    }
}
