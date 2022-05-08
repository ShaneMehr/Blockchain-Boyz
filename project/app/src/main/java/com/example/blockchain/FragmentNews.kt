package com.example.blockchain

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class FragmentNews : Fragment() {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    val RSS_FEED_LINK = "http://cointelegraph.com/rss/tag/altcoin";

    private var adapter: MyItemRecyclerViewAdapter? = null
    var rssItems = ArrayList<RssItem>()

    private var listV : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmentnews_layout, container, false)
        listV = view.findViewById(R.id.listV)
        listV?.setHasFixedSize(true)
        listV?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        context?.let { rssFeedFetcher(it) }
        val subList = rssItems.subList(0,7)
        adapter = MyItemRecyclerViewAdapter(rssItems, listener,activity)

        listV?.adapter= adapter


        return view
    }



    fun rssFeedFetcher(context: Context): ArrayList<RssItem> {
        val parser = Parser.Builder()
            .context(context)
            .charset(Charset.forName("ISO-8859-7"))
            .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
            .build()
        runBlocking {
            launch {
                val channel = parser.getChannel(url = RSS_FEED_LINK)
                Log.d("MyChannel",channel.toString())
                var count =0
                for(item in channel.articles){
                    val itemToAdd = RssItem()
                    itemToAdd.title = item.title.toString()
                    itemToAdd.link = item.link.toString()
                    itemToAdd.pubDate = item.pubDate.toString()
                    itemToAdd.description = item.description.toString()
                    itemToAdd.category = "item.categories[0]"

                    rssItems.add(itemToAdd)
                    count+=1
                    Log.d("Item",itemToAdd.toString())

                }
            }
        }
        return rssItems
    }


//    fun updateRV(rssItemsL: List<RssItem>) {
//        if (rssItemsL != null && rssItemsL.isNotEmpty()) {
//            rssItems.addAll(rssItemsL)
//            adapter?.notifyDataSetChanged()
//        }
//    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: RssItem?)
    }
}