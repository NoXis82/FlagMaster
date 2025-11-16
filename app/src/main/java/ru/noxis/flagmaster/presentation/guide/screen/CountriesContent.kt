package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.core.domain.CountryInfo
import ru.noxis.core.domain.enum.Сountries
import ru.noxis.core.util.asUiPainter
import ru.noxis.core.util.asUiTextCapitalName
import ru.noxis.core.util.asUiTextCountryName
import ru.noxis.flagmaster.R
import ru.noxis.flagmaster.presentation.guide.components.CountryCard
import ru.noxis.flagmaster.presentation.guide.components.CustomOutlinedTextField
import ru.noxis.flagmaster.presentation.guide.event.FlagMasterUiEvent
import ru.noxis.flagmaster.presentation.guide.state.FlagMasterUiState
import ru.noxis.flagmaster.presentation.guide.viewmodel.CountriesViewModel
import ru.noxis.flagmaster.ui.theme.FlagMasterTheme

@Composable
fun CountriesContent(modifier: Modifier = Modifier) {
    val viewModel: CountriesViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsState()
    val stateUi by viewModel.uiState.collectAsState()

    CountriesBox(
        modifier = modifier,
        stateUi = stateUi,
        countries = countries,
        onUIEvent = { event ->
            viewModel.onUIEvent(event)
        }
    )
}

@Composable
private fun CountriesBox(
    stateUi: FlagMasterUiState,
    countries: List<CountryInfo>,
    modifier: Modifier = Modifier,
    onUIEvent: (FlagMasterUiEvent) -> Unit
) {
    Box(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CustomOutlinedTextField(
                value = stateUi.searchText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onValueChange = {
                    onUIEvent(FlagMasterUiEvent.OnSearchTextChange(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (stateUi.isSearching) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (countries.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.nothing_found),
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        items(countries, key = { it.code }) { country ->
                            CountryCard(country)
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CountriesContentPreview() {
    val stateUi = FlagMasterUiState(searchText = "searchText")
    val countries = listOf(
        CountryInfo(
            flagIcon = Сountries.CF.asUiPainter(),
            nameCountry = Сountries.CF.asUiTextCountryName(),
            capitalCountry = Сountries.CF.asUiTextCapitalName(),
            code = Сountries.CF.name
        )
    )
    FlagMasterTheme {
        CountriesBox(
            stateUi = stateUi,
            countries = countries,
            onUIEvent = {}
        )
    }
}