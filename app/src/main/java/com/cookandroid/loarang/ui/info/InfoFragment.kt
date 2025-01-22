package com.cookandroid.loarang.ui.info

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cookandroid.loarang.R
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.textColor
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun InfoScreen(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.backgroundGrey)
            .verticalScroll(rememberScrollState())
    ) {
        ActionInfoScreen()
        Spacer(modifier = Modifier.height(16.dp))
        FeeInfoScreen()
    }
}

@Composable
private fun ActionInfoScreen() {
    val context = LocalContext.current
    var numberOfPeople by remember { mutableStateOf(TextFieldValue()) }
    var lowestPrice by remember { mutableStateOf(TextFieldValue()) }
    var breakEvenPoint by remember { mutableStateOf("0") }
    var recommendedBid by remember { mutableStateOf("0") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(12.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = context.getString(R.string.info_auction_title),
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.textColor
        )
        CustomTextField(value = numberOfPeople, label = context.getString(R.string.info_auction_number_of_people_title)) {
            numberOfPeople = it
            calculateAuctionValues(numberOfPeople.text, lowestPrice.text) { breakEven, recommend ->
                breakEvenPoint = breakEven
                recommendedBid = recommend
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = lowestPrice, label = context.getString(R.string.info_auction_lowest_price_title)) {
            lowestPrice = it
            calculateAuctionValues(numberOfPeople.text, lowestPrice.text) { breakEven, recommend ->
                breakEvenPoint = breakEven
                recommendedBid = recommend
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = context.getString(R.string.info_auction_recommended_bid_amount_title) + " : $breakEvenPoint",
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.textColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = context.getString(R.string.info_auction_break_even_point_title) + " : $recommendedBid",
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.textColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun FeeInfoScreen() {
    val context = LocalContext.current
    var amountReceived by remember { mutableStateOf(TextFieldValue()) }
    var amountToSend by remember { mutableStateOf("0") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(12.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = context.getString(R.string.info_fee_title),
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.textColor
        )
        CustomTextField(value = amountReceived, label = context.getString(R.string.info_fee_amount_received_title)) {
            amountReceived = it
            calculateFee(amountReceived.text) { sendAmount ->
                amountToSend = sendAmount
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = context.getString(R.string.info_fee_amount_to_send_title) + " : $amountToSend",
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.textColor,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CustomTextField(value: TextFieldValue, label: String, onValueChange: (TextFieldValue) -> Unit) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.text.all { it.isDigit()}) {
                onValueChange(newValue)
            }
        },
        label = { Text(
            text = label,
            style = AppTypography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.textColor
        ) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}

private fun calculateAuctionValues(
    numberOfPeople: String,
    lowestPrice: String,
    onResult: (String, String) -> Unit
) {
    if (numberOfPeople.isNotEmpty() && lowestPrice.isNotEmpty()) {
        val doubleCount = numberOfPeople.toDoubleOrNull() ?: return
        val doubleExchange = lowestPrice.toDoubleOrNull() ?: return
        val doubleBreakEvenPoint = floor((doubleExchange - ceil(doubleExchange / 20)) * (doubleCount - 1) / doubleCount)
        val doubleRecommendPrice = floor(doubleBreakEvenPoint * 0.91)
        onResult(doubleBreakEvenPoint.toString(), doubleRecommendPrice.toString())
    }
}

private fun calculateFee(amountReceived: String, onResult: (String) -> Unit) {
    if (amountReceived.isNotEmpty()) {
        val doublePrice = amountReceived.toDoubleOrNull() ?: return
        val doubleFormatSendPrice = ceil(doublePrice * 1.0526315789)
        onResult(doubleFormatSendPrice.toString())
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "SchedulePreview (Dark)")
@Composable
private fun InfoPreview() {
    AppTheme {
        InfoScreen(name = "Info")
    }
}

@Preview
@Composable
private fun ActionInfoPreview() {
    AppTheme {
        ActionInfoScreen()
    }
}

@Preview
@Composable
private fun FeeInfoPreview() {
    AppTheme {
        FeeInfoScreen()
    }
}