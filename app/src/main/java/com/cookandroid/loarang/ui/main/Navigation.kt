package com.cookandroid.loarang.ui.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cookandroid.loarang.R
import com.cookandroid.loarang.ui.character.CharacterScreen
import com.cookandroid.loarang.ui.homework.HomeworkScreen
import com.cookandroid.loarang.ui.info.InfoScreen
import com.cookandroid.loarang.ui.schedule.ScheduleScreen
import com.cookandroid.loarang.ui.schedule.ScheduleViewModel
import com.cookandroid.loarang.ui.setting.SettingScreen
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.mainGreen
import com.cookandroid.loarang.util.Const.CHARACTER
import com.cookandroid.loarang.util.Const.HOMEWORK
import com.cookandroid.loarang.util.Const.INFORMATION
import com.cookandroid.loarang.util.Const.SCHEDULE
import com.cookandroid.loarang.util.Const.SETTING

// Navigation Host
@Composable
fun MainNavigationView(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Character.screenRoute
    ) {
        composable(NavigationItem.Character.screenRoute) {
            val viewModel: MainViewModel = hiltViewModel()
            CharacterScreen(name = stringResource(id = NavigationItem.Character.title), viewModel = viewModel)
        }
        composable(NavigationItem.Homework.screenRoute) {
            val viewModel: MainViewModel = hiltViewModel()
            HomeworkScreen(name = stringResource(id = NavigationItem.Homework.title), viewModel = viewModel)
        }
        composable(NavigationItem.Schedule.screenRoute) {
            val viewModel: ScheduleViewModel = hiltViewModel()
            ScheduleScreen(name = stringResource(id = NavigationItem.Schedule.title), viewModel = viewModel)
        }
        composable(NavigationItem.Information.screenRoute) {
            val viewModel: MainViewModel = hiltViewModel()
            InfoScreen(name = stringResource(id = NavigationItem.Information.title), viewModel = viewModel)
        }
        composable(NavigationItem.Setting.screenRoute) {
            val viewModel: MainViewModel = hiltViewModel()
            SettingScreen(name = stringResource(id = NavigationItem.Setting.title), viewModel = viewModel)
        }
    }
}

// Navigation Bar
@Composable
fun MainNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Character,
        NavigationItem.Homework,
        NavigationItem.Schedule,
        NavigationItem.Information,
        NavigationItem.Setting
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.backgroundListItem,
        contentColor = MaterialTheme.colorScheme.mainGreen
    ) {
        items.forEach { item ->
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            NavigationBarItem(
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = {
                    Text(stringResource(id = item.title), fontSize = 9.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.mainGreen
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainNavigationPreview() {
    AppTheme {
        MainNavigationBar(navController = rememberNavController())
    }
}

// Navigation Routes
sealed class NavigationItem(val title: Int, val icon: Int, val screenRoute: String) {
    data object Character : NavigationItem(R.string.tab_character, R.drawable.icon_character, CHARACTER)

    data object Homework : NavigationItem(R.string.tab_homework, R.drawable.icon_homework, HOMEWORK)

    data object Schedule : NavigationItem(R.string.tab_calender, R.drawable.icon_calendar, SCHEDULE)

    data object Information : NavigationItem(R.string.tab_info, R.drawable.icon_info, INFORMATION)

    data object Setting : NavigationItem(R.string.tab_setting, R.drawable.icon_setting, SETTING)
}