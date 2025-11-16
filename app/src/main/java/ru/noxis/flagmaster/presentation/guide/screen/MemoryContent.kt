package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.flagmaster.R
import ru.noxis.flagmaster.presentation.guide.event.FlagMasterUiEvent
import ru.noxis.flagmaster.presentation.guide.state.TypeAnswer
import ru.noxis.flagmaster.presentation.guide.viewmodel.CountriesViewModel

@Composable
fun MemoryContent(
    modifier: Modifier = Modifier
) {
    val viewModel: CountriesViewModel = hiltViewModel()
    val stateUi by viewModel.uiState.collectAsState()

    LaunchedEffect(true) {
        viewModel.onUIEvent(FlagMasterUiEvent.StartGame)
    }

    Box(
        modifier = modifier
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
                        .size(width = 204.dp, height = 140.dp)
                        .border(1.dp, color = textColor, shape = RectangleShape),
                    painter = countryRandom.flagIcon.asPainter(),
                    contentDescription = "flag icon"
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.whose_flag),
                    fontSize = 18.sp,
                    color = textColor
                )
                Spacer(Modifier.height(16.dp))
                val colorAnswer = when (stateUi.answerCheck) {
                    TypeAnswer.NotAnswer -> Color.Unspecified
                    TypeAnswer.OnAnswer -> {
                        if (stateUi.answerIndex == stateUi.randomIndex) {
                            Color(0xFF4CAF50)
                        } else {
                            Color(0xFFF44336)
                        }
                    }
                }

                countries.forEachIndexed { index, country ->
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                            disabledContainerColor = when {
                                stateUi.answerIndex == index -> colorAnswer
                                stateUi.randomIndex == index -> Color(0xFF4CAF50)
                                else -> Color.Unspecified
                            }
                        ),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp),
                        enabled = stateUi.answerCheck == TypeAnswer.NotAnswer,
                        onClick = {
                            viewModel.onUIEvent(FlagMasterUiEvent.OnCheckAnswer(index))
                        }
                    ) {
                        Text(
                            text = country.nameCountry.asString(),
                            color = textColor,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }

                Spacer(Modifier.height(32.dp))
                if (stateUi.answerCheck != TypeAnswer.NotAnswer) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 64.dp),
                        contentPadding = PaddingValues(16.dp),
                        onClick = {
                            viewModel.onUIEvent(FlagMasterUiEvent.StartGame)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.btn_next),
                            color = textColor,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
