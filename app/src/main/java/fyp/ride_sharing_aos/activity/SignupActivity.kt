package fyp.ride_sharing_aos.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fyp.ride_sharing_aos.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {


    private lateinit var mAuth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference
        setUponClick()
    }

    override fun onResume() {
        super.onResume()

    }

    fun setUponClick()
    {
        signup_button.setOnClickListener({
            val email = signup_itsc.text.toString() + "@connect.ust.hk"
            val password = signup_password.text.toString()

            if (inputValidation()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Authentication Success.", Toast.LENGTH_SHORT).show()
                                val user = mAuth.getCurrentUser()
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
            else
            {
                //Show Error

            }
        })
    }


    //https://kotlinlang.org/docs/reference/functions.html
    fun inputValidation() : Boolean
    {
        val uname  = signup_username.text.toString()
        val password = signup_password.text.toString()


        //username validation
        if (uname.length < 6 && uname.isNullOrEmpty() ) {
            //pop up msg
            return false
        }
        if (uname.length > 8) {
            //pop up msg
            return false
        }
        //password validation
        if (password.length < 6 && password.isNullOrEmpty())
            //pop up msg
            return false

        if ( !password.matches("[0-9]+".toRegex()) && !password.matches("[a-z]+".toRegex()) && !password.matches("[A-Z]+".toRegex()))
             //pop up msg
            return false

        //identity validation
        if (!radio_usr_student.isChecked() && !radio_usr_staff.isChecked())
            return false

        //gender validation
        if(!radio_sex_male.isChecked() && !radio_sex_female.isChecked())
            return false

        return true
    }


}
