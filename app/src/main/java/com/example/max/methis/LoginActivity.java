package com.example.max.methis;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import 	java.net.URLEncoder;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via User/password.
 * Modified by: Maxime Sabran
 * Removed : auto completion login, auto-registering
 * */
public class LoginActivity extends AppCompatActivity   {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    public String mResponse;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUserView = (AutoCompleteTextView) findViewById(R.id.user);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUserSignInButton = (Button) findViewById(R.id.login);
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid user, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        String url = "http://demo.u.cytrus.biz/login.php";

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);
        mResponse="";

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        mAuthTask = new UserLoginTask(user, password,url);

        //Allow AsyncTasks to be made in parallel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mAuthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
        }else {
            mAuthTask.execute((Void) null);
        }

        mAuthTask = null;
        showProgress(false);


    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUser;
        private URL mURL;
        private final String mPassword;
        /*
         * Constructor
         * display a failed message if the url was malformed
         */

        UserLoginTask(String user, String password, String url) {
            mUser = user;
            mPassword = password;

            try{
                mURL = new URL(url);
            }catch (Exception MalformedURLException){
                mResponse ="failed: wrong url";
            }
        }

        /*
         * POST and CALL request to the url in order to login
         */

        @Override
        protected Boolean doInBackground(Void... params) {


            System.setProperty("http.keepAlive", "false");
            OutputStreamWriter writer = null;
            BufferedReader reader = null;
            URLConnection connexion = null;
            try {
                // Encoding parameters for the POST
                String datas = "username"+"="+mUser + "&"+"password"+"="+mPassword;


                // Opening the connection to the url and allowing an upstream input

                connexion = mURL.openConnection();
                connexion.setDoOutput(true);


                // Sending the data
                writer = new OutputStreamWriter(connexion.getOutputStream());

                // Writing the data
                writer.write(datas);

                // Emptying the stream
                writer.flush();

                // Making the CALL request and reading it
                reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    mResponse+=ligne;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try{writer.close();}catch(Exception e){}
                try{reader.close();}catch(Exception e){}
            }

            return true;
        }

        /*
         * the AsyncTask ie is done:
         * Checking the CALL result
         * if success: starting the master/flow activity
         * else: display the error message
         */
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (mResponse.contains("success")) {
                showProgress(true);
                Context context = mUserView.getContext();
                Intent intent = new Intent(context, CarModelListActivity.class);
                context.startActivity(intent);
            }if(mResponse.contains("failed")){
                mPasswordView.setError(mResponse.substring(mResponse.indexOf(":") + 1));
                mPasswordView.requestFocus();
                mAuthTask = null;
                showProgress(false);
            }
            if (success) {
            } else {
                mPasswordView.setError(mResponse.substring(mResponse.indexOf(":") + 1));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

