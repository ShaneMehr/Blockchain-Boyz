package com.example.blockchain.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.blockchain.R
import com.example.blockchain.models.Coins

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {


    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtRank: TextView
        val txtSymbol: TextView
        val txtName: TextView
        val txtPercent: TextView
        val txtPrice: TextView

        init {
            txtRank = itemView.findViewById(R.id.txt_rank)
            txtSymbol = itemView.findViewById(R.id.txt_symbol)
            txtName = itemView.findViewById(R.id.txt_name)
            txtPercent = itemView.findViewById(R.id.txt_percentage)
            txtPrice = itemView.findViewById(R.id.txt_price)

        }
    }

    private val differCallback = object: DiffUtil.ItemCallback<Coins>(){
        override fun areItemsTheSame(oldItem: Coins, newItem: Coins): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: Coins, newItem: Coins): Boolean {
            return  oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.crypto_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coinInfo = differ.currentList[position]

        holder.apply {
            txtName.text = coinInfo.name
            txtPercent.text = coinInfo.percentage
            txtPrice.text = coinInfo.price
            txtSymbol.text = coinInfo.symbol
            txtRank.text = coinInfo.rank

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}