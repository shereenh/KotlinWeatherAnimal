package shereen.weather.animal.ui.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.autocomplete_list_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.inflate

class PlacesAdapter(private val itemClickListener: (String) -> Unit): RecyclerView.Adapter<PlacesAdapter.PlacesHolder>() {

    private var placeList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesAdapter.PlacesHolder {
        val inflatedView = parent.inflate(R.layout.autocomplete_list_item, false)  //executes the extension function
        return PlacesHolder(inflatedView)
    }

    override fun getItemCount() = placeList.size

    override fun onBindViewHolder(holder: PlacesAdapter.PlacesHolder, position: Int) {
        val cityItem = placeList[position]
        holder.bind(cityItem, itemClickListener)
    }

    fun setData(newData: List<String>){
        placeList.clear()
        for(city in newData){
            placeList.add(city)
        }
        placeList.add("Google")
        notifyDataSetChanged()
    }

    fun clearData(){
        placeList.clear()
        notifyDataSetChanged()
    }

    class PlacesHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v
        private var city: String? = null


        fun bind(city: String, itemClickListener: (String) -> Unit){
            this.city = city
            if(this.city == "Google"){
                view.googleImg.visibility = View.VISIBLE
            }else{
                view.autocompleteText.text = city
                view.googleImg.visibility = View.GONE
                view.setOnClickListener{ itemClickListener(city)}

            }
        }

    }
}