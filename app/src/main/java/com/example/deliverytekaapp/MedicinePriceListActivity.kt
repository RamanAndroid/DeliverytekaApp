package com.example.deliverytekaapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_medecine_price_list.*

class MedicinePriceListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val drawer by lazy { drawer_layout }
    private lateinit var id: String
    private lateinit var phone: String
    private lateinit var fragment:Fragment
    private lateinit var navigationView:NavigationView
    private lateinit var toggle:ActionBarDrawerToggle
    val manager = supportFragmentManager
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
        navigationView = nav_view
        navigationView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState==null){
            supportActionBar?.title = "Каталог"
            val transaction = manager.beginTransaction()
            val fragment = CatalogFragment()
            fragment.arguments = bundleOf("ID_USER" to id)
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            navigationView.setCheckedItem(R.id.nav_catalog)
        }
    }

    override fun onBackPressed() {
        if(this.drawer.isDrawerOpen(GravityCompat.START)){
            this.drawer.closeDrawers()
        }else{
            super.onBackPressed()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_catalog->{
                supportActionBar?.title = "Каталог"
                val transaction = manager.beginTransaction()
                val fragment = CatalogFragment()
                fragment.arguments = bundleOf("ID_USER" to id)
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                navigationView.setCheckedItem(R.id.nav_catalog)
            }
            R.id.nav_cart->{
                supportActionBar?.title = "Корзина"
                val transaction = manager.beginTransaction()
                val fragment = CartFragment()
                fragment.arguments = bundleOf("ID_USER" to id)
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                navigationView.setCheckedItem(R.id.nav_cart)
            }
            R.id.nav_profile->{
                supportActionBar?.title = "Профиль"
                val transaction = manager.beginTransaction()
                val fragment = ProfileFragment()
                fragment.arguments = bundleOf("ID_USER" to id)
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                navigationView.setCheckedItem(R.id.nav_profile)
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true
    }
}