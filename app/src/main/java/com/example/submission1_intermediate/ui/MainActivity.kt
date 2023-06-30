package com.example.submission1_intermediate.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1_intermediate.R
import com.example.submission1_intermediate.databinding.ActivityMainBinding
import com.example.submission1_intermediate.detail.DetailStoryActivity
import com.example.submission1_intermediate.login.LoginActivity
import com.example.submission1_intermediate.maps.MapsActivity
import com.example.submission1_intermediate.paging.LoadingAdapter
import com.example.submission1_intermediate.post.PostActivity
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.response.story

class MainActivity : AppCompatActivity() {
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding : ActivityMainBinding
    private val viewMod: MainViewModel by viewModels {
        ViewModelFac(this, UserPreference.getInstance(dataStore))
    }
    private lateinit var adapter: Adaptor

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        viewMod.getListStory().observe(this@MainActivity) {
            adapter.setList(it)

        }

        adapter= Adaptor()
        adapter.notifyDataSetChanged()
        adapter.setOnClickCallback(object : Adaptor.OnItemClickCallback{
            override fun onItemClicked(data: story) {
                Intent(this@MainActivity,DetailStoryActivity::class.java).also{
                    it.putExtra(DetailStoryActivity.NAME,data.name)
                    it.putExtra(DetailStoryActivity.DESC,data.description)
                    it.putExtra(DetailStoryActivity.URL,data.photoUrl)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter
            }
        }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_post -> {
                Intent(this, PostActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this,"Add Story", Toast.LENGTH_SHORT).show()

                }

            }
            R.id.menu_logout -> {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this,"Logout", Toast.LENGTH_SHORT).show()
                    viewMod.logout()
                }

            }
            R.id.menu_maps -> {
                Intent(this, MapsActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this,"Maps Story", Toast.LENGTH_SHORT).show()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
    }


