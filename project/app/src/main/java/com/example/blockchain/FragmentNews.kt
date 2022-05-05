package com.example.blockchain

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blockchain.R
import android.os.AsyncTask
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class FragmentNews : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    val RSS_FEED_LINK = "https://www.relsellglobal.in/feed/";

    var adapter: MyItemRecyclerViewAdapter? = null
    var rssItems = ArrayList<RssItem>()

    var listV : RecyclerView ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmentnews_layout, container, false)
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RssItem?)
    }
}