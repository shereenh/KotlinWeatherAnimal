package shereen.weather.animal.ui.helper

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.animal_line.view.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants

import shereen.weather.animal.model.source.AnimalUrlSource.Companion.mediumArcticAnimalList
import shereen.weather.animal.model.source.AnimalUrlSource.Companion.mediumJungleAnimalList
import shereen.weather.animal.model.source.AnimalUrlSource.Companion.mediumDesertAnimalList
import shereen.weather.animal.model.source.AnimalUrlSource.Companion.mediumDomesticAnimalList


class ImageChanger(private val context: Context, private val rootView: View) {

    val glide: RequestManager = Glide.with(context)
    val imgViews = listOf<ImageView>(rootView.imageLine1, rootView.imageLine2, rootView.imageLine3, rootView.imageLine4, rootView.imageLine5, rootView.imageLine6)

    fun glider(link: String, view: ImageView){
        glide
            .load(link)
            .apply(RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.imagenotfound))
            .into(view)
    }

    fun setImages(theme: String){
        var mList : MutableList<String> = mutableListOf()
        when(theme){
            WConstants.JUNGLE -> mList = mediumJungleAnimalList as MutableList<String>
            WConstants.ARCTIC -> mList = mediumArcticAnimalList as MutableList<String>
            WConstants.DESERT -> mList = mediumDesertAnimalList as MutableList<String>
            WConstants.DOMESTIC -> mList = mediumDomesticAnimalList as MutableList<String>
        }

        for(i in 0..5){
            glider(mList[i], imgViews[i])
        }

    }

}

