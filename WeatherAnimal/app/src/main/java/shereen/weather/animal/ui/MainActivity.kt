package shereen.weather.animal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import shereen.weather.animal.model.WConstants
import shereen.weather.animal.ui.fragments.AddFragment
import shereen.weather.animal.ui.fragments.HomeFragment
import shereen.weather.animal.ui.fragments.SettingsFragment
import shereen.weather.animal.viewmodel.MainViewModel
import android.os.StrictMode
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import shereen.weather.animal.R
import shereen.weather.animal.ui.fragments.QuickViewFragment
import androidx.core.view.MenuItemCompat




class MainActivity : AppCompatActivity(), SettingsFragment.OnFragmentInteractionListener {

    private lateinit var mViewModel: MainViewModel
    lateinit var theme: String
    private lateinit var settingsFragment: SettingsFragment
    private lateinit var addFragment: AddFragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var quickViewFragment: QuickViewFragment
    private lateinit var menuItem: MenuItem
    lateinit var searchView : SearchView
    var currentFragment: String = ""

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
        quickViewFragment = QuickViewFragment()
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        firsTimeAction(mViewModel.checkFirstTime())
        theme = mViewModel.theme
        setTheme()
        startQuickView()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

//        mViewModel.deleteAllCities()
    }

    private fun listeners(){
        bottom_navigation.setOnNavigationItemSelectedListener(
            object: BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(@NonNull item: MenuItem):Boolean {
                    when (item.itemId) {
                        R.id.add -> startQuickView()
                        R.id.home -> startHome()
                        R.id.settings -> startSettings()
                    }
                    return true
                }
            })


    }

    private fun observers(){
        mViewModel.currentFragment.observe(this, Observer<String> { curFrag ->
            Log.d(WConstants.LOG, "LIVE DATA: "+ curFrag)
            currentFragment = curFrag
            changeFragment(curFrag)
        })
    }

    private fun changeFragment(fragValue: String){
        when(fragValue){
            WConstants.HOME -> startHome()
            WConstants.QUICK -> startQuickView()
            WConstants.SETTINGS -> startSettings()
            WConstants.SEARCH -> startAdd()
        }
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

    fun startQuickView(){
        supportFragmentManager.inTransaction {
            replace(R.id.frameLayout, quickViewFragment)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(shereen.weather.animal.R.menu.menu_items, menu)
        menuItem = menu!!.findItem(shereen.weather.animal.R.id.placeSearch)
        searchView = menuItem.actionView as SearchView
        mViewModel.saveMenuItem(menuItem)

        MenuItemCompat.setOnActionExpandListener(menuItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                Log.d(WConstants.LOG, "Request to collapse: "+ currentFragment)
                if(currentFragment == WConstants.SEARCH){
//                    Toast.makeText(this@MainActivity, "onMenutItemActionCollapse called", Toast.LENGTH_SHORT).show()
                    mViewModel.changeCurrentFragment(WConstants.QUICK)

                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return super.onOptionsItemSelected(item)
        when(item!!.itemId){
            R.id.placeSearch-> {Log.d(WConstants.LOGGER, "clicked menu_items")
                mViewModel.changeCurrentFragment(WConstants.SEARCH)
            }
            R.id.refresh -> mViewModel.refreshCities()
            R.id.delete -> mViewModel.deleteAllCities()

        }
        return true
    }

}
