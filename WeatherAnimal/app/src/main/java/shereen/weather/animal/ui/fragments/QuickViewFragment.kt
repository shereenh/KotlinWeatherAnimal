package shereen.weather.animal.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.detail_pager_item.view.*
import kotlinx.android.synthetic.main.fragment_quick_view.view.*

import shereen.weather.animal.model.WConstants
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.ui.adapter.CitiesAdapter
import shereen.weather.animal.ui.adapter.nested.RecyclerViewClickListener
import shereen.weather.animal.ui.adapter.nested.SwipeToDeleteCallback
import shereen.weather.animal.viewmodel.MainViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import shereen.weather.animal.viewmodel.entity.CityView

class QuickViewFragment : Fragment(), RecyclerViewClickListener {

    lateinit var rootView: View
    lateinit var mViewModel : MainViewModel
    lateinit var cityAdapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run{ ViewModelProviders.of(this).get(MainViewModel::class.java)}?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(shereen.weather.animal.R.layout.fragment_quick_view, container, false)
        observers()
        init()
        listeners()
        return rootView
    }

    fun init(){
        // saved cities list
        rootView.cityRecycler.layoutManager = LinearLayoutManager(activity)
        rootView.cityRecycler.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        cityAdapter = CitiesAdapter(this.activity!!, this)
        rootView.cityRecycler.adapter = cityAdapter
        val swipeHandler = object: SwipeToDeleteCallback(activity!!.applicationContext){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d(WConstants.LOGGER, "Trying to remove: " + viewHolder.adapterPosition +" "+ viewHolder.itemView.cityName.text)
                cityAdapter.removeAt(viewHolder.adapterPosition)
                mViewModel.deleteCity(viewHolder.itemView.cityName.text.toString())
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rootView.cityRecycler)

        rootView.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

    }

    private fun observers(){
//        mViewModel.getsavedCities().observe(this, Observer<List<CityEntity>>{
//                savedList -> cityAdapter.setData(savedList)
//        })

        mViewModel.cityViewList.observe(this, Observer<List<CityView>>{
            cityList -> cityAdapter.setData(cityList)
        })
    }

    private fun listeners(){
        rootView.swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            // Your code to refresh the list here.

            // Make sure you call swipeContainer.setRefreshing(false)

            // once the network request has completed successfully.

            mViewModel.refreshCities()
            rootView.swipeContainer.isRefreshing = false
        })
    }

    override fun recyclerViewListClicked(v: View, pos: Int) {
        Log.d(WConstants.LOGGER, "Clicked! " + pos)
        mViewModel.currentFragment.value = WConstants.HOME
        mViewModel.currentWeatherIndex.value = pos
    }
}
