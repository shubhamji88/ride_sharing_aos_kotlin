package fyp.ride_sharing_aos.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fyp.ride_sharing_aos.HomeActivity
import fyp.ride_sharing_aos.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference

        login_button.setOnClickListener{
            mAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString()).
                    addOnCompleteListener { task: Task<AuthResult> ->
                        val intentToMain = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intentToMain)
                    }
        }

//          Code For Logout
 //       logout.setOnClickListener(){
//            mAuth.signOut()
//            val intent = Intent(this@MainActivity, GetStartActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

    }
}