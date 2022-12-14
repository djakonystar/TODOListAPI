package dev.djakonystar.todolistapi.ui.update

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.djakonystar.todolistapi.R
import dev.djakonystar.todolistapi.core.NetworkResult
import dev.djakonystar.todolistapi.core.updateRequest
import dev.djakonystar.todolistapi.databinding.FragmentRegisterBinding
import dev.djakonystar.todolistapi.databinding.FragmentUpdateBinding
import dev.djakonystar.todolistapi.ui.MyViewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MyViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)
        navController = findNavController()

        val age = arguments?.getInt("age") ?: 0

        binding.apply {
            etAge.setText(age.toString())

            btnLogin.setOnClickListener {
                val newAge = etAge.text.toString().toInt()

                val updateRequest = updateRequest(newAge)

                viewModel.update(updateRequest)

                viewModel.update.observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResult.Loading -> {
                            setLoading(true)
                        }

                        is NetworkResult.Success -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Updated Successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        }

                        is NetworkResult.Error -> {
                            setLoading(false)
                            Snackbar.make(btnLogin, it.message.toString(), Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }
}
