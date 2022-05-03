package com.example.blockchain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentHoldings : Fragment() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmentholdings_layout, container, false)
    }

    fun onClickBuy() {
        val buyBTN = requireView().findViewById<Button>(R.id.holdingBuy)
        buyBTN?.setOnClickListener({
            val usdView = view?.findViewById<TextView>(R.id.holdingUSD)
            usdView?.text = "$100"
        })
    }

}