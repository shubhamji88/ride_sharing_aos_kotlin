package fyp.ride_sharing_aos.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import fyp.ride_sharing_aos.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()



    }
}
