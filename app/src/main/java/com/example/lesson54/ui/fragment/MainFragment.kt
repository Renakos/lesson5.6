package com.example.lesson54.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lesson54.R
import com.example.lesson54.data.model.Post
import com.example.lesson54.data.viewmodel.MainViewModel
import com.example.lesson54.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private val postId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkForData()
        setupUpdateButton()
        setupCreateButton()
        setupEditButton()
        setupDeleteButton()
    }


    private fun setupUpdateButton() {
        binding.btnUpdatePost.setOnClickListener {
            val id = binding.etId.text.toString().toInt()
            val title = binding.etTitle.text.toString().trim()
            val body = binding.etBody.text.toString().trim()
            val post = Post(id, title = title, body = body, userId = id)
            viewModel.updatePost(
                postId,
                post

            ) { s, throwable ->
                Log.e("error", s, throwable)
                checkForData()
            }
            checkForData()

            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
        }
    }

    private fun setupCreateButton() = with(binding) {
        btnCreatePost.setOnClickListener {
            val id = etId.text.toString().toInt()
            val title = etTitle.text.toString()
            val body = etBody.text.toString()
            val newPost =
                Post(title = title, body = body, userId = id)

            viewModel.createNewPost(
                newPost
            ) { message, throwable ->
                Log.e("error", message, throwable)
                checkForData()

            }
            checkForData()
            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
        }
    }

    private fun setupEditButton() = with(binding) {
        btnUpdatePost.setOnClickListener {
            val id = etId.text.toString().toInt()
            val postId = etIdPost.text.toString().toInt()
            val postParameter = HashMap<String, Any>()
            val title = etTitle.text.toString()
            val body = etBody.text.toString()
            val editedPost =
                Post(id = 2, title = title, body = body, userId = id)
            postParameter["id"] = id
            postParameter["postId"] = postId
            viewModel.editPost(editedPost, postParameter) { s, throwable ->
                Log.e("error", s, throwable)
                checkForData()
            }
            checkForData()
            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)

        }
    }

    private fun setupDeleteButton() = with(binding) {
        btnDeletePost.setOnClickListener {
            val id = etId.text.toString().toInt()
            val postId = etIdPost.text.toString().toInt()
            val title = etTitle.text.toString()
            val body = etBody.text.toString()
            val editedPost =
                Post(id = 3, title = title, body = body, userId = id)
            checkForData()
            viewModel.deletePost(editedPost, postId) { s, throwable ->
                Log.e("error", s, throwable)
                checkForData()
            }
            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkForData() {
        Log.e("DataBase", viewModel.allDatabasePosts.toString())
        Log.e("DataBase", viewModel.allPosts.toString())
    }

}
