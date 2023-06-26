package cl.tiocomegfas.app.marvelcomics.util.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cl.tiocomegfas.app.marvelcomics.R

val Poppins = FontFamily(
    fonts = listOf(
        Font(R.font.poppins_thin, FontWeight.Thin),
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
    )
)