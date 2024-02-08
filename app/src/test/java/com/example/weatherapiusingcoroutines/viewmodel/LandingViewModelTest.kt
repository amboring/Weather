package com.example.weatherapiusingcoroutines.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherapiusingcoroutines.models.response.Main
import com.example.weatherapiusingcoroutines.models.response.Weather
import com.example.weatherapiusingcoroutines.models.state.LandingWeather
import com.example.weatherapiusingcoroutines.models.state.WeatherState
import com.example.weatherapiusingcoroutines.service.Repository
import com.example.weatherapiusingcoroutines.util.DatastoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class LandingViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var datastoreManager: DatastoreManager


    private lateinit var viewModel: LandingViewModel
    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    @Mock
    private lateinit var mockObserver: Observer<WeatherState>
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = LandingViewModel(repository, datastoreManager)
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun wrapUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN getDefaultLocationWeather AND obtained valid data THEN weather state is updated with data`() =
        runTest(testDispatcher) {
            Mockito.`when`(repository.getLatitudeWeather(0.0, 0.0))
                .thenReturn(getMockedLandingWeather())

            viewModel.getDefaultLocationWeather(0.0, 0.0)
            viewModel.weatherLiveData.observeForever(mockObserver)

            verify(mockObserver).onChanged(WeatherState.Loading)
            verify(mockObserver).onChanged(WeatherState.HasWeather(getMockedLandingWeather()))

            viewModel.weatherLiveData.removeObserver(mockObserver)
        }
    @Test
    fun `WHEN getDefaultLocationName AND no data THEN weather state is updated with error`() =
        runTest(testDispatcher) {
            Mockito.`when`(repository.getLatitudeWeather(0.0, 0.0))
                .thenReturn(null)


            viewModel.getDefaultLocationWeather(0.0, 0.0)
            viewModel.weatherLiveData.observeForever(mockObserver)

            verify(mockObserver).onChanged(WeatherState.Loading)
            verify(mockObserver).onChanged(WeatherState.Error("No data found."))

            viewModel.weatherLiveData.removeObserver(mockObserver)
        }

    private fun getMockedLandingWeather() = LandingWeather(
        "New York",
        listOf(Weather("clear sky", "01d", 800, "Clear")),
        Main(270.83, 62, 1028, 275.06, 277.02, 272.66)
    )
}