package p4groupone.lenditandroid.ui.login

import android.R.attr.background
import android.R.id.background
import android.graphics.drawable.Drawable
import android.text.style.BackgroundColorSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants
import p4groupone.lenditandroid.R
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans

@Composable
fun LoginViewScreen() {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var rememberMe by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryColor),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 100.dp
                    )
                )
                .background(SecondaryColor),
            contentAlignment = Alignment.Center
        ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(R.drawable.orlando_logo),
                    contentDescription = "Orlando City Logo",
                    modifier = Modifier
                        .size(width = 200.dp, height = 200.dp)
                        .offset(x = 0.dp, y = 20.dp)
                )
            }
        Text(
            text = "Orlando",
            fontSize = 60.sp,
            fontFamily = openSans,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Lend",
                fontSize = 60.sp,
                fontFamily = openSans,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Box{
                Text(
                    text = " IT",
                    fontSize = 60.sp,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.offset(x = 2.dp, y = 2.dp)
                )
                Text(
                    text = " IT",
                    fontSize = 60.sp,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.offset(x = -2.dp, y = 2.dp)
                )
                Text(
                    text = " IT",
                    fontSize = 60.sp,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.offset(x = 2.dp, y = -2.dp)
                )
                Text(
                    text = " IT",
                    fontSize = 60.sp,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.offset(x = -2.dp, y = -2.dp)
                )

                Text(
                    text = " IT",
                    fontSize = 60.sp,
                    fontFamily = openSans,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
            }


        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 10.dp),
            Arrangement.spacedBy(10.dp)
        ){
            Text(
                text = " Username",
                fontSize = 20.sp,
                fontFamily = openSans,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            TextField(
                value = username,
                onValueChange = { username = it},
                placeholder = {
                    Text(
                        text = "Enter Username",
                        fontFamily = openSans,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black.copy(alpha = 0.3f),
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )

            Text(
                text = " Password",
                fontSize = 20.sp,
                fontFamily = openSans,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            TextField(
                value = password,
                onValueChange = { password = it},
                placeholder = {
                    Text(
                        text = "Enter Password",
                        fontFamily = openSans,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black.copy(alpha = 0.3f),
                    )
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )

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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryColor
                )){
                Text(
                    text = "Log In",
                    fontFamily = openSans,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))


                    Text(
                        text = "Forgot Password",
                        //fontFamily = openSans,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable{

                        }

                    )

            }
            }

        }
    }



@Preview(showBackground = true)
@Composable
fun LoginViewScreenPreview() {
    LoginViewScreen()
}