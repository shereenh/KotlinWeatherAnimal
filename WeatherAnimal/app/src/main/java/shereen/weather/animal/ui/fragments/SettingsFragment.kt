package shereen.weather.animal.ui.fragments

import android.content.Context
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*

import shereen.weather.animal.R
import shereen.weather.animal.viewmodel.MainViewModel

class SettingsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    lateinit var rootView: View
    lateinit var mViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run{ViewModelProviders.of(this).get(MainViewModel::class.java)}?: throw Exception("Invalid Activity")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        init()
        listeners()
        observers()
        return rootView
    }

    private fun init(){
        rootView.themeSetting.text = mViewModel.theme.toUpperCase()
    }

    private fun listeners(){
        rootView.themeLinear.setOnClickListener{
            if(rootView.changeThemeButton.isVisible){
                rootView.changeThemeButton.visibility = View.GONE
            }else{
                rootView.changeThemeButton.visibility = View.VISIBLE
            }
        }

        rootView.changeThemeButton.setOnClickListener{ listener?.callClimateActivity() }
    }

    private fun observers(){

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun callClimateActivity()
    }

}
