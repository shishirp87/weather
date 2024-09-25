package com.shishir.weatherapp


import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shishir.weatherapp.ui.dashboard.DashboardScreen
import com.shishir.weatherapp.ui.dashboard.DashboardUiState
import com.shishir.weatherapp.ui.dashboard.DashboardViewModel
import com.shishir.weatherapp.ui.dashboard.SearchBarState
import com.shishir.weatherapp.ui.dashboard.WeatherScreenContent
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    // Mock the ViewModel
    private val mockViewModel: DashboardViewModel = mock(DashboardViewModel::class.java)

    @Test
    fun dashboardScreen_displaysWeatherTopAppBar() {
        // Arrange
        `when`(mockViewModel.searchBarState).thenReturn(mutableStateOf(SearchBarState.CLOSED))
        `when`(mockViewModel.searchTextState).thenReturn(mutableStateOf(""))
        `when`(mockViewModel.uiState).thenReturn(MutableStateFlow(DashboardUiState()))

        // Act
        composeTestRule.setContent {
            DashboardScreen(viewModel = mockViewModel)
        }

        // Assert
        composeTestRule.onNodeWithTag("WeatherTopAppBar").assertExists()
    }

    @Test
    fun dashboardScreen_onTextChange_callsUpdateSearchTextState() {
        // Arrange
        val searchBarState = mutableStateOf(SearchBarState.OPENED)
        val searchTextState = mutableStateOf("")
        `when`(mockViewModel.searchBarState).thenReturn(searchBarState)
        `when`(mockViewModel.searchTextState).thenReturn(searchTextState)
        `when`(mockViewModel.uiState).thenReturn(MutableStateFlow(DashboardUiState()))

        // Act
        composeTestRule.setContent {
            DashboardScreen(viewModel = mockViewModel)
        }

        val inputText = "San Francisco"
        composeTestRule.onNodeWithTag("SearchTextField")
            .performTextInput(inputText)

        // Assert
        verify(mockViewModel).updateSearchTextState(inputText)
    }

    @Test
    fun weatherScreenContent_whenLoading_displaysProgressIndicator() {
        // Arrange
        val uiState = DashboardUiState(isLoading = true)

        // Act
        composeTestRule.setContent {
            WeatherScreenContent(uiState = uiState, viewModel = null)
        }

        // Assert
        composeTestRule.onNodeWithTag("ProgressIndicator").assertExists()
    }
}