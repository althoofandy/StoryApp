package com.example.submission1_intermediate.ui
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submission1_intermediate.databinding.ItemStoryBinding
import com.example.submission1_intermediate.response.story

class Adaptor : PagingDataAdapter<story, Adaptor.UserViewHolder>(DIFF_CALLBACK) {
    private val list = ArrayList<story>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<story>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : story) {
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with((itemView))
                    .load(user.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(rvStory)
                nama.text = user.name

            }
        }
    }

    override fun onCreateViewHolder(qwe: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(qwe.context),qwe,false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(hold: UserViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            hold.bind(data)
        }

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: story)
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<story>() {
            override fun areItemsTheSame(oldItem: story, newItem: story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: story, newItem: story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}