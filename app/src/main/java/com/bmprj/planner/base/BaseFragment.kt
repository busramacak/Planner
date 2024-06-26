package com.bmprj.planner.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bmprj.planner.utils.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseFragment<B: ViewDataBinding>(private val layout:Int):Fragment() {
    private lateinit var _binding:B
    protected val binding get()=_binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding=DataBindingUtil.inflate(inflater,layout,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }
    abstract fun initView(view:View)

    @JvmName("UiStateT")
    fun <T> StateFlow<UiState<T>>.handleState(
        coroutineExceptionHandler: CoroutineExceptionHandler?=null,
        onLoading: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSucces: ((T) -> Unit)? = null
    ) {
        lifecycleScope.launch(coroutineExceptionHandler?: EmptyCoroutineContext) {
            this@handleState
                .onStart {
                    onLoading?.invoke()
                }
                .catch {
                    onError?.invoke(it)
                }.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            onLoading?.invoke()
                        }

                        is UiState.Success -> {
                            onSucces?.invoke(state.result)
                        }

                        is UiState.Error -> {
                            onError?.invoke(state.error)
                        }
                    }
                }
        }
    }

    @JvmName("T")
    fun <T> StateFlow<T>.handleStateT(
        coroutineExceptionHandler: CoroutineExceptionHandler?=null,
        onLoading: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSucces: ((T) -> Unit)? = null
    ) {
        lifecycleScope.launch(coroutineExceptionHandler?: EmptyCoroutineContext) {
            this@handleStateT
                .onStart {
                    onLoading?.invoke()
                }
                .catch {
                    onError?.invoke(it)
                }.collect { state ->
                    onSucces?.invoke(state)
                }
        }
    }

}