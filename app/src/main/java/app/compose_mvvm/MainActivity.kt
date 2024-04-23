package app.compose_mvvm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.compose_mvvm.ui.theme.JetpackComposeMvvmTheme
import app.compose_mvvm.ui.theme.Purple500
import app.compose_mvvm.utils.Resource
import app.compose_mvvm.view.UserListItem
import app.compose_mvvm.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeMvvmTheme {
                NetworkCall()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun NetworkCall(
    viewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val getAllUserData = viewModel.getUserData.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Fetch User Data",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                    )
                },
                backgroundColor = Purple500,
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    LaunchedEffect(Unit) {
                        val result = viewModel.fetchUserData()

                        if (result is Resource.Success) {
                            Toast.makeText(
                                context,
                                "Data Fetched Successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (result is Resource.Error) {
                            Toast.makeText(
                                context,
                                "Error: ${result.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    if (!viewModel.isLoading.value) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    if (viewModel.isLoading.value) {
                        if (viewModel.getUserData.value!!.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                items(getAllUserData.value!!.size) { index: Int ->
                                    UserListItem(getAllUserData.value!![index])
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}