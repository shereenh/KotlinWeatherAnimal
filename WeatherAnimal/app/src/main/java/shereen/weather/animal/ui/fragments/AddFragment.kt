package shereen.weather.animal.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add.view.*
import shereen.weather.animal.model.WConstants
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import shereen.weather.animal.ui.adapter.PlacesAdapter
import shereen.weather.animal.viewmodel.MainViewModel
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible


class AddFragment : Fragment(){

    lateinit var rootView: View
    lateinit var mViewModel : MainViewModel

    lateinit var autoAdapter: PlacesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run{ ViewModelProviders.of(this).get(MainViewModel::class.java)}?: throw Exception("Invalid Activity") as Throwable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(shereen.weather.animal.R.layout.fragment_add, container, false)
        observers()
        init()
        listeners()
        return rootView
    }

    private fun init(){
        Log.d(WConstants.LOGGER, "Add fragment called")
        //auto-complete list
        rootView.autoRecycler.layoutManager = LinearLayoutManager(activity)  //LinearLayoutManager(activity)
        val itemOnClick: (String) -> Unit = { city ->
            Log.d(WConstants.LOGGER, city)

            mViewModel.saveCity(city)
            mViewModel.changeCurrentFragment(WConstants.QUICK)

            mViewModel.menuItem.collapseActionView()

        }
        autoAdapter = PlacesAdapter(itemClickListener = itemOnClick)
        rootView.autoRecycler.adapter = autoAdapter

        (mViewModel.menuItem.actionView as SearchView).setQuery("", true)
        mViewModel.clearAutoPredictions()
        recyclerHandler()

    }

    private fun listeners(){

        (mViewModel.menuItem.actionView as SearchView).setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query:String):Boolean {
                return false
            }
            override fun onQueryTextChange(newText:String):Boolean {
//                adapter.getFilter().filter(newText)
                recyclerHandler()
                Log.d(WConstants.LOGGER, "change: "+newText)
                if(newText == ""){
                    autoAdapter.clearData()
                }else{
                    mViewModel.requestAutoPredictions(newText)
                }
                return false
            }
        })

    }

    private fun observers() {
        mViewModel.getAutoPredictionList().observe(this, Observer<List<String>> { newList ->
            autoAdapter.setData(newList)
        })
    }

    private fun recyclerHandler(){
        if(autoAdapter.itemCount == 0){
            if(rootView.autoRecycler.isVisible){rootView.autoRecycler.visibility = View.GONE}
        }else{
            if(!rootView.autoRecycler.isVisible){rootView.autoRecycler.visibility = View.VISIBLE}
        }
    }

}
