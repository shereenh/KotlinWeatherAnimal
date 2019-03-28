package shereen.weather.animal.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hour_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.Helper
import shereen.weather.animal.ui.inflate
import shereen.weather.animal.viewmodel.entity.HourView

class HourAdapter(var mContext: Context): RecyclerView.Adapter<HourAdapter.HourHolder>() {

    private var hourList = arrayListOf<HourView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourAdapter.HourHolder {
        val inflatedView = parent.inflate(R.layout.hour_item, false)  //executes the extension function
        return HourAdapter.HourHolder(inflatedView)
    }

    override fun getItemCount() = hourList.size

    override fun onBindViewHolder(holder: HourAdapter.HourHolder, position: Int) {
        val hourItem = hourList[position]
//        Helper.printHour(hourItem)
        holder.bind(mContext, hourItem)
    }

    fun setData(newData: List<HourView>){
        //make more efficient later
        hourList.clear()
        hourList.addAll(newData)
        notifyDataSetChanged()
    }

    class HourHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v
        private var hourVal: String? = null
        private var minTemp: String? = null
        private var maxTemp: String? = null


        fun bind(context: Context, hourItem: HourView){
            this.hourVal = hourItem.hour
            this.minTemp = hourItem.min
            this.maxTemp = hourItem.max

            view.hour.text = hourVal
            view.hourMax.text = maxTemp
            view.hourMin.text = minTemp
            view.hourImg.setImageDrawable(context.resources.getDrawable(hourItem.icon))
        }

    }
}