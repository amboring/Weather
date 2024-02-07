package com.example.weatherapiusingcoroutines.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
    inline fun <reified T : ViewModel> getViewModel(viewModelStoreOwner: ViewModelStoreOwner) = ViewModelProvider(viewModelStoreOwner, this)[T::class.java]
    inline fun <reified T : ViewModel> getNavScopeViewModel(fragment: Fragment, navGraphId: Int) =
        ViewModelProvider(fragment.findNavController().getViewModelStoreOwner(navGraphId), this)[T::class.java]
}
