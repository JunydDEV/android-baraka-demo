package com.android.app.android_baraka_demo.presentation.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.android_baraka_demo.R
import com.android.app.android_baraka_demo.data.models.news.NewsItem

class TopNewsAdapter(
    val topNewsItemsList: List<NewsItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_news_big_layout, parent, false)
        return TopNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val topNewsViewHolder = holder as TopNewsViewHolder
        topNewsViewHolder.setNewsList()
    }

    override fun getItemCount(): Int {
        return topNewsItemsList.size
    }

    inner class TopNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageViewNewsImage = view.findViewById<ImageView>(R.id.imageViewNewsImage)
        private val textViewNewsTitle = view.findViewById<TextView>(R.id.textViewNewsTitle)

        fun setNewsList() {
            textViewNewsTitle.text = topNewsItemsList[adapterPosition].newsTitle
        }
    }
}