package com.octacore.agromallfarms.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.octacore.agromallfarms.R
import com.octacore.agromallfarms.ui.adapter.FarmersListAdapter

class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FarmersListAdapter
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        initViews(root)
        return root
    }

    private fun initViews(root: View) {
        recyclerView = root.findViewById(R.id.recyclerView)
        adapter = FarmersListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onStart() {
        super.onStart()
        dashboardViewModel.farmers.observe(this, Observer { farmers ->
            adapter.loadFarmers(farmers)
        })
    }
}
