package shereen.weather.animal.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.fragment_home.view.*

import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.adapter.DetailsAdapter
import shereen.weather.animal.ui.helper.LinePagerIndicatorDecoration
import shereen.weather.animal.viewmodel.MainViewModel
import shereen.weather.animal.viewmodel.entity.DetailView

class HomeFragment : Fragment() {

    lateinit var rootView: View
    lateinit var mViewModel : MainViewModel
    lateinit var detailAdapter: DetailsAdapter

    var curPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run{ ViewModelProviders.of(this).get(MainViewModel::class.java)}?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        init()
        observers()
        return rootView
    }

    fun init(){
        rootView.detailRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        detailAdapter = DetailsAdapter(this.activity!!)
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rootView.detailRecycler)
        rootView.detailRecycler.addItemDecoration(LinePagerIndicatorDecoration())
        rootView.detailRecycler.adapter = detailAdapter
    }

    private fun observers(){

//        mViewModel.getDetailCities().observe(this, Observer<List<DetailEntity>>{
//            detailList -> detailAdapter.setData(detailList)
//            Log.d(WConstants.LOGGER, "Got from detail DB")
//            rootView.detailRecycler.scrollToPosition(curPos)
//        })

        mViewModel.detailViewList.observe(this, Observer<List<DetailView>>{
                detailList -> detailAdapter.setData(detailList)
                Log.d(WConstants.LOGGER, "Got from detail DB")
                rootView.detailRecycler.scrollToPosition(curPos)
        })

        mViewModel.currentWeatherIndex.observe(this, Observer<Int>{
            currentIndex -> rootView.detailRecycler.scrollToPosition(currentIndex)
            curPos = currentIndex
            Log.d(WConstants.LOGGER, "Got from index changer: "+currentIndex)
        })
    }

}
