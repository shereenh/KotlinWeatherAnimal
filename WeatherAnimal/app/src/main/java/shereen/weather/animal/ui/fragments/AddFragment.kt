package shereen.weather.animal.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add.view.*
import shereen.weather.animal.model.WConstants
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.ui.adapter.CitiesAdapter
import shereen.weather.animal.ui.adapter.PlacesAdapter
import shereen.weather.animal.viewmodel.MainViewModel
import android.content.Context
import android.view.inputmethod.InputMethodManager


class AddFragment : Fragment(){

    lateinit var rootView: View
    lateinit var mViewModel : MainViewModel

    lateinit var autoAdapter: PlacesAdapter
    lateinit var cityAdapter: CitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run{ ViewModelProviders.of(this).get(MainViewModel::class.java)}?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(shereen.weather.animal.R.layout.fragment_add, container, false)
        observers()
        init()
        listeners()
        return rootView
    }

    private fun init(){
        //auto-complete list
        rootView.autoRecycler.layoutManager = LinearLayoutManager(activity)//LinearLayoutManager(activity)
        val itemOnClick: (String) -> Unit = { city ->
            Log.d(WConstants.LOGGER, city)
//            rootView.city_name.setText(city)
            rootView.autoRecycler.visibility = View.GONE
            mViewModel.saveCity(city)
            hideTextbox()

        }
        autoAdapter = PlacesAdapter(itemClickListener = itemOnClick)
        rootView.autoRecycler.adapter = autoAdapter

        // saved cities list
        rootView.cityRecycler.layoutManager = LinearLayoutManager(activity)
        val cityItemOnClick: (CityEntity) -> Unit = { city ->
            Log.d(WConstants.LOGGER, "Clicked! " + city.name)
        }
        cityAdapter = CitiesAdapter(this.activity!!, itemClickListener = cityItemOnClick)
        rootView.cityRecycler.adapter = cityAdapter
    }

    private fun listeners(){

        rootView.addCityButton.setOnClickListener{
//            rootView.city_name.setText("")
            showTextbox()
        }

        rootView.city_name.setOnClickListener{
            rootView.city_name.setText("")
        }

        rootView.city_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if(input == ""){
                    autoAdapter.clearData()
                } else if(!input.contains(",")){
                    mViewModel.requestAutoPredictions(input)
                    rootView.autoRecycler.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable) {} })
    }

    private fun observers(){
        mViewModel.getAutoPredictionList().observe(this, Observer<List<String>>{
            newList -> autoAdapter.setData(newList)
        })

        mViewModel.getsavedCities().observe(this, Observer<List<CityEntity>>{
            savedList -> cityAdapter.setData(savedList)
        })
    }

    fun hideTextbox(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity!!.window.currentFocus!!.windowToken, 0)
//        rootView.city_name.clearFocus()
        rootView.city_name.visibility = View.GONE

    }

    fun showTextbox(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        rootView.city_name.visibility = View.VISIBLE
        rootView.city_name.setText("")
        rootView.city_name.requestFocus()
    }



}
