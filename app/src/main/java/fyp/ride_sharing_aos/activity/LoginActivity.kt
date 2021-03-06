package fyp.ride_sharing_aos.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import fyp.ride_sharing_aos.BaseActivity
import fyp.ride_sharing_aos.HomeActivity
import fyp.ride_sharing_aos.R
import fyp.ride_sharing_aos.tools.Tools
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var mAuth : FirebaseAuth

    private val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        login_button.setOnClickListener{
            val email = login_email.text.toString()
            val password = login_password.text.toString()

            mAuth.signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString())
                    .addOnCompleteListener { task: Task<AuthResult> ->
                        if(task.isSuccessful)
                        {

                            val intentToMain = Intent(this@LoginActivity, HomeActivity::class.java)
                            intentToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intentToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intentToMain)
                        }
                        else
                        {
                            Log.w(TAG, "SignInUserWithEmail:failure", task.getException())
                            Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()

                        }
                    }
        }

        login_forgot.setOnClickListener{
            val intentToMain = Intent(this@LoginActivity, ForgetPassActivity::class.java)
            startActivity(intentToMain)
        }
    }

    fun inputValidation(username : String, password : String) : Boolean
    {
        val error_msg: ArrayList<String> = ArrayList()

        if(username.isNullOrEmpty())
        {
            error_msg.add(getString(R.string.login_user_error))
        }
        if(password.isNullOrEmpty())
        {
            error_msg.add(getString(R.string.login_password_error))
        }

        if(error_msg.isEmpty())
        {
            return true
        }
        else
        {
            Tools.showDialog(this@LoginActivity, "Alert", error_msg)
            return false
        }

    }




}
