package dev.mcd.calendar.feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
//import com.example.calendarbyourselvesdacs3.presentation.navigation.NavGraph
//import com.example.calendarbyourselvesdacs3.presentation.sign_in.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.calendar.feature.presentation.routing.Routing
import dev.mcd.calendar.ui.theme.CalendarTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Routing(navController = navController)

//                    NavGraph(viewModel = viewModel, context = applicationContext)

                }
            }
        }
    }
}
