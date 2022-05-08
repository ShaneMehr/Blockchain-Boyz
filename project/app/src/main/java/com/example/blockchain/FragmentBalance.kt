package com.example.blockchain

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import android.content.SharedPreferences
import android.widget.EditText

class FragmentBalance : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragmentbalance_layout, container, false)
        val usdView = newView.findViewById<TextView>(R.id.balanceUSD)
        val ethView = newView.findViewById<TextView>(R.id.balanceETH)
        val buyButton = newView.findViewById<Button>(R.id.balanceBuy)
        val sellButton = newView.findViewById<Button>(R.id.balanceSell)
        val mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
//        if (mPrefs.contains(BALANCE_USD_KEY)) {
//            usdView.setText("$ " + mPrefs.getFloat(BALANCE_USD_KEY, 0F).toString())
//        }
        if (mPrefs.contains(BALANCE_ETH_KEY)) {
            ethView.text = (mPrefs.getFloat(BALANCE_ETH_KEY, 0F).toString() + " ETH")
        }
        buyButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.balance_view, FragmentBalanceBuy())
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            buyButton.visibility = View.INVISIBLE
            sellButton.visibility = View.INVISIBLE
        }
        return newView
    }

    companion object {
        private const val BALANCE_USD_KEY = "BALANCE_USD_KEY"
        private const val BALANCE_ETH_KEY = "BALANCE_ETH_KEY"
    }

}