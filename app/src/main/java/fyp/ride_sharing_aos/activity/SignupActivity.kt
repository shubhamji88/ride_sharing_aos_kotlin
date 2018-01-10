package fyp.ride_sharing_aos.activity

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fyp.ride_sharing_aos.R
import kotlinx.android.synthetic.main.activity_signup.*
import android.widget.RadioButton
import fyp.ride_sharing_aos.HomeActivity
import fyp.ride_sharing_aos.User



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

            val gender_selectedId = gender.getCheckedRadioButtonId()
            val gender_radioButton = findViewById<View>(gender_selectedId) as RadioButton

            val gender = gender_radioButton.text.toString()

            val identity_selectedId = identity.getCheckedRadioButtonId()
            val identity_radioButton = findViewById<View>(identity_selectedId) as RadioButton

            val identity = identity_radioButton.text.toString()

            val username = signup_username.text.toString()

            if (inputValidation()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful) {
                                val userId = mAuth.currentUser?.uid
                                val registerRef = dbRef.child("users").child(userId)
                                Toast.makeText(this, "Authentication Success.", Toast.LENGTH_SHORT).show()
                                val user = User(email, gender,identity,userId.toString(),username)
                                registerRef.setValue(user).addOnSuccessListener(){
                                    val intent = Intent(this@SignupActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    this.finish()
                                }
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

        var error : Int = 0
        var error_msg : String = ""


        //username validation
        if (uname.length < 6 || uname.length > 8 || uname.isNullOrEmpty() ) {
           error_msg +=  "\n - The length of username must be larger than 5 and smaller than 8 and must not empty"
            error++

        }
        //password validation
        if (password.length < 6 || password.isNullOrEmpty()) {
            error_msg += "\n - The length of password must be larger than 5 and must not empty"
            error++
        }

        if ( !password.matches("[0-9]+".toRegex()) && (password.matches("[a-z]+".toRegex()) || password.matches("[A-Z]+".toRegex()))) {
            error_msg += "\n - Password must contain letters and numbers "
            error++
        }

        if(error > 0) {
            val builder1 = AlertDialog.Builder(this@SignupActivity)
            builder1.setMessage(error_msg)
            builder1.setCancelable(true)
            builder1.setTitle("Alert")
            builder1.setPositiveButton(
                    "OK"
            ) { dialog, id -> dialog.cancel() }

            builder1.setNegativeButton(
                    "No"
            ) { dialog, id -> dialog.cancel() }
            val alert11 = builder1.create()
            alert11.show()

            return false
        }
        else
            return true
    }



}