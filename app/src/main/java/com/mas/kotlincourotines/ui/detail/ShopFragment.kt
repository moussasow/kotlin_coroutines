package com.mas.kotlincourotines.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mas.kotlincourotines.databinding.FragmentShopBinding
import com.squareup.picasso.Picasso

/**
 * Use the [ShopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopFragment : Fragment() {

    companion object {
        const val ARG_IMAGE_URL = "param1"
        const val ARG_SHOP_TITLE = "param2"

        @JvmStatic
        fun newInstance(url: String, title: String) =
            ShopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_URL, url)
                    putString(ARG_SHOP_TITLE, title)
                }
            }
    }

    private lateinit var binding: FragmentShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var url: String? = null
        var title: String? = null
        arguments?.let {
            url = it.getString(ARG_IMAGE_URL)
            title = it.getString(ARG_SHOP_TITLE)
        }
        updateUi(url, title)
        binding.button.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateUi(url: String?, title: String?) {
        Picasso.get().load(url).into(binding.imageShop)
        binding.textTitle.text = title
    }
}