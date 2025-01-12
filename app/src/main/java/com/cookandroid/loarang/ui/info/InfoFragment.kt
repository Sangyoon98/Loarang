package com.cookandroid.loarang.ui.info

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cookandroid.loarang.R
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentInfoBinding
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.textColor
import kotlin.math.ceil
import kotlin.math.floor

class InfoFragment : BaseFragment() {
    companion object {
        fun newInstance() = InfoFragment()
        const val TAG = "InfoFragment"
    }

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    lateinit var context: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        binding.fragmentAuction.auctionNumberOfPeople.editText?.addTextChangedListener(auctionLoss)
        binding.fragmentAuction.auctionLowestPrice.editText?.addTextChangedListener(auctionLoss)
        binding.fragmentFee.feeAmountReceived.editText?.addTextChangedListener(transactionFee)

        return binding.root
    }

    private val auctionLoss: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (binding.fragmentAuction.auctionNumberOfPeople.editText?.length()!! > 0 && binding.fragmentAuction.auctionLowestPrice.editText?.length()!! > 0) {
                val doubleCount = binding.fragmentAuction.auctionNumberOfPeople.editText?.text.toString().toDouble()
                val doubleExchange = binding.fragmentAuction.auctionLowestPrice.editText?.text.toString().toDouble()
                val doubleBreakEvenPoint =
                    floor((doubleExchange - ceil(doubleExchange / 20)) * (doubleCount - 1) / doubleCount)
                val doubleRecommendPrice = floor(doubleBreakEvenPoint * 0.91)

                binding.fragmentAuction.auctionBreakEvenPoint.text = doubleBreakEvenPoint.toString()
                binding.fragmentAuction.auctionRecommendedBidAmount.text = doubleRecommendPrice.toString()
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }

    private val transactionFee: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (binding.fragmentFee.feeAmountReceived.editText?.length()!! > 0) {
                val doublePrice = binding.fragmentFee.feeAmountReceived.editText?.text.toString().toDouble()
                val doubleFormatSendPrice = ceil(doublePrice * 1.0526315789)

                binding.fragmentFee.feeAmountToSend.text = doubleFormatSendPrice.toString()
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }
}

@Composable
private fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = context.getString(R.string.info_auction_title),
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = numberOfPeople, label = "Number of People") {
            numberOfPeople = it
            calculateAuctionValues(numberOfPeople.text, lowestPrice.text) { breakEven, recommend ->
                breakEvenPoint = breakEven
                recommendedBid = recommend
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = lowestPrice, label = "Lowest Price") {
            lowestPrice = it
            calculateAuctionValues(numberOfPeople.text, lowestPrice.text) { breakEven, recommend ->
                breakEvenPoint = breakEven
                recommendedBid = recommend
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Break-even Point: $breakEvenPoint")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Recommended Bid: $recommendedBid")
    }
}

@Composable
private fun FeeInfoScreen() {
    val context = LocalContext.current
    var amountReceived by remember { mutableStateOf(TextFieldValue()) }
    var amountToSend by remember { mutableStateOf("0") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = context.getString(R.string.info_fee_title),
            style = AppTypography.titleLarge,
            color = MaterialTheme.colorScheme.textColor
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(value = amountReceived, label = "Amount Received") {
            amountReceived = it
            calculateFee(amountReceived.text) { sendAmount ->
                amountToSend = sendAmount
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Amount to Send: $amountToSend")
    }
}

@Composable
private fun CustomTextField(value: TextFieldValue, label: String, onValueChange: (TextFieldValue) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth()
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
        InfoScreen()
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