package com.cookandroid.loarang.ui.info

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentInfoBinding
import com.cookandroid.loarang.ui.MainActivity
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

        binding.count.addTextChangedListener(auctionLoss)
        binding.exchange.addTextChangedListener(auctionLoss)
        binding.price.addTextChangedListener(transactionFee)

        return binding.root
    }

    private val auctionLoss: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (binding.count.length() > 0 && binding.exchange.length() > 0) {
                val doubleCount = binding.count.text.toString().toDouble()
                val doubleExchange = binding.exchange.text.toString().toDouble()
                val doubleBreakEvenPoint =
                    floor((doubleExchange - ceil(doubleExchange / 20)) * (doubleCount - 1) / doubleCount)
                val doubleRecommendPrice = floor(doubleBreakEvenPoint * 0.91)

                binding.breakEvenPoint.text = doubleBreakEvenPoint.toString()
                binding.recommendPrice.text = doubleRecommendPrice.toString()
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }

    private val transactionFee: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (binding.price.length() > 0) {
                val doublePrice = binding.price.text.toString().toDouble()
                val doubleFormatSendPrice = ceil(doublePrice * 1.0526315789)

                binding.formatSendPrice.text = doubleFormatSendPrice.toString()
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }
}