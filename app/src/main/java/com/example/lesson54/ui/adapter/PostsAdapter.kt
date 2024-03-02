package com.example.lesson54.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson54.R
import com.example.lesson54.data.model.Post

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.CharacterViewHolder>() {

     var originalPosts = mutableListOf<Post>()

    //    private var filteredPosts: List<Post> = originalPosts
//    private var posts = listOf<Post>()
    private var onItemLongClickListener: OnItemLongClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val post = originalPosts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return originalPosts.size
    }

    fun updateData(newData: List<Post>) {
        originalPosts.clear()
        originalPosts.addAll(newData)
        originalPosts.distinctBy {
            it.id
        }
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val descTextView: TextView = itemView.findViewById(R.id.tv_desc)

        fun bind(post: Post) {
            titleTextView.text = post.title
            descTextView.text = post.body
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemLongClickListener?.onItemLongClick(position)
                }
                true
            }
        }

    }

    //    fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filteredList = mutableListOf<Post>()
//                if (constraint.isNullOrEmpty()) {
//                    filteredList.addAll(originalPosts)
//                } else {
//                    val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
//                    for (post in originalPosts) {
//                        if (post.title.lowercase(Locale.ROOT).contains(filterPattern) ||
//                            post.body.lowercase(Locale.ROOT).contains(filterPattern)
//                        ) {
//                            filteredList.add(post)
//                        }
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filteredList
//                return filterResults
//            }
//
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filteredPosts = results?.values as List<Post>
//                notifyDataSetChanged()
//            }
//
//        }
//    }
    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }
}


