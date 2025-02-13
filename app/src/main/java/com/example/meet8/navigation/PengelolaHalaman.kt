package com.example.meet8.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meet8.ui.view.screen.MahasiswaFormView
import com.example.meet8.ui.view.screen.MataKuliahView
import com.example.meet8.ui.view.screen.MataKuliahView
import com.example.meet8.ui.view.screen.SplashView
import com.example.meet8.ui.view.screen.MataKuliahView
import com.example.meet8.ui.view.screen.TampilView
import com.example.meet8.ui.view.viewmodel.MahasiswaViewModel
import com.example.meet8.ui.view.viewmodel.RencanaStudyViewModel

enum class Halaman {
    Splash,
    Mahasiswa,
    Matakuliah,
    Tampil
}

@Composable
fun MahasiswaApp(
    modifier: Modifier = Modifier,
    mahasiswaViewModel: MahasiswaViewModel = viewModel(),
    krsViewModel: RencanaStudyViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val mahasiswaUiState = mahasiswaViewModel.mahasiswaUiState.collectAsState().value
    val rencanaUiState = krsViewModel.krsStateUi.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Halaman.Splash.name,
        modifier = Modifier.padding()
    ) {
        composable(route = Halaman.Splash.name) {
            SplashView(onMulaiButton = {
                navController.navigate(Halaman.Mahasiswa.name)
            })
        }
        composable(route = Halaman.Mahasiswa.name) {
            MahasiswaFormView(
                onSubmitButtonClicked = {
                    mahasiswaViewModel.saveDataMahasiswa(it)
                    navController.navigate(Halaman.Matakuliah.name)
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Halaman.Matakuliah.name) {
            MataKuliahView(
                mahasiswa = mahasiswaUiState,
                onSubmitButtonClicked = { krsViewModel.saveDataKRS(it)
                    navController.navigate(Halaman.Tampil.name) },
                onBackButtonClicked = { navController.popBackStack() }
            )
        }
        composable(route = Halaman.Tampil.name){
            TampilView(
                mahasiswaUiState,
                rencanaUiState,
                onBackButtonClicked = {navController.popBackStack()},
                onResetButtonClicked = {navController.navigate(Halaman.Splash.name)}
            )
        }
    }
}
