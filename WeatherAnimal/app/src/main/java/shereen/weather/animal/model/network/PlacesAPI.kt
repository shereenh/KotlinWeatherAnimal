package shereen.weather.animal.model.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import shereen.weather.animal.model.WConstants


class PlacesAPI(val mContext: Context) {

//    val mListener: PlacesListener
    private var placesClient: PlacesClient
    private var token: AutocompleteSessionToken

    var mutablePlacesList = MutableLiveData<List<String>>()
    private var placesList = arrayListOf<String>()


    init {
        Places.initialize(mContext, "AIzaSyDUXAYlWMiWxDRgnekL_3zYehT0Avz0N50")
        placesClient = Places.createClient(mContext)
        token = AutocompleteSessionToken.newInstance()
    }

    fun autoPredict(input: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setCountry("us")
            .setTypeFilter(TypeFilter.CITIES)
            .setSessionToken(token)
            .setQuery(input)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            dealWithResponse(response.autocompletePredictions)
//            mListener.addToAdapter(response.autocompletePredictions)

        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e(WConstants.LOGGER, "Place not found: " + exception.statusCode)

            }
        }
    }

    private fun dealWithResponse(responses: List<AutocompletePrediction>){
        placesList.clear()
        for(item in responses){
            placesList.add(item.getFullText(null).toString())
        }
        mutablePlacesList.value = placesList
    }


//    public interface PlacesListener{
//        fun addToAdapter(predictions: List<AutocompletePrediction>)
//    }
}