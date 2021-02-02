package com.phover.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phover.R
import com.phover.model.RoverPhoto
import com.phover.utility.GridItemDecoration
import com.phover.utility.PhotoClickListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoverFragment : Fragment(R.layout.fragment_rover) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CollectionAdapter
    private var job: Job? = null
    private var rover = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getString("rover")?.let { rover = it }
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val textView: TextView = view.findViewById(R.id.text_view)
        val button: Button = view.findViewById(R.id.button)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = CollectionAdapter(object : PhotoClickListener {
            override fun onClick(photo: RoverPhoto) {
                val bundle = Bundle()
                bundle.putSerializable("photo", photo)
                val fragment = DetailsFragment()
                fragment.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }
        })

        val space: Int = resources.getDimensionPixelSize(R.dimen.dimension_value)
        val decoration = GridItemDecoration(3, space, true)
        recyclerView.addItemDecoration(decoration)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = NetworkStateAdapter { adapter.retry() },
            footer = NetworkStateAdapter { adapter.retry() }
        )

        button.setOnClickListener { adapter.retry() }
        adapter.addLoadStateListener {
            progressBar.isVisible = it.source.refresh is LoadState.Loading
            button.isVisible = it.source.refresh is LoadState.Error
            textView.isVisible = it.source.refresh is LoadState.Error
            recyclerView.isVisible = it.source.refresh !is LoadState.Error
        }

        if (rover == "curiosity") refreshCuriosity(null)
        if (rover == "opportunity") refreshOpportunity(null)
        if (rover == "spirit") refreshSpirit(null)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (rover == "curiosity") {
            inflater.inflate(R.menu.curiosity_menu, menu)
            if (R.id.filter1 == viewModel.curiosityFilter) menu.findItem(R.id.filter1).isChecked = true
            if (R.id.filter2 == viewModel.curiosityFilter) menu.findItem(R.id.filter2).isChecked = true
            if (R.id.filter3 == viewModel.curiosityFilter) menu.findItem(R.id.filter3).isChecked = true
            if (R.id.filter4 == viewModel.curiosityFilter) menu.findItem(R.id.filter4).isChecked = true
            if (R.id.filter5 == viewModel.curiosityFilter) menu.findItem(R.id.filter5).isChecked = true
            if (R.id.filter6 == viewModel.curiosityFilter) menu.findItem(R.id.filter6).isChecked = true
            if (R.id.filter7 == viewModel.curiosityFilter) menu.findItem(R.id.filter7).isChecked = true
            if (R.id.filter8 == viewModel.curiosityFilter) menu.findItem(R.id.filter8).isChecked = true
        }
        if (rover == "opportunity") {
            inflater.inflate(R.menu.opportunity_menu, menu)
            if (R.id.filter1 == viewModel.opportunityFilter) menu.findItem(R.id.filter1).isChecked = true
            if (R.id.filter2 == viewModel.opportunityFilter) menu.findItem(R.id.filter2).isChecked = true
            if (R.id.filter3 == viewModel.opportunityFilter) menu.findItem(R.id.filter3).isChecked = true
            if (R.id.filter4 == viewModel.opportunityFilter) menu.findItem(R.id.filter4).isChecked = true
            if (R.id.filter5 == viewModel.opportunityFilter) menu.findItem(R.id.filter5).isChecked = true
            if (R.id.filter6 == viewModel.opportunityFilter) menu.findItem(R.id.filter6).isChecked = true
        }
        if (rover == "spirit") {
            inflater.inflate(R.menu.spirit_menu, menu)
            if (R.id.filter1 == viewModel.spiritFilter) menu.findItem(R.id.filter1).isChecked = true
            if (R.id.filter2 == viewModel.spiritFilter) menu.findItem(R.id.filter2).isChecked = true
            if (R.id.filter3 == viewModel.spiritFilter) menu.findItem(R.id.filter3).isChecked = true
            if (R.id.filter4 == viewModel.spiritFilter) menu.findItem(R.id.filter4).isChecked = true
            if (R.id.filter5 == viewModel.spiritFilter) menu.findItem(R.id.filter5).isChecked = true
            if (R.id.filter6 == viewModel.spiritFilter) menu.findItem(R.id.filter6).isChecked = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter1) {
            item.isChecked = true
            if (rover == "curiosity") {
                refreshCuriosity(null)
                viewModel.curiosityFilter = R.id.filter1
            }
            if (rover == "opportunity") {
                refreshOpportunity(null)
                viewModel.opportunityFilter = R.id.filter1
            }
            if (rover == "spirit") {
                refreshSpirit(null)
                viewModel.spiritFilter = R.id.filter1
            }
        } else if (item.groupId == R.id.filter_group) {
            item.isChecked = true
            if (rover == "curiosity") {
                refreshCuriosity(item.title.toString())
                viewModel.curiosityFilter = item.itemId
            }
            if (rover == "opportunity") {
                refreshOpportunity(item.title.toString())
                viewModel.opportunityFilter = item.itemId
            }
            if (rover == "spirit") {
                refreshSpirit(item.title.toString())
                viewModel.spiritFilter = item.itemId
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshCuriosity(camera: String?) {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getCuriosity(camera).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun refreshOpportunity(camera: String?) {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getOpportunity(camera).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun refreshSpirit(camera: String?) {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getSpirit(camera).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}