package com.mas.kotlincourotines.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mas.kotlincourotines.R
import com.mas.kotlincourotines.data.ApiRepository
import com.mas.kotlincourotines.data.model.ShopContent
import com.mas.kotlincourotines.data.result.NewsResult
import com.mas.kotlincourotines.data.result.ShopResult
import com.mas.kotlincourotines.databinding.FragmentMainBinding
import com.mas.kotlincourotines.network.ApiModule
import com.mas.kotlincourotines.ui.adapter.ShopContentAdapter
import com.mas.kotlincourotines.ui.detail.ShopFragment
import com.mas.kotlincourotines.ui.setDebouncedClickListener
import com.mas.kotlincourotines.ui.viewmodels.MainViewModel
import com.mas.kotlincourotines.ui.viewmodels.MainViewModelFactory

class MainFragment : Fragment(), ShopContentAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var shopContentAdapter: ShopContentAdapter? = null
    private val shopContentList = mutableListOf<ShopContent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ApiRepository(ApiModule.masApi)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initRecyclerView()
        binding.btnRefresh.setDebouncedClickListener {
            fetchContent()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchContent()
    }

    private fun initObservers() {
        mainViewModel.data.observe(viewLifecycleOwner) { newsResult ->
            when(newsResult) {
                is NewsResult.Success ->{

                }
                is NewsResult.Error -> {
                    showError(newsResult.exception.message)
                }
            }
        }
        mainViewModel.shopContents.observe(viewLifecycleOwner) { shopResult ->
            when(shopResult) {
                is ShopResult.Success -> {
                    shopContentList.clear()
                    shopContentList.addAll(shopResult.data.contents)
                    shopContentAdapter?.notifyDataSetChanged()
                    binding.recyclerview.visibility = View.VISIBLE
                }
                is ShopResult.Error -> {
                    showError(shopResult.exception.message)
                }
            }
        }

        mainViewModel.loading.observe(viewLifecycleOwner) { isLoading->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
    private fun fetchContent() {
        binding.recyclerview.visibility = View.GONE
        mainViewModel.loadData()
        mainViewModel.fetchShopContents()

    }
    private fun initRecyclerView() {
        shopContentAdapter = ShopContentAdapter(shopContentList, this).also { adapter ->
            binding.recyclerview.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }

    private fun showError(error: String?) {
        binding.apply {
            textError.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
            textError.text= error
        }
    }

    override fun onItemClick(shopContent: ShopContent) {
        val fragment = ShopFragment.newInstance(shopContent.image.url, shopContent.title)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}