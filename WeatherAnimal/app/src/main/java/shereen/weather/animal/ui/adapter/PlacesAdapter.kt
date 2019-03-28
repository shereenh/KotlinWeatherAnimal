package shereen.weather.animal.ui.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.autocomplete_list_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.inflate
import java.util.*

class PlacesAdapter(private val itemClickListener: (String) -> Unit): RecyclerView.Adapter<PlacesAdapter.PlacesHolder>() {

    private var placeList = arrayListOf<String>()


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PlacesAdapter.PlacesHolder {
        val inflatedView = parent.inflate(R.layout.autocomplete_list_item, false)  //executes the extension function
        return PlacesHolder(inflatedView)
    }

    override fun getItemCount():Int = placeList.size

    override fun onBindViewHolder(holder: PlacesAdapter.PlacesHolder, position: Int) {
        val cityItem = placeList[position]
        holder.bind(cityItem, itemClickListener)
    }

    fun setData(newData: List<String>){
        placeList.clear()
        placeList.addAll(newData)
        placeList.add("Google")
        Log.d(WConstants.LOGGER, "-------------size: "+ Arrays.asList(placeList))
        notifyDataSetChanged()
    }

    fun clearData(){
        placeList.clear()
        notifyDataSetChanged()
    }

    class PlacesHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v
//        private var city: String? = null


        fun bind(city: String, itemClickListener: (String) -> Unit){
//            this.city = cityArg
            if(city == "Google"){
                view.googleImg.visibility = View.VISIBLE
                view.autocompleteText.visibility = View.GONE
//                view.autocompleteText.text = city

            }else{
                view.autocompleteText.visibility = View.VISIBLE
                view.autocompleteText.text = city
                view.googleImg.visibility = View.GONE
                view.setOnClickListener{ itemClickListener(city)}

            }
        }

    }
}