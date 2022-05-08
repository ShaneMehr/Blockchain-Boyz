package com.example.blockchain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blockchain.R
import com.example.blockchain.adapters.CoinAdapter
import com.example.blockchain.repository.CoinRepository
import com.example.blockchain.viewmodels.CryptoListViewModel
import com.example.blockchain.viewmodels.CryptoListViewModelFactory
import com.example.blockchain.utils.Resource

class FragmentMarket : Fragment() {

    lateinit var repository: CoinRepository
    private lateinit var viewModel: CryptoListViewModel
    lateinit var  coinAdapter: CoinAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    val TAG = "CryptoListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.crypto_list_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = CoinRepository()
        val cryptoListViewModelFactory = CryptoListViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, cryptoListViewModelFactory).get(CryptoListViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        setUpRecyclerView()

        viewModel.coinsList.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        coinAdapter.differ.submitList(it.data.toList())
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(context, "Check your internet", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Error has Occured ${response.message}")
                }
                is Resource.Loading -> showProgressBar()
            }

        })
    }

    private fun hideProgressBar(){ progressBar.setVisibility(View.GONE) }
    private fun showProgressBar(){ progressBar.setVisibility(View.VISIBLE) }

    private fun setUpRecyclerView() {

        coinAdapter = CoinAdapter()
        recyclerView.apply {
            adapter =coinAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}