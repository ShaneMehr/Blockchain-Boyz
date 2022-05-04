package com.example.blockchain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentHoldings : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragmentholdings_layout, container, false)
        val buyButton = newView.findViewById<Button>(R.id.holdingBuy)
        buyButton.setOnClickListener {
            val usdView = view?.findViewById<TextView>(R.id.holdingUSD)
            usdView?.text = "$100"
        }
        return newView
    }

}