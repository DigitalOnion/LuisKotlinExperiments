package com.outerspace.kotlinexperiments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.outerspace.kotlinexperiments.databinding.ActivityMainBinding
import com.outerspace.kotlinexperiments.databinding.FragmentFirstBinding

// read: https://stackoverflow.com/questions/61306719/onactivitycreated-is-deprecated-how-to-properly-use-lifecycleobserver

class FirstFragment(val experimentIndex: Int) : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var experimentVM: ExperimentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        experimentVM = ViewModelProvider(this)[ExperimentViewModel::class.java]
    }

    lateinit var names: Array<String>
    lateinit var hints: Array<String>
    lateinit var defaults: Array<String>

    override fun onStart() {
        names = resources.getStringArray(R.array.experiment_names)
        hints = resources.getStringArray(R.array.hints)
        defaults = resources.getStringArray(R.array.default_entries)

        super.onStart()
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val previousEntry = sharedPreferences.getString(names[experimentIndex], defaults[experimentIndex])
        binding.firstExperimentEntry.text = SpannableStringBuilder(previousEntry)
        binding.firstExperimentEntry.hint = SpannableStringBuilder(hints[experimentIndex])
        binding.experimentDescription.text = SpannableStringBuilder(names[experimentIndex])
        binding.firstExperimentLayout.visibility = if (hints[experimentIndex] != "-") View.VISIBLE else View.GONE

        binding.firstExperimentButton.setOnClickListener {
            var entry = binding.firstExperimentEntry.text.toString()
            if (entry.isEmpty()) {
                entry = defaults[experimentIndex]
                binding.firstExperimentEntry.text = SpannableStringBuilder(entry)
            }

            sharedPreferences.edit()
                .putString(FIRST_ENTRY, entry)
                .apply()
            experimentVM.runExperiment(experimentIndex, entry)
        }

        experimentVM.mutableExperimentResult.observe(this) {
            binding.firstExperimentResult.text = it
        }
    }
}