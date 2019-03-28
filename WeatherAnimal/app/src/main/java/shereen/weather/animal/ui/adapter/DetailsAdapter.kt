package shereen.weather.animal.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.detail_pager_item.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.Helper

import shereen.weather.animal.ui.adapter.nested.DaySetter
import shereen.weather.animal.ui.inflate
import shereen.weather.animal.viewmodel.entity.DetailView

class DetailsAdapter(var mContext: Context): RecyclerView.Adapter<DetailsAdapter.DetailsHolder>() {

    private var detailList = arrayListOf<DetailView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsAdapter.DetailsHolder {
        val inflatedView = parent.inflate(R.layout.detail_pager_item, false)  //executes the extension function
        return DetailsAdapter.DetailsHolder(inflatedView)
    }

    override fun getItemCount() = detailList.size

//    rootView.detailRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//    detailAdapter = DetailsAdapter(this.activity!!)
//    val snapHelper: SnapHelper = PagerSnapHelper()
//    snapHelper.attachToRecyclerView(rootView.detailRecycler)
//    rootView.detailRecycler.addItemDecoration(LinePagerIndicatorDecoration())
//    rootView.detailRecycler.adapter = detailAdapter

    override fun onBindViewHolder(holder: DetailsAdapter.DetailsHolder, position: Int) {
        val detailItem = detailList[position]
        holder.bind(mContext, detailItem)

        holder.view.hourRecycler.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        val hourAdapter = HourAdapter(mContext)
//        Helper.printAll(detailItem)
        hourAdapter.setData(detailItem.day[0].hourList)
        holder.view.hourRecycler.adapter = hourAdapter
    }

    fun setData(newData: List<DetailView>){
        //make more efficient later
        detailList.clear()
        detailList.addAll(newData)
        notifyDataSetChanged()
    }

    class DetailsHolder(v: View) : RecyclerView.ViewHolder(v){

        var view: View = v
        private var cityName: String? = null
        private var cityTemp: String? = null
        private var description: String? = null
        private var minTemp: String? = null
        private var maxTemp: String? = null
        private var daySetter: DaySetter? = null

        fun bind(context: Context, detailView: DetailView){
            this.cityName = detailView.cityView.city
            this.cityTemp = detailView.cityView.temp
            this.description = detailView.cityView.description
            this.minTemp = detailView.cityView.min_temp
            this.maxTemp = detailView.cityView.max_temp

            view.cityName.text = cityName
            view.temp.text = cityTemp
            view.minTemp.text = minTemp
            view.maxTemp.text = maxTemp
            view.description.text = description
            view.icon.setImageDrawable(context.resources.getDrawable(detailView.cityView.icon))

            daySetter = DaySetter(context, view)
            daySetter!!.setList(detailView.day)



//            val dayItemOnClick: (Day) -> Unit = { day ->
//                Log.d(WConstants.LOGGER, " Day Clicked! " + day.day)
//            }
//            view.dayRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            val dayAdapter = DayAdapter(view.dayRecycler.context, dayItemOnClick)
//            dayAdapter.setData(detailEntity.day)
//            view.dayRecycler.adapter = dayAdapter


//            view.linearBack.setBackgroundDrawable(context.resources.getDrawable(detailEntity.icon))

//            view.setOnClickListener{itemClickListener(cityEntity)}
//            val dayLayoutManager = LinearLayoutManager(view.dayRecycler.context, LinearLayout.HORIZONTAL, false)
//            dayLayoutManager.initialPrefetchItemCount = 3
//
//            val dayItemOnClick: (Day) -> Unit = { day ->
//                Log.d(WConstants.LOGGER, " Day Clicked! " + day.day)
//            }
//
//            view.dayRecycler.apply{
//                layoutManager= dayLayoutManager
//                adapter= DayAdapter(context, dayItemOnClick)
//                setRecycledViewPool(viewPool)
//
//            }


        }

    }
}