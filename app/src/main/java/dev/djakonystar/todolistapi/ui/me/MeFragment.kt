package dev.djakonystar.todolistapi.ui.me

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
import dev.djakonystar.todolistapi.core.Settings
import dev.djakonystar.todolistapi.data.retrofit.RetrofitService
import dev.djakonystar.todolistapi.databinding.FragmentMeBinding
import dev.djakonystar.todolistapi.ui.update.UpdateViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MeFragment : Fragment(R.layout.fragment_me) {
    private lateinit var binding: FragmentMeBinding
    private lateinit var navController: NavController
    private val viewModel: MeViewModel by viewModel() // by inject()
    private val settings: Settings by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMeBinding.bind(view)
        navController = findNavController()

        viewModel.me()

        viewModel.me.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {
                    setLoading(true)
                }

                is NetworkResult.Success -> {
                    setLoading(false)

                    val user = it.data!!

                    binding.apply {
                        tvName.text = user.name
                        tvEmail.text = user.email
                        tvAge.text = user.age.toString()

                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)
                        val date = sdf.parse(user.createdAt)

                        val sdf2 = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ROOT)
                        val date2 = date?.let { sdf2.format(it) } ?: "Cannot parse date!"

                        tvDate.text = date2
                    }
                }

                is NetworkResult.Error -> {
                    setLoading(false)
                    Snackbar.make(binding.btnUpdate, it.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("age", binding.tvAge.text.toString().toInt())

            navController.navigate(R.id.action_meFragment_to_updateFragment, bundle)
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteMe()

            viewModel.delete.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Loading -> {
                        setLoading(true)
                    }

                    is NetworkResult.Success -> {
                        setLoading(false)
                        Toast.makeText(
                            requireContext(),
                            "Deleted Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(R.id.action_meFragment_to_loginFragment)
                    }

                    is NetworkResult.Error -> {
                        setLoading(false)
                        Snackbar.make(binding.btnDelete, it.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }
}
