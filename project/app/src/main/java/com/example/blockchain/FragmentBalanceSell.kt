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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class FragmentBalanceSell : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newView = inflater.inflate(R.layout.fragmentbalancesell_layout, container, false)
        val confirmButton = newView.findViewById<Button>(R.id.balanceSellConfirm)
        val cancelButton = newView.findViewById<Button>(R.id.balanceSellCancel)
        val usdView = newView.findViewById<EditText>(R.id.balanceSellUSD)
        val ethView = newView.findViewById<EditText>(R.id.balanceSellETH)
        val ethValue = 2743.22
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val myRef = Firebase.database.getReference(userid)
        usdView.setOnClickListener {
            if (usdView.text.toString() != "") {
                usdView.setText("%.2f".format(Locale.ENGLISH, usdView.text.toString().toFloat()))
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
                var usdQuantity = "%.2f".format(Locale.ENGLISH,(ethQuantity * ethValue))
                usdView.setText(usdQuantity.toString())
            } else {
                usdView.setText("")
                ethView.setText("")
            }
        }
        confirmButton.setOnClickListener {
            if (usdView.text.toString() != "" && ethView.text.toString() != "") {
                if (usdView.text.toString().toFloat() > 0 && ethView.text.toString().toFloat() > 0) {
                    myRef.get().addOnSuccessListener {
                        val ethBalance = it.value.toString().toFloat()
                        if (ethView.text.toString().toFloat() <= ethBalance) {
                            myRef.setValue(ethBalance - ethView.text.toString().toFloat())
                            val fragmentManager = requireActivity().supportFragmentManager
                            val fragmentTransaction = fragmentManager!!.beginTransaction()
                            fragmentTransaction.replace(R.id.balance_sell_view, FragmentBalance())
                            fragmentTransaction.commit()
                            fragmentManager.executePendingTransactions()
                            confirmButton.visibility = View.INVISIBLE
                            cancelButton.visibility = View.INVISIBLE
                            usdView.visibility = View.INVISIBLE
                            ethView.visibility = View.INVISIBLE
                        } else {
                            Toast.makeText(newView.context, "Please enter a quantity less than or equal to your current balance",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                    myRef.get().addOnFailureListener {
                        Toast.makeText(newView.context, "Error with database, please try again",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(newView.context, "Please enter a quantity greater than 0",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(newView.context, "Please enter a quantity to sell",
                    Toast.LENGTH_SHORT).show()
            }
        }
        cancelButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.balance_sell_view, FragmentBalance())
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            confirmButton.visibility = View.INVISIBLE
            cancelButton.visibility = View.INVISIBLE
            usdView.visibility = View.INVISIBLE
            ethView.visibility = View.INVISIBLE
        }
        return newView
    }

}