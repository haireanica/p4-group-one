package p4groupone.lenditandroid.ui.dashboard

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.R
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans


@Composable
fun DashboardViewScreen(navController: NavController) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryColor)
    ) {

        val referenceHeight = 900.dp
        val scale = (maxHeight / referenceHeight)
            .coerceIn(0.75f, 1f)

        val headerHeight = 280.dp * scale
        val buttonSize = 150.dp * scale
        val rowSpacing = 30.dp * scale
        val headerSpacing = 40.dp * scale

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 100.dp * scale
                            )
                        )
                        .background(Color.White)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(270.dp * scale)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 100.dp * scale
                            )
                        )
                        .background(PrimaryColor),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Orlando",
                            fontSize = 70.sp * scale,
                            fontFamily = openSans,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "Lend",
                                fontSize = 70.sp * scale,
                                fontFamily = openSans,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Box {

                                Text(
                                    text = " IT",
                                    fontSize = 70.sp * scale,
                                    fontFamily = openSans,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.offset(
                                        x = 2.dp * scale,
                                        y = 2.dp * scale
                                    )
                                )

                                Text(
                                    text = " IT",
                                    fontSize = 70.sp * scale,
                                    fontFamily = openSans,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.offset(
                                        x = -2.dp * scale,
                                        y = 2.dp * scale
                                    )
                                )

                                Text(
                                    text = " IT",
                                    fontSize = 70.sp * scale,
                                    fontFamily = openSans,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.offset(
                                        x = 2.dp * scale,
                                        y = -2.dp * scale
                                    )
                                )

                                Text(
                                    text = " IT",
                                    fontSize = 70.sp * scale,
                                    fontFamily = openSans,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.offset(
                                        x = -2.dp * scale,
                                        y = -2.dp * scale
                                    )
                                )

                                Text(
                                    text = " IT",
                                    fontSize = 70.sp * scale,
                                    fontFamily = openSans,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(headerSpacing)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                DashboardButton(
                    image = R.drawable.sign_up,
                    description = "Sign Up Button",
                    text = "Sign Up",
                    size = buttonSize,
                    scale = scale
                ) {
                    // do something
                }

                Spacer(
                    modifier = Modifier.width(rowSpacing)
                )

                DashboardButton(
                    image = R.drawable.participant,
                    description = "Participant Button",
                    text = "Participants",
                    size = buttonSize,
                    scale = scale
                ) {
                    // do something
                }
            }

            Spacer(
                modifier = Modifier.height(rowSpacing)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                DashboardButton(
                    image = R.drawable.loan,
                    description = "Loan Button",
                    text = "Loan",
                    size = buttonSize,
                    scale = scale
                ) {
                    // do something
                }

                Spacer(
                    modifier = Modifier.width(rowSpacing)
                )

                DashboardButton(
                    image = R.drawable.return_icon,
                    description = "Return Button",
                    text = "Return",
                    size = buttonSize,
                    scale = scale
                ) {
                    // do something
                }
            }

            Spacer(
                modifier = Modifier.height(rowSpacing)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                DashboardButton(
                    image = R.drawable.inventory,
                    description = "Inventory Button",
                    text = "Inventory",
                    size = buttonSize,
                    scale = scale
                ) {
                    // do something
                }

                Spacer(
                    modifier = Modifier.width(rowSpacing)
                )

                DashboardButton(
                    image = R.drawable.logout,
                    description = "Log Out Button",
                    text = "Log Out",
                    size = buttonSize,
                    scale = scale
                ) {
                    navController.navigate("login")
                }
            }
        }
    }
}


@Composable
fun DashboardButton(
    image: Int,
    description: String,
    text: String,
    size: androidx.compose.ui.unit.Dp,
    scale: Float,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(size)
            .drawBehind {

                val paint = android.graphics.Paint().apply {

                    color = Color.Black
                        .copy(alpha = 0.5f)
                        .toArgb()

                    maskFilter = BlurMaskFilter(
                        8.dp.toPx() * scale,
                        BlurMaskFilter.Blur.NORMAL
                    )
                }

                drawContext.canvas.nativeCanvas.drawRoundRect(
                    5.dp.toPx() * scale,
                    4.dp.toPx() * scale,
                    this.size.width + (5.dp.toPx() * scale),
                    this.size.height + (4.dp.toPx() * scale),
                    20.dp.toPx() * scale,
                    20.dp.toPx() * scale,
                    paint
                )
            }
            .clip(
                RoundedCornerShape(20.dp * scale)
            )
            .background(Color.White)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(image),
            contentDescription = description,
            modifier = Modifier
                .size(80.dp * scale)
                .offset(
                    y = -20.dp * scale
                )
        )

        androidx.compose.material3.Text(
            text = text,
            fontSize = 20.sp * scale,
            fontFamily = openSans,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(
                y = 40.dp * scale
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardViewScreenPreview() {

    val navController = rememberNavController()

    DashboardViewScreen(
        navController = navController
    )
}