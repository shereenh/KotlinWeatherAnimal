package shereen.weather.animal.ui.adapter.nested

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.day_item.view.*
import kotlinx.android.synthetic.main.day_view.view.*
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.viewmodel.entity.DayView
import kotlin.math.min

class DaySetter(val mContext: Context, val view: View) {

    var dayList = mutableListOf<DayView>()
    private val frameList = listOf<FrameLayout>(view.frame1, view.frame2, view.frame3, view.frame4, view.frame5)

    fun setHeight(){
        val mParams = frameList[0].layoutParams
        val width = mParams.width
        mParams.height = width
        frameList[0].layoutParams = mParams
        frameList[0].postInvalidate()
    }

    fun setList(dataList: List<DayView>){
        dayList.clear()
        for(item in dataList){
            dayList.add(item)
        }
        setDays()
//        setHeight()
    }

    fun setDays(){
        val listSize = min(dayList.size, frameList.size)
        for(i in 0 until listSize){
            frameList[i].day.text = dayList[i].day
            frameList[i].dayTemp.text = dayList[i].temp
            frameList[i].dayImg.setImageDrawable(mContext.resources.getDrawable(dayList[i].icon))
            frameList[i].setOnClickListener{ Log.d(WConstants.LOGGER, "Clicked day: "+ dayList[i].day)}
        }
    }

    fun changeSelected(){

    }
}