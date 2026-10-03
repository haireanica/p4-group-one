package p4groupone.lenditandroid.ui.login

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
fun LoginViewScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
    ) {

        // Reference screen height
        val referenceHeight = 900.dp

        // Scale everything based on available height
        val scale = (maxHeight / referenceHeight)
            .coerceIn(0.75f, 1f)

        // Scaled dimensions
        val headerHeight = 230.dp * scale
        val innerHeaderHeight = 220.dp * scale
        val logoSize = 190.dp * scale
        val headerRadius = 100.dp * scale

        val titleSize = 70.sp * scale
        val fieldLabelSize = 20.sp * scale
        val placeholderSize = 15.sp * scale
        val rememberSize = 20.sp * scale
        val buttonTextSize = 32.sp * scale
        val forgotSize = 20.sp * scale

        val fieldHeight = 60.dp * scale
        val buttonHeight = 60.dp * scale

        val horizontalPadding = 35.dp * scale
        val contentSpacing = 10.dp * scale
        val headerToTitleSpacing = 10.dp * scale

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {

                // White border/layer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = headerRadius
                            )
                        )
                        .background(Color.White)
                )

                // Purple inner layer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(innerHeaderHeight)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = headerRadius
                            )
                        )
                        .background(SecondaryColor),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(R.drawable.orlando_logo),
                        contentDescription = "Orlando City Logo",
                        modifier = Modifier
                            .size(logoSize)
                            .offset(
                                x = 0.dp,
                                y = 10.dp * scale
                            )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(headerToTitleSpacing)
            )

            // Orlando
            Text(
                text = "Orlando",
                fontSize = titleSize,
                fontFamily = openSans,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Lend IT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Lend",
                    fontSize = titleSize,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Box {

                    Text(
                        text = " IT",
                        fontSize = titleSize,
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
                        fontSize = titleSize,
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
                        fontSize = titleSize,
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
                        fontSize = titleSize,
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
                        fontSize = titleSize,
                        fontFamily = openSans,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                }
            }

            // Login content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = horizontalPadding,
                        vertical = 10.dp * scale
                    ),
                verticalArrangement = Arrangement.spacedBy(contentSpacing)
            ) {

                // Username label
                Text(
                    text = " Username",
                    fontSize = fieldLabelSize,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Username field
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = {
                        Text(
                            text = "Enter Username",
                            fontFamily = openSans,
                            fontSize = placeholderSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black.copy(alpha = 0.3f)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldHeight)
                        .clip(RoundedCornerShape(50.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    )
                )

                // Password label
                Text(
                    text = " Password",
                    fontSize = fieldLabelSize,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Password field
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "Enter Password",
                            fontFamily = openSans,
                            fontSize = placeholderSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Black.copy(alpha = 0.3f)
                        )
                    },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fieldHeight)
                        .clip(RoundedCornerShape(50.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White
                    )
                )

                // Remember Me
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.White,
                            checkmarkColor = Color.Black
                        )
                    )

                    Text(
                        text = "Remember Me",
                        fontFamily = openSans,
                        fontSize = rememberSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Log In button
                Button(
                    onClick = {
                        navController.navigate("dashboard")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryColor
                    )
                ) {

                    Text(
                        text = "Log In",
                        fontFamily = openSans,
                        fontSize = buttonTextSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Forgot Password
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier = Modifier.height(10.dp * scale)
                    )

                    Text(
                        text = "Forgot Password",
                        fontSize = forgotSize,
                        fontFamily = openSans,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            // Forgot password navigation
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewScreenPreview() {
    val navController = rememberNavController()
    LoginViewScreen(navController = navController)
}