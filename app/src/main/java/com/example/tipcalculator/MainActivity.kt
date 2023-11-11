package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.Components.InputField
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import com.example.tipcalculator.ui.theme.lightBg
import com.example.tipcalculator.util.calculateTotalPerPerson
import com.example.tipcalculator.util.totalTip
import com.example.tipcalculator.widgets.RoundIconButton
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {

                Surface(modifier = Modifier.fillMaxSize()) {
                    MainContent()

                }

            }
        }
    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {

    TipCalculatorTheme {

        Surface(
            color = MaterialTheme.colorScheme.onBackground
        ) {
            content()

        }
    }
}

@Preview
@Composable
fun TopHeader(totalPerPerson : Double = 0.0) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)
        .height(150.dp)
        .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp))),
        color = Color(0xFF22356F)
    ) {

        Column(modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            val total = "%.2f".format(totalPerPerson)

            Text(text = "Total per person",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )


            Text(text = "₹ $total",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.White)
        }
    }
}

@Preview
@Composable
fun MainContent() {
    Column(modifier = Modifier.padding(all = 12.dp) ) {
        BillForm {}
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier= Modifier,
             onValChange: (String) -> Unit={}) {

    val roundoff = remember {
        mutableStateOf(0.0f)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    val split = remember {
        mutableStateOf(1)
    }
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val sliderpositionState = remember {
        mutableStateOf(0f)
    }
    val totalBill =  remember {
        mutableStateOf("")
    }
    val validState = remember (totalBill.value) {
        totalBill.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            InputField(valueState = totalBill,
                labelId = "Enter Bill Amount",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState)
                        return@KeyboardActions
                    onValChange(totalBill.value.trim())
                    keyboardController?.hide()
                }
            )
            if(validState) {
                Row(modifier = Modifier.padding((3.dp)),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Split",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {

                        RoundIconButton(imageVector = Icons.Default.Remove ,
                            onClickAction = {
                                if (split.value > 1)
                                {
                                    split.value -=1

                                }
                                totalPerPersonState.value =
                                    calculateTotalPerPerson(totalBill = totalBill.value.toDouble(),
                                        splitBy = split.value,
                                        tipPercentage = roundoff.value.toDouble())
                            })

                        Text(text = split.value.toString(),
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 4.dp))

                        RoundIconButton(imageVector = Icons.Default.Add,
                            onClickAction = {
                                split.value +=1
                                totalPerPersonState.value =
                                    calculateTotalPerPerson(totalBill = totalBill.value.toDouble(),
                                        splitBy = split.value,
                                        tipPercentage = roundoff.value.toDouble())
                            })

                    }
                }
                
                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                    
                    Text(text = "Tip",
                        modifier = Modifier.align(Alignment.CenterVertically))
                    
                    Spacer(modifier = Modifier.width(200.dp))

                    Text(text = "₹ ${roundoff.value}",
                        modifier = Modifier.align(Alignment.CenterVertically))
                }

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = (sliderpositionState.value*100).toInt().toString()+"%")
                    
                    Spacer(modifier = Modifier.height(14.dp))

                    Slider(value = sliderpositionState.value , onValueChange = { newVal ->
                        sliderpositionState.value = newVal
                        tipAmountState.value =
                            totalTip(totalBill =totalBill.value.toDouble(), tipPercentage =  ((sliderpositionState.value*100).toDouble()))
                        roundoff.value  = ((tipAmountState.value * 100.0).roundToInt() / 100.0).toFloat()

                        totalPerPersonState.value =
                            calculateTotalPerPerson(totalBill = totalBill.value.toDouble(),
                                splitBy = split.value,
                                tipPercentage = (sliderpositionState.value*100).toDouble())

                    },
                        modifier = Modifier.padding(start = 20.dp,end = 20.dp),
                        steps = 5,
                        onValueChangeFinished = {

                        })
                }
            }
        }

    }
}


