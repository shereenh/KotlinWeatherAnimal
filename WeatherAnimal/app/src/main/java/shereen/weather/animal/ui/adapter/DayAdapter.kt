package shereen.weather.animal.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.day_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.ui.inflate
import shereen.weather.animal.viewmodel.entity.DayView

class DayAdapter(var mContext: Context, private val itemClickListener: (DayView) -> Unit): RecyclerView.Adapter<DayAdapter.DayHolder>() {

    private var dayList = arrayListOf<DayView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.DayHolder {
        val inflatedView = parent.inflate(R.layout.day_item, false)  //executes the extension function
        return DayAdapter.DayHolder(inflatedView)
    }

    override fun getItemCount() = dayList.size

    override fun onBindViewHolder(holder: DayAdapter.DayHolder, position: Int) {
        val dayItem = dayList[position]
        holder.bind(mContext, dayItem, itemClickListener)
    }

    fun setData(newData: List<DayView>){
        //make more efficient later
        dayList.clear()
        dayList.addAll(newData)
        notifyDataSetChanged()
    }

    fun clearData(){
        dayList.clear()
        notifyDataSetChanged()
    }

    class DayHolder(v: View) : RecyclerView.ViewHolder(v){

        private var view: View = v
        private var day: String? = null
        private var temp: String? = null


        fun bind(context: Context, dayEntity: DayView, itemClickListener: (DayView) -> Unit){
            this.day = dayEntity.day
            this.temp = dayEntity.temp

            view.day.text = day
            view.dayTemp.text = temp
            view.dayImg.setImageDrawable(context.resources.getDrawable(dayEntity.icon))
            view.setOnClickListener{itemClickListener(dayEntity)}

        }

    }


}