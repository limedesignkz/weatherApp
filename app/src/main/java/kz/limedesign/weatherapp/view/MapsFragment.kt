package kz.limedesign.weatherapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kz.limedesign.weatherapp.R
import kz.limedesign.weatherapp.utils.GPS_REQUEST
import kz.limedesign.weatherapp.utils.Gps
import kz.limedesign.weatherapp.utils.LOCATION_REQUEST
import kz.limedesign.weatherapp.utils.Logger
import kz.limedesign.weatherapp.viewmodel.MapsViewModel

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel: MapsViewModel by viewModels()

    private var isGPSEnabled = false

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(54.0, 76.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //googleMap.isMyLocationEnabled = false
    }

    companion object {
        fun newInstance() = MapsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = false
        Gps(requireActivity())
            .turnGPSOn(object : Gps.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                Logger.d()
                this@MapsFragment.isGPSEnabled = isGPSEnable
            }
        })
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> Logger.d()//latLong.text = getString(R.string.enable_gps)

            isPermissionsGranted() -> {
                Logger.d()
                startLocationUpdate()
            }

            shouldShowRequestPermissionRationale() -> Logger.d()//latLong.text = getString(R.string.permission_request)

            else -> {
                Logger.d()
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_REQUEST
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun startLocationUpdate() {
        viewModel.locationData.observe(viewLifecycleOwner, Observer {
            Logger.d(it.longitude.toString(), it.latitude.toString())
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED


    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                && ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}