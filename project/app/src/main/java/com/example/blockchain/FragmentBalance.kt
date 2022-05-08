package com.example.blockchain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

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
        val ethValue = 2743.22
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val myRef = Firebase.database.getReference(userid)
        myRef.get().addOnSuccessListener {
            if (it.value == null) {
                myRef.setValue(0)
                ethView.text = ("0 ETH")
                usdView.text = "$0"
            } else {
                ethView.text = (it.value.toString() + " ETH")
                val roundedUSD = "%,.2f".format(Locale.ENGLISH, (it.value.toString().toFloat() * ethValue))
                usdView.text = "$" + roundedUSD.toString()
            }
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
        sellButton.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.balance_view, FragmentBalanceSell())
            fragmentTransaction.commit()
            fragmentManager.executePendingTransactions()
            buyButton.visibility = View.INVISIBLE
            sellButton.visibility = View.INVISIBLE
        }
        return newView
    }

}