package com.karthik.zohotasknewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karthik.zohotasknewsapp.News
import com.karthik.zohotasknewsapp.OnReadMoreListener
import com.karthik.zohotasknewsapp.databinding.ItemNewsLayoutBinding

class NewsListAdapter(
    var newsList: List<News>? = null,
    val onReadMoreListener: OnReadMoreListener
) : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): NewsListViewHolder {
        val view = ItemNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        view.root.setOnClickListener {
            newsList?.get(pos)?.let { news -> onReadMoreListener.onNewsClick(news) }
        }

        view.tvReadMoreItemNewsLayout.setOnClickListener {
            newsList?.get(pos)?.let { news -> onReadMoreListener.onReadMoreClick(news) }
        }

        return NewsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {

        newsList?.get(position)?.apply {
            holder.binding.apply {
                Glide.with(imgvThumbnailItemNewsLayout.context).load(imageUrl)
                    .into(imgvThumbnailItemNewsLayout)
                tvTitleItemNewsLayout.text = title
                tvDescriptionItemNewsLayout.text = content
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(newsList: List<News>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }

    class NewsListViewHolder(val binding: ItemNewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}