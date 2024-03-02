package com.example.lesson54.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson54.data.viewmodel.MainViewModel
import com.example.lesson54.databinding.FragmentSecondBinding
import com.example.lesson54.ui.adapter.PostsAdapter


class SecondFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding get() = _binding!!
    private val adapter = PostsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePosts()
        setupRecyclerView()
        setupRecyclerViewDelete()
        setupBackButton()
    }

    private fun observePosts() {
        viewModel.allPosts.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupRecyclerViewDelete() {
        adapter.setOnItemLongClickListener(object : PostsAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                val postToDelete = adapter.originalPosts[position]
                viewModel.deletePost(post = postToDelete, onFailure = { message: String ->
                    Log.e("failureDelete", message)
                })
            }

        })
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
