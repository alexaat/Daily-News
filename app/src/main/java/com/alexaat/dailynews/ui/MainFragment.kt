package com.alexaat.dailynews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexaat.dailynews.R
import com.alexaat.dailynews.adapters.NewsListAdapter
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.eventlisteners.OnItemClickedListener
import com.alexaat.dailynews.viewmodels.MainFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModels()

    @Inject
    lateinit var adapter: NewsListAdapter

    private val onItemClickedListener = OnItemClickedListener{
        mainFragmentViewModel.onItemClicked(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val newsListRecyclerview: RecyclerView = view.findViewById(R.id.news_list_recyclerview)
        val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swiperefresh)


        adapter.addOnItemClickedListener(onItemClickedListener)
        newsListRecyclerview.adapter = adapter

        mainFragmentViewModel.articles.observe(viewLifecycleOwner, {
            it?.let { articles ->
                adapter.submitList(articles)
            }
        })

        mainFragmentViewModel.navigateToArticleFragment.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let{newsItem->
                val action = MainFragmentDirections.actionMainFragmentToArticleFragment(webUrl = newsItem.webUrl, title = newsItem.webTitle)
                findNavController().navigate(action)
            }
        })

        swipeRefresh.setOnRefreshListener {
            mainFragmentViewModel.refreshArticles()
            swipeRefresh.isRefreshing = false
        }

        mainFragmentViewModel.loadingStatus.observe(viewLifecycleOwner, {
            val dataState = it.getContentIfNotHandled()
            dataState?.let{state->
                when(state){
                    is DataState.Error ->{
                        Snackbar.make(view, getString(R.string.error_while_loading_data), Snackbar.LENGTH_SHORT).show()
                    }
                    is DataState.Loading ->{
                        Snackbar.make(view, getString(R.string.loading), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar?.title = getString(R.string.main_fragment_title)
         mainFragmentViewModel.refreshArticles()
    }



}