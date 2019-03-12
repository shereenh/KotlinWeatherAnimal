package shereen.weather.animal.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_climate.*
import kotlinx.android.synthetic.main.theme_choice.*
import kotlinx.android.synthetic.main.animal_line.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.helper.ImageChanger
import shereen.weather.animal.viewmodel.ClimateViewModel

class ClimateActivity : AppCompatActivity() {

    lateinit var mViewModel: ClimateViewModel
    lateinit var imageChanger: ImageChanger
    lateinit var curTheme: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_climate)
        init()
        listeners()
        observers()
    }

    private fun init(){

        mViewModel = ViewModelProviders.of(this).get(ClimateViewModel::class.java)
        imageChanger = ImageChanger(this, climateMain)
        relativeMain.setBackgroundResource(R.color.dim)
        setAllDim()
    }

    private fun listeners(){
        jungleSelection.setOnClickListener{ mViewModel.changeSampledTheme(WConstants.JUNGLE) }
        arcticSelection.setOnClickListener{ mViewModel.changeSampledTheme(WConstants.ARCTIC) }
        desertSelection.setOnClickListener{ mViewModel.changeSampledTheme(WConstants.DESERT) }
        domesticSelection.setOnClickListener{ mViewModel.changeSampledTheme(WConstants.DOMESTIC) }
    }

    private fun observers(){
        mViewModel.sampledThemeLive.observe(this, Observer<String>{ response ->
            Log.d(WConstants.LOGGER, "observer sampledThemeLive")
            changePictures(response)})
    }

    private fun changePictures(theme: String){
        curTheme = theme
        setAllDim()
        imageChanger.setImages(curTheme)
        when(theme){
            WConstants.JUNGLE -> { setFocused(jungleText)
                                    climateMain.setBackgroundResource(R.drawable.jungle)
                                    firstDecorator.setBackgroundResource(R.color.dark_green)
                                    goButton.setBackgroundResource(R.color.dark_green)}
            WConstants.ARCTIC -> { setFocused(arcticText)
                climateMain.setBackgroundResource(R.drawable.arctic)
                firstDecorator.setBackgroundResource(R.color.blue)
                goButton.setBackgroundResource(R.color.blue)}
            WConstants.DESERT -> { setFocused(desertText)
                climateMain.setBackgroundResource(R.drawable.desert)
                firstDecorator.setBackgroundResource(R.color.brown)
                goButton.setBackgroundResource(R.color.brown)}
            WConstants.DOMESTIC -> { setFocused(domesticText)
                climateMain.setBackgroundResource(R.drawable.indoor)
                firstDecorator.setBackgroundResource(R.color.black)
                goButton.setBackgroundResource(R.color.black)}
        }
    }

    private fun setFocused(view: TextView){
        view.setBackgroundColor(Color.TRANSPARENT)
        view.setTypeface(null, Typeface.BOLD)
    }

    private fun setAllDim(){

        jungleText.setBackgroundResource(R.color.dim)
        arcticText.setBackgroundResource(R.color.dim)
        desertText.setBackgroundResource(R.color.dim)
        domesticText.setBackgroundResource(R.color.dim)
        jungleText.setTypeface(null, Typeface.NORMAL)
        arcticText.setTypeface(null, Typeface.NORMAL)
        desertText.setTypeface(null, Typeface.NORMAL)
        domesticText.setTypeface(null, Typeface.NORMAL)
    }

//    fun startTask(){
//
//        if (mHandler == null){
//            mHandler = Handler()
//        }
//        mRunnable = Runnable{
//            Log.d(WConstants.LOGGER, "from inside handler")
//            mViewModel.changeCurImage()
//            mHandler.postDelayed(mRunnable, 3000)
//        }
//        mHandler.postDelayed(mRunnable, 3000)
//    }


    override fun onResume() {
        super.onResume()
        myAnimator()
    }


    private fun myAnimator(){

        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 15000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width = imageLine1.width*2
            val translationX = width * progress *6
            imageLine1.translationX = translationX
            imageLine2.translationX = translationX - width
            imageLine3.translationX = translationX - 2 * width
            imageLine4.translationX = translationX - 3 * width
            imageLine5.translationX = translationX - 4 * width
            imageLine6.translationX = translationX -5 * width
        }
        animator.start()

    }

    fun changeToMainActivity(view: View){
        mViewModel.changeStoredTheme(curTheme)
        val intent = Intent(this@ClimateActivity, MainActivity::class.java)
        startActivity(intent)
    }

}
