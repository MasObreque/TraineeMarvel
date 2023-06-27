package cl.tiocomegfas.app.marvelcomics.util.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.tiocomegfas.app.marvelcomics.util.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemList(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            1.dp,
            Color.Black
        )
    ) {
        Row {
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    modifier = Modifier
                        .height(56.dp)
                        .width(56.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = title,
                    textAlign = TextAlign.Center,
                    color = if(isSystemInDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF0A0615),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                )
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = subtitle,
                    textAlign = TextAlign.Center,
                    color = if(isSystemInDarkTheme()) Color(0xFFF1F4F8) else Color(0xFF404B52),
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.width(33.dp))
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ChevronRight,
                    tint = if(isSystemInDarkTheme()) Color.White else Color.Black,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}