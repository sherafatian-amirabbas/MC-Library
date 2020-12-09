package com.example.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.work.ExistingPeriodicWorkPolicy
import com.example.library.workmanager.LibraryWorker
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.toolbarFavorite
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        Initialize()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.menu_search)
        searchItem?.isVisible = false


        val homeMenuItem = menu?.findItem(R.id.menu_home)
        homeMenuItem?.setOnMenuItemClickListener {

            startMainActivity()
            true
        }


        val refreshItem = menu?.findItem(R.id.menu_refresh)
        refreshItem?.isVisible = false


        val favoriteItem = menu?.findItem(R.id.menu_favorite)
        favoriteItem?.setOnMenuItemClickListener {

            startFavoritesActivity()
            true
        }


        val settingMenuItem = menu?.findItem(R.id.menu_settings)
        settingMenuItem?.isVisible = false


        return true
    }


    // ------------------------- private members

    private fun Initialize() {

        setSupportActionBar(toolbarSetting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarSetting.setNavigationOnClickListener { finish() }

        updateView()


        button_submit.setOnClickListener {

            var num = editTextNumber.text.toString()
            var interval = 0

            var invalid = false;
            if(num.isNullOrEmpty())
                invalid = true
            else
            {
                interval = num.toInt()
                if(interval < 15 || interval > 1440)
                    invalid = true
            }

            if(invalid)
                Toast.makeText(this,
                    "the Interval should greater than 15 minutes and less than 1440 minutes (24 h)",
                    Toast.LENGTH_SHORT)
                    .show()
            else {

                var originalSetting = _repo.User.getUserSetting()!!
                _repo.User.updateServiceSetting(interval, switchWorkmanager.isChecked){

                    Toast.makeText(this, "Settings Saved and Applied", Toast.LENGTH_SHORT).show()

                    if(!it.isServiceEnabled)
                        LibraryWorker.stop()
                    else
                    {
                        if(!originalSetting.isServiceEnabled)
                            LibraryWorker.setup(applicationContext, ExistingPeriodicWorkPolicy.REPLACE)
                        else if(originalSetting.serviceIntervalInMinutes != it.serviceIntervalInMinutes) {

                            LibraryWorker.stop()
                            LibraryWorker.setup(applicationContext, ExistingPeriodicWorkPolicy.REPLACE)
                        }
                    }
                }
            }
        }
    }

    private fun updateView() {

        var userSetting = _repo.User.getUserSetting()!!
        switchWorkmanager.isChecked = userSetting.isServiceEnabled
        editTextNumber.setText(userSetting.serviceIntervalInMinutes.toString())
    }

    private fun startFavoritesActivity() {

        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}