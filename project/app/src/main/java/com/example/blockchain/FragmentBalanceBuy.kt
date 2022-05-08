package com.example.blockchain

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
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

class FragmentBalanceBuy : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        val newView = inflater.inflate(R.layout.fragmentbalancebuy_layout, container, false)
        val confirmButton = newView.findViewById<Button>(R.id.balanceBuyConfirm)
        val cancelButton = newView.findViewById<Button>(R.id.balanceBuyCancel)
        val usdView = newView.findViewById<EditText>(R.id.balanceBuyUSD)
        val ethView = newView.findViewById<EditText>(R.id.balanceBuyETH)
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
                var usdQuantity = "%.2f".format(Locale.ENGLISH, (ethQuantity * ethValue))
                usdView.setText(usdQuantity)
            } else {
                usdView.setText("")
                ethView.setText("")
            }
        }
        confirmButton.setOnClickListener {
            if (usdView.text.toString() != "" && ethView.text.toString() != "") {
                if (usdView.text.toString().toFloat() > 0 && ethView.text.toString()
                        .toFloat() > 0
                ) {
                    myRef.get().addOnSuccessListener {
                        val ethBalance = it.value.toString().toFloat()
                        myRef.setValue(ethBalance + ethView.text.toString().toFloat())
                        val fragmentManager = requireActivity().supportFragmentManager
                        val fragmentTransaction = fragmentManager!!.beginTransaction()
                        fragmentTransaction.replace(R.id.balance_buy_view, FragmentBalance())
                        fragmentTransaction.commit()
                        fragmentManager.executePendingTransactions()
                        //confirmButton.visibility = View.INVISIBLE
                        //cancelButton.visibility = View.INVISIBLE
                        //usdView.visibility = View.INVISIBLE
                        //ethView.visibility = View.INVISIBLE
                    }
                    myRef.get().addOnFailureListener {
                        Toast.makeText(
                            newView.context, "Error with database, please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        newView.context, "Please enter a quantity greater than 0",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    newView.context, "Please enter a quantity to purchase",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        cancelButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.balance_buy_view, FragmentBalance())
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            //confirmButton.visibility = View.INVISIBLE
            //cancelButton.visibility = View.INVISIBLE
            //usdView.visibility = View.INVISIBLE
            //ethView.visibility = View.INVISIBLE
        }
        return newView
    }

}