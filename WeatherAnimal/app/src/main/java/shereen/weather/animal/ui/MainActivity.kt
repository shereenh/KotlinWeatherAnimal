package shereen.weather.animal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import shereen.weather.animal.R
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.fragments.AddFragment
import shereen.weather.animal.ui.fragments.HomeFragment
import shereen.weather.animal.ui.fragments.SettingsFragment
import shereen.weather.animal.viewmodel.MainViewModel
import android.os.StrictMode






class MainActivity : AppCompatActivity(), SettingsFragment.OnFragmentInteractionListener {

    lateinit var mViewModel: MainViewModel
    lateinit var theme: String
    lateinit var settingsFragment: SettingsFragment
    lateinit var addFragment: AddFragment
    lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listeners()
        observers()
    }

    private fun init(){
        settingsFragment = SettingsFragment()
        addFragment = AddFragment()
        homeFragment = HomeFragment()
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        firsTimeAction(mViewModel.checkFirstTime())
        theme = mViewModel.theme
        setTheme()
        startAdd()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        mViewModel.deleteAllCities()
    }

    private fun listeners(){
        bottom_navigation.setOnNavigationItemSelectedListener(
            object: BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(@NonNull item: MenuItem):Boolean {
                    when (item.itemId) {
                        R.id.add -> startAdd()
                        R.id.home -> startHome()
                        R.id.settings -> startSettings()
                    }
                    return true
                }
            })
    }

    private fun observers(){

    }

    fun startSettings(){
        title = resources.getString(R.string.settings)
        supportFragmentManager.inTransaction {
            replace(R.id.frameLayout, settingsFragment)
        }
    }

    fun startHome(){
        title = resources.getString(R.string.home)
        supportFragmentManager.inTransaction {
            replace(R.id.frameLayout, homeFragment)
        }
    }

    fun startAdd(){
        title = resources.getString(R.string.add)
        supportFragmentManager.inTransaction {
            replace(R.id.frameLayout, addFragment)
        }
    }

    private fun setTheme(){
        when(theme){
            WConstants.JUNGLE -> mainLayout.setBackgroundColor(resources.getColor(R.color.dark_green))
            WConstants.ARCTIC -> mainLayout.setBackgroundColor(resources.getColor(R.color.blue))
            WConstants.DESERT -> mainLayout.setBackgroundColor(resources.getColor(R.color.brown))
            WConstants.DOMESTIC -> mainLayout.setBackgroundColor(resources.getColor(R.color.pink))
        }
    }

    private fun firsTimeAction(value: Boolean){
        if(value){
            mainLayout.setBackgroundColor(resources.getColor(R.color.red))
//            callClimateActivity()
        }else{
            mainLayout.setBackgroundColor(resources.getColor(R.color.blue))
        }
    }

    override fun callClimateActivity(){
        val intent = Intent(this@MainActivity, ClimateActivity::class.java)
        startActivity(intent)
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

}
