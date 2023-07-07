package com.mohamed.ai_app.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mohamed.ai_app.R
import com.mohamed.ai_app.databinding.FragmentHomeBinding
import com.mohamed.ai_app.helpers.Utils.hasPermissions

/**
 * Created by Mohamed Hashim on 06/07/2023.
 */

class HomeFragment : Fragment() {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null

    private val fragmentHomeBinding
        get() = _fragmentHomeBinding!!

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    context,
                    "Permission request granted",
                    Toast.LENGTH_LONG,
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Permission request denied",
                    Toast.LENGTH_LONG,
                ).show()
            }
        }

    override fun onResume() {
        super.onResume()
        if (!hasPermissions(requireContext())) {
            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_container,
            ).navigate(R.id.action_camera_to_home)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA,
            ),
            -> {
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        fragmentHomeBinding.imgObjectDetection.setOnClickListener {
            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_container,
            ).navigate(HomeFragmentDirections.actionHomeToObjectDetectorCamera())
        }

        fragmentHomeBinding.imgGestureRecognition.setOnClickListener {
            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_container,
            ).navigate(HomeFragmentDirections.actionHomeToGestureRecognizerCamera())
        }
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _fragmentHomeBinding = null
        super.onDestroyView()
    }
}
