package shereen.weather.animal.ui.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.saved_city_list_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.adapter.nested.RecyclerViewClickListener
import shereen.weather.animal.ui.inflate
import shereen.weather.animal.viewmodel.entity.CityView

class CitiesAdapter(var mContext: Context, private val itemClickListener: RecyclerViewClickListener): RecyclerView.Adapter<CitiesAdapter.CitiesHolder>() {

    private var cityList = arrayListOf<CityView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesAdapter.CitiesHolder {
        val inflatedView = parent.inflate(R.layout.saved_city_list_item, false)  //executes the extension function
        return CitiesAdapter.CitiesHolder(inflatedView)
    }

    override fun getItemCount()  = cityList.size

    override fun onBindViewHolder(holder: CitiesAdapter.CitiesHolder, position: Int) {
        val cityItem = cityList[position]
        holder.bind(mContext, cityItem, itemClickListener)
    }

    fun setData(newData: List<CityView>){
        cityList.clear()
        cityList.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int){
        cityList.removeAt(position)
        notifyItemRemoved(position)
    }

    class CitiesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{


        private var view: View = v
        private var cityName: String? = null
        private var cityTemp: String? = null
        private var cityTemp_min: String? = null
        private var cityTemp_max: String? = null
        private var cityDesc: String? = null
        private var cityImg: Int? = null

        private var itemClickListener: RecyclerViewClickListener? = null

        override fun onClick(v: View?) {
            itemClickListener!!.recyclerViewListClicked(v!!, this.position)
        }

        fun bind(context: Context, cityView: CityView, itemClickListener: RecyclerViewClickListener){
            this.cityName = cityView.city
            this.cityTemp = cityView.temp
            this.cityTemp_min = cityView.min_temp
            this.cityTemp_max = cityView.max_temp
            this.cityDesc = cityView.description
            this.cityImg = cityView.icon
            this.itemClickListener = itemClickListener

            view.cityName.text = cityName
            view.cityTemp.text = cityTemp.toString()
            view.maxTemp.text = cityTemp_max.toString()
            view.minTemp.text = cityTemp_min.toString()
            view.cityDesc.text = cityDesc
            view.cityImg.setImageDrawable(context.resources.getDrawable(cityView.icon))
//            view.setOnClickListener{itemClickListener(cityEntity)}
            view.setOnClickListener(this)


        }

    }
}