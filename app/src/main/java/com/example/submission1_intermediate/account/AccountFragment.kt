package com.example.submission1_intermediate.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.example.submission1_intermediate.R
import com.example.submission1_intermediate.databinding.FragmentAccountBinding
import com.example.submission1_intermediate.login.LoginActivity
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.ui.MainViewModel
import com.example.submission1_intermediate.ui.ViewModelFac

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding tidak ada!")
    private val Context.dataStore by preferencesDataStore("settings")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireContext().dataStore
        val viewMod: AccountViewModel by viewModels {
            AccountViewModelFactory(requireContext(), UserPreference.getInstance(pref))
        }

            binding.apply {
                viewMod.getUser().observe(viewLifecycleOwner){
                    binding.nama.text = it.name
                }
                btnLogout.setOnClickListener {
                    viewMod.logout()
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                }
            }



    }

}