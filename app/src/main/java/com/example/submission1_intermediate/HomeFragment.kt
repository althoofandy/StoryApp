package com.example.submission1_intermediate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1_intermediate.databinding.FragmentHomeBinding
import com.example.submission1_intermediate.detail.DetailStoryActivity
import com.example.submission1_intermediate.login.LoginActivity
import com.example.submission1_intermediate.paging.LoadingAdapter
import com.example.submission1_intermediate.post.PostActivity
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.response.story
import com.example.submission1_intermediate.ui.Adaptor
import com.example.submission1_intermediate.ui.MainViewModel
import com.example.submission1_intermediate.ui.ViewModelFac

class HomeFragment : Fragment() {
    private lateinit var adapter: Adaptor
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding tidak ada!")
    private val Context.dataStore by preferencesDataStore("settings")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireContext().dataStore
        val viewMod: MainViewModel by viewModels {
            ViewModelFac(requireContext(), UserPreference.getInstance(pref))
        }
        binding.btnUpload.setOnClickListener {
            startActivity(Intent(requireContext(), PostActivity::class.java))
        }
        viewMod.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                binding.rvStory.adapter = adapter.withLoadStateFooter(
                    footer = LoadingAdapter {
                        adapter.retry()
                    }
                )
                viewMod.getStoryPage(user.token).observe(this) {
                    adapter.submitData(lifecycle, it)
                }
                Log.d("result main :", user.token)
            } else {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }
        viewMod.getListStory().observe(viewLifecycleOwner) {
            adapter.setList(it)

        }

        adapter = Adaptor()
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : Adaptor.OnItemClickCallback {
            override fun onItemClicked(data: story) {
                Intent(requireContext(), DetailStoryActivity::class.java).also {
                    it.putExtra(DetailStoryActivity.NAME, data.name)
                    it.putExtra(DetailStoryActivity.DESC, data.description)
                    it.putExtra(DetailStoryActivity.URL, data.photoUrl)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(requireContext())
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter
        }


    }


}