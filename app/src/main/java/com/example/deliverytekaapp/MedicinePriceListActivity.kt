package com.example.deliverytekaapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_medecine_price_list.*

class MedicinePriceListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val drawer by lazy { drawer_layout }
    lateinit var id: String
    lateinit var phone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecine_price_list)
        val bundle = intent.extras
        bundle?.let{
            id = bundle.getString("id").toString()
            phone = bundle.getString("phone").toString()
        }
        val toolbar = toolbar
        setSupportActionBar(toolbar)
        val navigationView = nav_view
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState==null){
            val fragment = CatalogFragment.newInstance(id)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
            navigationView.setCheckedItem(R.id.nav_catalog)
        }
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_catalog->{
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,CatalogFragment()).commit()
            }
        }
        return true
    }
}