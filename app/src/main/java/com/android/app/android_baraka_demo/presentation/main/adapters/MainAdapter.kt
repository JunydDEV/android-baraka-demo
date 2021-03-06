package com.android.app.android_baraka_demo.presentation.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app.android_baraka_demo.R
import com.android.app.android_baraka_demo.data.models.ErrorItem
import com.android.app.android_baraka_demo.data.models.Section
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.news.NewsSection
import com.android.app.android_baraka_demo.data.models.news.TopNewsSection
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.models.tickers.TickersSection
import com.android.app.android_baraka_demo.presentation.main.adapters.TickersAdapter.Companion.DELAY_BETWEEN_SCROLL_MS
import com.android.app.android_baraka_demo.presentation.main.adapters.TickersAdapter.Companion.DIRECTION_RIGHT
import com.android.app.android_baraka_demo.presentation.main.adapters.TickersAdapter.Companion.SCROLL_DX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainAdapter(
    private val context: Context,
    private val sectionsList: List<Section>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var firstPosition: Int = 0
    private lateinit var tickersAdapter: TickersAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            Section.TICKERS -> {
                val view = layoutInflater.inflate(R.layout.section_tickers, parent, false)
                TickersSectionViewHolder(view)
            }
            Section.TOP_NEWS -> {
                val view = layoutInflater.inflate(R.layout.section_top_news, parent, false)
                TopNewsSectionViewHolder(view)
            }
            Section.ALL_NEWS -> {
                val view = layoutInflater.inflate(R.layout.section_news, parent, false)
                NewsSectionViewHolder(view)
            }
            Section.ERROR -> {
                val view = layoutInflater.inflate(R.layout.item_empty_layout, parent, false)
                ErrorItemViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_loader, parent, false)
                LoadingItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TickersSectionViewHolder -> {
                val sectionTickers = sectionsList[position] as TickersSection
                holder.addTickerList(sectionTickers.tickerItemsList)
                holder.setSectionTitle(sectionTickers.getLabel())
            }
            is TopNewsSectionViewHolder -> {
                val topNewsSection = sectionsList[position] as TopNewsSection
                holder.addTopNewsList(topNewsSection.topNewsItemsList)
                holder.setSectionTitle(topNewsSection.getLabel())

            }
            is NewsSectionViewHolder -> {
                val newsSection = sectionsList[position] as NewsSection
                holder.addNewsList(newsSection.newsItemsList)
                holder.setSectionTitle(newsSection.getLabel())
            }
            is ErrorItemViewHolder -> {
                val errorItem = sectionsList[position] as ErrorItem
                holder.bind(errorItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return sectionsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return sectionsList[position].getType()
    }

    fun getHorizontalLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun getVerticalLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    inner class TickersSectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewSectionTitle: TextView = view.findViewById(R.id.textViewSectionTitle)
        private val recyclerViewTickers: RecyclerView = view.findViewById(R.id.recyclerViewTickers)

        fun setSectionTitle(title: String) {
            textViewSectionTitle.text = title
        }

        fun addTickerList(tickerItemsList: List<TickerItem>) {
            tickersAdapter = TickersAdapter()
            recyclerViewTickers.apply {
                layoutManager = getHorizontalLayoutManager()
                adapter = tickersAdapter
            }

            tickersAdapter.submitList(tickerItemsList)
            /*recyclerViewTickers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    firstPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                }
            })*/

            CoroutineScope(Dispatchers.IO).launch {
                autoScrollTickersList(recyclerViewTickers, tickersAdapter)
            }
        }
    }

    inner class TopNewsSectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewSectionTitle: TextView = view.findViewById(R.id.textViewSectionTitle)
        private val recyclerViewTopNews: RecyclerView = view.findViewById(R.id.recyclerViewTopNews)

        fun setSectionTitle(title: String) {
            textViewSectionTitle.text = title
        }

        fun addTopNewsList(topNewsItemsList: List<NewsItem>) {
            val topNewsAdapter = TopNewsAdapter(topNewsItemsList)
            recyclerViewTopNews.apply {
                layoutManager = getHorizontalLayoutManager()
                adapter = topNewsAdapter
            }
        }
    }

    inner class NewsSectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewSectionTitle: TextView = view.findViewById(R.id.textViewSectionTitle)
        private val recyclerViewAllNews: RecyclerView = view.findViewById(R.id.recyclerViewNews)

        fun setSectionTitle(title: String) {
            textViewSectionTitle.text = title
        }

        fun addNewsList(newsItemsList: List<NewsItem>) {
            val topNewsAdapter = NewsAdapter(newsItemsList)
            recyclerViewAllNews.apply {
                layoutManager = getVerticalLayoutManager()
                adapter = topNewsAdapter
            }
        }
    }

    private tailrec suspend fun autoScrollTickersList(
        recyclerView: RecyclerView,
        tickersAdapter: TickersAdapter
    ) {
        if (recyclerView.canScrollHorizontally(DIRECTION_RIGHT)) {
            recyclerView.smoothScrollBy(SCROLL_DX, 0)
        } else {
            firstPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (firstPosition != RecyclerView.NO_POSITION) {
                val currentList = tickersAdapter.currentList
                val secondPart = currentList.subList(0, firstPosition)
                val firstPart = currentList.subList(firstPosition, currentList.size)
                tickersAdapter.submitList(firstPart + secondPart)
            }
        }
        delay(DELAY_BETWEEN_SCROLL_MS)
        autoScrollTickersList(recyclerView, tickersAdapter)
    }

    inner class LoadingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Nothing to map here
    }

    inner class ErrorItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewEmptyLayout: TextView = view.findViewById(R.id.textViewEmptyLayout)

        fun bind(errorItem: ErrorItem) {
            textViewEmptyLayout.text = errorItem.message
        }
    }

    fun updateNewList() {
        tickersAdapter.notifyItemRangeChanged(firstPosition, tickersAdapter.currentList.size)
    }
}