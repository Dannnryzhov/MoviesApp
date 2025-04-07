package com.example.moviesapp.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentHostBinding
import com.example.moviesapp.presentation.application.MoviesApp
import com.example.moviesapp.presentation.viewmodel.HostViewModel
import com.example.moviesapp.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class HostFragment : BaseFragment<FragmentHostBinding, HostViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override val viewModel: HostViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HostViewModel::class.java]
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHostBinding {
        return FragmentHostBinding.inflate(inflater, container, false)
    }

    override fun injectDependencies() {
        (requireActivity().application as MoviesApp).component.inject(this)
    }

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Настраиваем нижнюю навигацию
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        // Обработка кнопки "Назад"
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.fragmentContainerLand != null) {
                    val detailFragment = childFragmentManager.findFragmentById(R.id.fragment_container_land)
                    if (detailFragment != null) {
                        childFragmentManager.beginTransaction().remove(detailFragment).commit()
                        return
                    }
                }
                if (!navController.popBackStack()) {
                    requireActivity().finish()
                }
            }
        })

        // Если есть правая панель — режим ландшафтной ориентации
        if (binding.fragmentContainerLand != null) {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.movieDetailFragment) {
                    // Загружаем детальный фрагмент в правую панель
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_land, MovieDetailFragment::class.java, arguments)
                        .commit()

                    // Узнаем источник вызова
                    val source = arguments?.getString("sourceFragment")
                    when (source) {
                        "favorites" -> {
                            controller.popBackStack(R.id.favouriteMoviesFragment, false)
                        }
                        else -> {
                            controller.popBackStack(R.id.movieListFragment, false)
                        }
                    }
                } else {
                    // Если не детальный экран — очищаем правую панель
                    childFragmentManager.findFragmentById(R.id.fragment_container_land)?.let { fragment ->
                        childFragmentManager.beginTransaction().remove(fragment).commit()
                    }
                }
            }
        }
    }
}
