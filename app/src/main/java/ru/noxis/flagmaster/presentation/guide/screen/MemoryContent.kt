package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.flagmaster.presentation.guide.event.FlagMasterUiEvent
import ru.noxis.flagmaster.presentation.guide.state.TypeAnswer
import ru.noxis.flagmaster.presentation.guide.viewmodel.CountriesViewModel

@Composable
fun MemoryContent(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewModel: CountriesViewModel = hiltViewModel()
    val stateUi by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        viewModel.onUIEvent(FlagMasterUiEvent.StartGame)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val countries = stateUi.randomElements
            if (countries.isNotEmpty()) {
                val countryRandom = countries[stateUi.randomIndex]
                val textColor = LocalContentColor.current
                Image(
                    modifier = Modifier
                        .size(width = 102.dp, height = 70.dp)
                        .border(1.dp, color = textColor, shape = RectangleShape),
                    painter = countryRandom.flagIcon.asPainter(),
                    contentDescription = null
                )

                val colorAnswer = when(stateUi.answerCheck) {
                    TypeAnswer.NotAnswer -> Color.Unspecified
                    TypeAnswer.OnAnswer -> {
                        if (stateUi.answerIndex == stateUi.randomIndex) Color.Green else Color.Red
                    }
                }

                countries.forEachIndexed { index, country ->
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = when {
                                stateUi.answerIndex == index -> colorAnswer
                                stateUi.randomIndex == index -> Color.Green
                                else -> Color.Unspecified
                            }
                        ),
                        enabled = stateUi.answerCheck == TypeAnswer.NotAnswer,
                        onClick = {
                            viewModel.onUIEvent(FlagMasterUiEvent.OnCheckAnswer(index))
                        }
                    ) {
                        Text(country.nameCountry.asString(), color = Color.Black)
                    }
                    Spacer(Modifier.height(4.dp))
                }

                if (stateUi.answerCheck != TypeAnswer.NotAnswer) {
                    Button(onClick = {
                        viewModel.onUIEvent(FlagMasterUiEvent.StartGame)
                    }
                    ) {
                        Text("Новое")
                    }
                }
            }
        }
    }
}
