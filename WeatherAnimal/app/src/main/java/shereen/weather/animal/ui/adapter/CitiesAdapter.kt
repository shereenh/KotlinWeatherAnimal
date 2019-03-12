package shereen.weather.animal.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.saved_city_list_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.room.entity.CityEntity
import shereen.weather.animal.ui.inflate

class CitiesAdapter(var mContext: Context, private val itemClickListener: (CityEntity) -> Unit): RecyclerView.Adapter<CitiesAdapter.CitiesHolder>() {

    private var cityList = arrayListOf<CityEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesAdapter.CitiesHolder {
        val inflatedView = parent.inflate(R.layout.saved_city_list_item, false)  //executes the extension function
        return CitiesAdapter.CitiesHolder(inflatedView)
    }

    override fun getItemCount()  = cityList.size

    override fun onBindViewHolder(holder: CitiesAdapter.CitiesHolder, position: Int) {
        val cityItem = cityList[position]
        holder.bind(mContext, cityItem, itemClickListener)
    }

    fun setData(newData: List<CityEntity>){
        //make more efficient later
        cityList.clear()
        for(item in newData){
            cityList.add(item)
        }
//        cityList = newData as ArrayList<CityEntity>
        notifyDataSetChanged()
    }

    class CitiesHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v
        private var cityName: String? = null
        private var cityTemp: Double? = null
        private var cityImg: Int? = null

        fun bind(context: Context, cityEntity: CityEntity, itemClickListener: (CityEntity) -> Unit){
            this.cityName = cityEntity.name
            this.cityTemp = cityEntity.temp
            this.cityImg = cityEntity.icon

            view.cityName.text = cityName
            view.cityTemp.text = cityTemp.toString()
            view.cityImg.setImageDrawable(context.resources.getDrawable(cityEntity.icon))
            view.setOnClickListener{itemClickListener(cityEntity)}

        }

    }
}