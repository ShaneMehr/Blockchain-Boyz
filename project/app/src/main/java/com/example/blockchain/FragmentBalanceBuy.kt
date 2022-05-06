package com.example.blockchain

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentBalanceBuy : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragmentbalancebuy_layout, container, false)
        val confirmButton = newView.findViewById<Button>(R.id.balanceBuyConfirm)
        val cancelButton = newView.findViewById<Button>(R.id.balanceBuyCancel)
        val usdView = newView.findViewById<EditText>(R.id.balanceBuyUSD)
        val ethView = newView.findViewById<EditText>(R.id.balanceBuyETH)
        val ethValue = 2743.22
        usdView.setOnClickListener {
            if (usdView.text.toString() != "") {
                val usdQuantity = usdView.text.toString().toFloat()
                var ethQuantity = usdQuantity / ethValue
                ethView.setText(ethQuantity.toString())
            } else {
                usdView.setText("")
                ethView.setText("")
            }
        }
        ethView.setOnClickListener {
            if (ethView.text.toString() != "") {
                val ethQuantity = ethView.text.toString().toFloat()
                var usdQuantity = ethQuantity * ethValue
                usdView.setText(usdQuantity.toString())
            } else {
                usdView.setText("")
                ethView.setText("")
            }
        }
        confirmButton.setOnClickListener {
            if (usdView.text.toString() != "" && ethView.text.toString() != "") {
                if (usdView.text.toString().toFloat() > 0 && ethView.text.toString().toFloat() > 0) {
                    val mPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)
                    val editor = mPrefs.edit()
                    val currETH = mPrefs.getFloat(BALANCE_ETH_KEY, 0F)
                    editor.putFloat(BALANCE_ETH_KEY, currETH + ethView.text.toString().toFloat())
                    editor.commit()
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction.replace(R.id.balance_buy_view, FragmentBalance())
                    //fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    fragmentManager.executePendingTransactions()
                    confirmButton.visibility = View.INVISIBLE
                    cancelButton.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(newView.context, "Please enter a quantity greater than 0",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(newView.context, "Please enter a quantity to purchase",
                    Toast.LENGTH_SHORT).show()
            }

            //requireActivity().supportFragmentManager.popBackStack()
        }
        return newView
    }

    companion object {
        private const val BALANCE_USD_KEY = "BALANCE_USD_KEY"
        private const val BALANCE_ETH_KEY = "BALANCE_ETH_KEY"
    }
}