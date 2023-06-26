package cl.tiocomegfas.app.marvelcomics.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import cl.tiocomegfas.app.marvelcomics.R
import cl.tiocomegfas.app.marvelcomics.util.composables.CardBitmapItemList
import cl.tiocomegfas.app.marvelcomics.util.composables.CardItemList
import cl.tiocomegfas.app.marvelcomics.util.composables.LoadingDialog
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    if(uiState.isLoading) {
        LoadingDialog(lottie = R.raw.lottie_loading, message = "Obteniendo datos...")
    } else {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            when(val result = uiState.characters) {
                is HomeUiState.GetAllCharactersAction.InitialState -> {}
                is HomeUiState.GetAllCharactersAction.Success -> {
                    LazyColumn {
                        items(
                            count = result.mapper.characters.size,
                            key = { result.mapper.characters[it].id }
                        ) {index ->
                            if(result.mapper.characters[index].bitmap != null) {
                                CardBitmapItemList(
                                    modifier = Modifier.fillMaxWidth(),
                                    bitmap = result.mapper.characters[index].bitmap!!,
                                    title = result.mapper.characters[index].name,
                                    subtitle = "Tiene ${result.mapper.characters[index].comics} comics y ${result.mapper.characters[index].series} series",
                                ){}
                            } else {
                                onEvent(HomeEvent.GetThumbnail(result.mapper.characters[index].id))
                                CardItemList(
                                    modifier = Modifier.fillMaxWidth(),
                                    icon = R.drawable.marvel_logo_0,
                                    title = result.mapper.characters[index].name,
                                    subtitle = "Tiene ${result.mapper.characters[index].comics} comics y ${result.mapper.characters[index].series} series",
                                ) {
                                    Log.e("TAG", "Is clicked item")
                                }
                            }
                            Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))
                        }
                    }
                }
                is HomeUiState.GetAllCharactersAction.Empty -> {

                }
                is HomeUiState.GetAllCharactersAction.Error -> {
                }
            }
        }
    }
}