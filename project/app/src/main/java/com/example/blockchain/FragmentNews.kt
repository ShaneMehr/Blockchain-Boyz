package com.example.blockchain

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockchain.R
import android.os.AsyncTask
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class FragmentNews : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    val RSS_FEED_LINK = "http://cointelegraph.com/rss/tag/altcoin";

    var adapter: MyItemRecyclerViewAdapter? = null
    var rssItems = ArrayList<RssItem>()

    var listV : RecyclerView ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmentnews_layout, container, false)
        listV = view.findViewById(R.id.listV)
        return view
    }

    // Changed Lowkey
    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MyItemRecyclerViewAdapter(rssItems, listener,activity)
        listV?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        listV?.adapter = adapter

        val url = URL(RSS_FEED_LINK)
        //need to implement
        //RssFeedFetcher(this).execute(url)
    }

    fun updateRV(rssItemsL: List<RssItem>) {
        if (rssItemsL != null && rssItemsL.isNotEmpty()) {
            rssItems.addAll(rssItemsL)
            adapter?.notifyDataSetChanged()
        }
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RssItem?)
    }
}