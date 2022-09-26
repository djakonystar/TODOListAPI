package dev.djakonystar.todolistapi.ui.register

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
import dev.djakonystar.todolistapi.data.models.request.Register
import dev.djakonystar.todolistapi.databinding.FragmentRegisterBinding
import dev.djakonystar.todolistapi.ui.MyViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MyViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        navController = findNavController()

        binding.apply {

            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val name = etName.text.toString()
                val age = etAge.text.toString().toInt()

                val user = Register(name, email, password, age)
                viewModel.register(user)

                viewModel.register.observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResult.Loading -> {
                            setLoading(true)
                        }

                        is NetworkResult.Success -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Register Successful!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navController.navigate(R.id.action_registerFragment_to_loginFragment)
                        }

                        is NetworkResult.Error -> {
                            setLoading(false)
                            Snackbar.make(btnRegister, it.message.toString(), Snackbar.LENGTH_SHORT)
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
