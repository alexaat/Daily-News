package com.alexaat.dailynews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexaat.dailynews.R
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.databinding.ItemNewsBinding
import com.alexaat.dailynews.eventlisteners.OnItemClickedListener
import javax.inject.Inject

class NewsListAdapter @Inject constructor(): ListAdapter<NewsItem, NewsListAdapter.ViewHolder>(NewsListCallBack()){

    private var onItemClickedListener: OnItemClickedListener? = null

    fun addOnItemClickedListener(onItemClickedListener: OnItemClickedListener){
        this.onItemClickedListener = onItemClickedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), onItemClickedListener)

    class ViewHolder(private val binding: ItemNewsBinding) :RecyclerView.ViewHolder(binding.root){
        companion object{
            fun inflateFrom(parent: ViewGroup):ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val res = R.layout.item_news
                return ViewHolder(DataBindingUtil.inflate(inflater,res,parent,false))
            }
        }

        fun bind(newsItem: NewsItem, onItemClickedListener: OnItemClickedListener?){
            binding.newsItem = newsItem
            onItemClickedListener?.let{
                binding.onItemClickedListener = it
            }
            binding.executePendingBindings()
        }
    }

}

class NewsListCallBack: DiffUtil.ItemCallback<NewsItem>() {
    override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem == newItem
    }
}