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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.core.domain.CountryInfo
import ru.noxis.flagmaster.presentation.guide.components.CountryCard
import ru.noxis.flagmaster.presentation.guide.event.FlagMasterUiEvent
import ru.noxis.flagmaster.presentation.guide.state.FlagMasterUiState
import ru.noxis.flagmaster.presentation.guide.viewmodel.CountriesViewModel

@Composable
fun CountriesContent(modifier: Modifier = Modifier) {
    val viewModel: CountriesViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsState()
    val stateUi by viewModel.uiState.collectAsState()

    CountriesBox(
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
            TextField(
                value = stateUi.searchText,
                onValueChange = {
                    onUIEvent(FlagMasterUiEvent.OnSearchTextChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (stateUi.isSearching) {
                Box(modifier = Modifier.fillMaxSize(),
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
                        Text("НЕ НАЙДЕНО")
                    }
                } else {
                    LazyColumn(contentPadding = PaddingValues(16.dp)) {
                        items(countries, key = { it.code }) { country ->
                            CountryCard(country)
                            Spacer(Modifier.height(8.dp))
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
    val countries = emptyList<CountryInfo>()
    CountriesBox(
        stateUi = stateUi,
        countries = countries,
        onUIEvent = {}
    )
}