package com.falcon.split.screens.mainNavigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import com.falcon.split.MainViewModel

@Composable
fun GroupScreen(
    onNavigate: (rootName: String) -> Unit,
    prefs: DataStore<Preferences>,
    newsViewModel: MainViewModel,
    snackBarHostState: SnackbarHostState,
    navControllerMain: NavHostController
) {
    Text("GroupScreen")
}