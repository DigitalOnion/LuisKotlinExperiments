package com.outerspace.kotlinexperiments

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

const val FIRST_ENTRY = "first-entry"

interface MainInterface {
    var resultReg: ActivityResultLauncher<Array<String>>
    val liveUri: MutableLiveData<Uri?>
}

class MainActivity : AppCompatActivity(), MainInterface {
    private lateinit var experimentVM: ExperimentViewModel
    private lateinit var viewPager: ViewPager2
    override lateinit var resultReg: ActivityResultLauncher<Array<String>>
    override val liveUri: MutableLiveData<Uri?> = MutableLiveData(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        experimentVM = ViewModelProvider(this as ViewModelStoreOwner)[ExperimentViewModel::class.java]

        resultReg = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            uri: Uri? ->
            if (uri != null) liveUri.value = uri
        }

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = object: FragmentStateAdapter(this) {
            val first: ExperimentFragment by lazy { ExperimentFragment(0) }
            val second: ExperimentFragment by lazy { ExperimentFragment(1) }
            val third: ExperimentFragment by lazy { ExperimentFragment(2)}
            val fourth: ExperimentFragment by lazy { ExperimentFragment(3)}
            val fifth: ExperimentFragment by lazy { ExperimentFragment(4)}

            val fragmentList: MutableList<Fragment> = mutableListOf(
                fifth, fourth, third, second, first
            )

            override fun getItemCount(): Int = fragmentList.size

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
    }
}