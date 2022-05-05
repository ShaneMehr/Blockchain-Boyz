package com.example.blockchain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class FragmentBalance : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragmentbalance_layout, container, false)
        val buyButton = newView.findViewById<Button>(R.id.balanceBuy)
        buyButton.setOnClickListener {
            //val usdView = view?.findViewById<TextView>(R.id.balanceUSD)
            //usdView?.text = "$100"
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.view_pager, FragmentBalanceBuy())
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
        }
        return newView
    }

}