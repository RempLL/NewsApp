package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.CategoryViewPagerAdapter
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var categoryAdapter: CategoryViewPagerAdapter

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        viewPager.apply {
            adapter = categoryAdapter
        }
        categoryAdapter.names = resources.getStringArray(R.array.a).toList()
        categoryAdapter.newsAdapter.setOnItemClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainsFragmentToArticleFragment(it)
            )
        }
        viewModel.getAllCategories()

    }

    private fun subscribeToObservers() {
        viewModel.businessNews.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                it.data?.let { newsResponse ->
                    categoryAdapter.setBusinessArticles(newsResponse)
                }

            }
            Timber.d(it.toString())
        })
        viewModel.entertainmentNews.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                it.data?.let { newsResponse ->
                    categoryAdapter.setEntertainmentArticles(newsResponse)
                }
            }
        })
        viewModel.healthNews.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                it.data?.let { newsResponse ->
                    categoryAdapter.setHealthArticles(newsResponse)
                }
            }
        })
        viewModel.scienceNews.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                it.data?.let { newsResponse ->
                    categoryAdapter.setScienceArticles(newsResponse)
                }
            }
        })
        viewModel.sportNews.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                it.data?.let { newsResponse ->
                    categoryAdapter.setSportsArticles(newsResponse)
                }
            }
        })
        viewModel.techNews.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                it.data?.let { newsResponse ->
                    categoryAdapter.setTechnologyArticles(newsResponse)
                }
            }
        })
    }
}