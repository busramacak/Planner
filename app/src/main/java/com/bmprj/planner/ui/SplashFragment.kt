package com.bmprj.planner.ui

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bmprj.planner.R
import com.bmprj.planner.base.BaseFragment
import com.bmprj.planner.databinding.FragmentSplashBinding
import com.bmprj.planner.utils.toast

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {



    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                continueWithAction()
            } else {
                toast("Bildirim izni verilmedi.")
            }
        }

    private fun continueWithAction() {
        val action = SplashFragmentDirections.actionSplashFragmentToNotesFragment()
        findNavController().navigate(action)
    }


    override fun initView(view: View) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                continueWithAction()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


}