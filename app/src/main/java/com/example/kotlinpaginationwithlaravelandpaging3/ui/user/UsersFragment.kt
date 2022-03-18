package com.example.kotlinpaginationwithlaravelandpaging3.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kotlinpaginationwithlaravelandpaging3.R
import com.example.kotlinpaginationwithlaravelandpaging3.databinding.FragmentUsersBinding
import com.example.kotlinpaginationwithlaravelandpaging3.presentation.UserViewModel
import com.example.kotlinpaginationwithlaravelandpaging3.presentation.UserViewModel2
import com.example.kotlinpaginationwithlaravelandpaging3.presentation.UserViewModelFactory
import com.example.kotlinpaginationwithlaravelandpaging3.repository.RetrofitClient
import com.example.kotlinpaginationwithlaravelandpaging3.repository.UserRepositoryImp
import com.example.kotlinpaginationwithlaravelandpaging3.ui.user.adapters.UserClickListener
import com.example.kotlinpaginationwithlaravelandpaging3.ui.user.adapters.UsersAdapterV3
import kotlinx.coroutines.flow.collectLatest


class UsersFragment : Fragment(R.layout.fragment_users){

    lateinit var binding : FragmentUsersBinding


    lateinit var recyclerViewAdapterv3: UsersAdapterV3

    private val viewModel1 by viewModels<UserViewModel> { UserViewModelFactory(
        UserRepositoryImp(RetrofitClient.webservice)
        )
     }

    // Si se desea probar el viewModel2 descomentar y comentar la instancia del viewModel1
   // private val viewModel2: UserViewModel2 by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersBinding.bind(view)

        initView()
        setObserver()
        setupSearchView()
       // usersRyclerViewAdapter = UsersAdapter(this)

       // binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
       // binding.rvUsers.adapter = usersRyclerViewAdapter

    }
    private fun initView() {
        recyclerViewAdapterv3 = UsersAdapterV3(UserClickListener {
            Toast.makeText(requireContext(), "Click a: ${it.name}", Toast.LENGTH_SHORT).show()
        })
        binding.rvUsers.adapter = recyclerViewAdapterv3
    }

    fun setObserver(){

        lifecycleScope.launchWhenCreated {
            viewModel1.getUsers(null).collectLatest {
                recyclerViewAdapterv3.submitData(it)
            }
        }

    }

    private fun setupSearchView(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Busqueda usando el viewModel y Flow
                Log.d("search", query!!)
                lifecycleScope.launchWhenCreated {
                    viewModel1.getUsers(query).collectLatest {
                        recyclerViewAdapterv3.submitData(it)
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                //Busqueda usando el viewModel y Flow
                Log.d("search", newText!!)
                lifecycleScope.launchWhenCreated {
                    viewModel1.getUsers(newText).collectLatest {
                        recyclerViewAdapterv3.submitData(it)
                    }
                }
                //WARNING: DO NOT USE THIS METHOD IN A PRODUCTION PROJECT BECAUSE CAN PRODUCE
                //HUNDRES OF REQUEST WHILE THE USER WRITES A SEARCH, INSTEAD OF USE THE METHOD onQueryTextSubmit.
                return false
            }
        })
    }

}