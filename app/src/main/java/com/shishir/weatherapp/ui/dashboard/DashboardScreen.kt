package com.shishir.weatherapp.ui.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shishir.weatherapp.R
import com.shishir.weatherapp.model.ForecastModel
import com.shishir.weatherapp.model.WeatherModel
import com.shishir.weatherapp.ui.dashboard.components.ForecastComponent
import com.shishir.weatherapp.utils.DateUtil.toFormattedDate
import kotlin.random.Random

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val searchBarState by viewModel.searchBarState
    val searchTextState by viewModel.searchTextState
    val uiState: DashboardUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            WeatherTopAppBar(
                searchBarState = searchBarState,
                searchTextState = searchTextState,
                onTextChange = { viewModel.updateSearchTextState(it) },
                onCloseClicked = { viewModel.updateSearchBarState(SearchBarState.CLOSED) },
                onSearchClicked = { viewModel.getWeather(it) },
                onSearchTriggered = {
                    viewModel.updateSearchBarState(newValue = SearchBarState.OPENED)
                }
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                WeatherScreenContent(uiState = uiState, modifier = modifier, viewModel = viewModel)
            }
        },
    )
}

@Composable
fun WeatherScreenContent(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel?,
) {
    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.testTag("ProgressIndicator")
                    .size(100.dp)
                    .align(Alignment.Center))
            }
        }

        uiState.errorMessage.isNotEmpty() -> {
            WeatherErrorState(uiState = uiState, viewModel = viewModel)
        }

        else -> {
            WeatherSuccessState(modifier = modifier, uiState = uiState)
        }
    }
}

@Composable
private fun WeatherErrorState(
    modifier: Modifier = Modifier,
    uiState: DashboardUiState,
    viewModel: DashboardViewModel?,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(modifier = Modifier
            .wrapContentSize()
            .align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                modifier = modifier
                    .weight(2f)
                    .alpha(0.5f)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                text = "Something went wrong: ${uiState.errorMessage}",
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = { viewModel?.getWeather() }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Retry",
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.retry),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun WeatherSuccessState(
    modifier: Modifier,
    uiState: DashboardUiState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = uiState.weatherModel?.name.orEmpty(),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = uiState.weatherModel?.date?.toFormattedDate().orEmpty(),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = stringResource(
                R.string.temperature_value_in_celsius,
                uiState.weatherModel?.temperature.toString()
            ),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            text = uiState.weatherModel?.condition?:"",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = stringResource(
                R.string.feels_like_temperature_in_celsius,
                uiState.weatherModel?.feelsLike.toString()
            ),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(R.string.wind_speed_label),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uiState.weatherModel?.wind.toString().plus(" ").plus(stringResource(id = R.string.wind_speed_unit)),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(R.string.humidity_label),
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uiState.weatherModel?.humidity.toString().plus(stringResource(id = R.string.humidity_unit)),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.forecast),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 8.dp, start = 16.dp),
        ) {
            uiState.weatherModel?.let { weather ->
                itemsIndexed(weather.forecasts) { index, forecast ->
                    if (index == 0){
                        return@itemsIndexed
                    }
                    ForecastComponent(
                        date = forecast.date,
                        minTemp = stringResource(
                            R.string.temperature_value_in_celsius,
                            forecast.minTemp
                        ),
                        maxTemp = stringResource(
                            R.string.temperature_value_in_celsius,
                            forecast.maxTemp,
                        ),
                        condition = forecast.condition
                    )
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherScreenContentPreview() {
    val forecasts = mutableListOf<ForecastModel>()
    for (i in 0..9) {
        forecasts.add(
            ForecastModel(
                date = "2023-10-02",
                maxTemp = "${Random.nextInt(18, 21)}",
                minTemp = "${Random.nextInt(10, 15)}",
                condition = "Rainy"
                )
        )
    }
    WeatherScreenContent(
        DashboardUiState(
            WeatherModel(
                temperature = 19,
                date = "Oct 7",
                wind = 22,
                humidity = 35,
                feelsLike = 18,
                condition = "Cloudy",
                name = "Munich",
                forecasts = forecasts,
            ),
        ), viewModel = null
    )
}

@Composable
fun WeatherTopAppBar(
    searchBarState: SearchBarState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchBarState) {
        SearchBarState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }

        SearchBarState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        modifier = Modifier.testTag("WeatherTopAppBar"),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search_icon),
                )
            }
        }
    )
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = MaterialTheme.colorScheme.primary,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().testTag("SearchTextField"),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = stringResource(R.string.search_hint),
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.7f),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon),
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_icon),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    keyboardController?.hide()
                },
            ),
        )
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Search for a city",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}