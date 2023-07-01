package com.example.submission1_intermediate.recent

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1_intermediate.R
import com.example.submission1_intermediate.databinding.FragmentHomeBinding
import com.example.submission1_intermediate.databinding.FragmentMapsBinding
import com.example.submission1_intermediate.databinding.FragmentRecentBinding
import com.example.submission1_intermediate.maps.MapsViewModel
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.ui.MainViewModel
import com.example.submission1_intermediate.ui.ViewModelFac
import com.example.submission1_intermediate.ui.ViewModelFactory

class RecentFragment : Fragment() {
    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding tidak ada!")
    private lateinit var viewMod: ReceentViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recent")
    private lateinit var adapter: RecentAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecentAdapter()
        val pref = requireContext().dataStore
        viewMod = viewModels<ReceentViewModel> {
            ViewModelFactory(UserPreference.getInstance(pref))
        }.value

        viewMod.getUser().observe(viewLifecycleOwner){
            Log.d("CEK ISI PERSONAL :" ,it.id)
            viewMod.getStoryPersonal(it.token,it.id)

            viewMod.getListStory().observe(viewLifecycleOwner) {
                adapter.setList(it)

            }
            binding.apply {
                rvRecent.layoutManager = LinearLayoutManager(requireContext())
                rvRecent.setHasFixedSize(true)
                rvRecent.adapter = adapter
            }
        }

    }


}