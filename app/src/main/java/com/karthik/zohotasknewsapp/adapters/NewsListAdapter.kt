package com.karthik.zohotasknewsapp.adapters

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karthik.zohotasknewsapp.News
import com.karthik.zohotasknewsapp.OnReadMoreListener
import com.karthik.zohotasknewsapp.databinding.ItemNewsLayoutBinding
import java.util.regex.Matcher
import java.util.regex.Pattern


class NewsListAdapter(
    var newsList: List<News>? = null,
    val onReadMoreListener: OnReadMoreListener,
    var searchedText : String? = null
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

                if(!searchedText.isNullOrBlank())
                {
                    var spannableString = SpannableString(title)
                    val pattern: Pattern = Pattern.compile(
                        searchedText,
                        Pattern.CASE_INSENSITIVE
                    )

                    //giving the compliled pattern to matcher to find matching pattern in cursor text

                    //giving the compliled pattern to matcher to find matching pattern in cursor text
                    val matcher: Matcher = pattern.matcher(title)
                    spannableString.setSpan(
                        BackgroundColorSpan(
                            Color.TRANSPARENT
                        ), 0, spannableString.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    while (matcher.find()) {

                        //highlight all matching words in cursor with white background(since i have a colorfull background image)
                        spannableString.setSpan(
                            BackgroundColorSpan(
                                Color.CYAN
                            ), matcher.start(), matcher.end(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

                    tvTitleItemNewsLayout.text = spannableString
                }else
                {
                    tvTitleItemNewsLayout.text = title
                    tvDescriptionItemNewsLayout.text = content
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(newsList: List<News>, query: String) {
        this.newsList = newsList
        searchedText = query
        notifyDataSetChanged()
    }

    class NewsListViewHolder(val binding: ItemNewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}