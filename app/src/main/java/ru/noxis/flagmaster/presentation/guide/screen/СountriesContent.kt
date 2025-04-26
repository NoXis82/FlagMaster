package ru.noxis.flagmaster.presentation.guide.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.noxis.flagmaster.presentation.guide.components.CountryCard
import ru.noxis.flagmaster.presentation.guide.viewmodel.СountriesViewModel

@Composable
fun CountriesContent() {
    val viewModel: СountriesViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsState(emptyList())

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(count = countries.size, key = { countries[it].code }) { index ->
            CountryCard(country = countries[index])
            Spacer(Modifier.height(8.dp))
        }
    }
}