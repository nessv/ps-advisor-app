package org.fundacionparaguaya.advisorapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.fundacionparaguaya.advisorapp.AdvisorApplication;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.activities.DashActivity;
import org.fundacionparaguaya.advisorapp.data.remote.AuthenticationManager;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.IrMapper;
import org.fundacionparaguaya.advisorapp.data.remote.intermediaterepresentation.LoginIr;
import org.fundacionparaguaya.advisorapp.jobs.SyncJob;
import org.fundacionparaguaya.advisorapp.models.Login;
import org.fundacionparaguaya.advisorapp.models.User;
import org.fundacionparaguaya.advisorapp.viewmodels.InjectionViewModelFactory;
import org.fundacionparaguaya.advisorapp.viewmodels.LoginViewModel;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * The fragment for the login page.
 */

public class LoginFragment extends Fragment {
    EditText mEmailView;
    EditText mPasswordView;
    Button mSubmitButton;
    TextView mIncorrectCredentialsView;
    TextView mPasswordReset;
    ImageView mHelpButton;

    AuthenticationManager mAuthManager;

    @Inject InjectionViewModelFactory mViewModelFactory;

    LoginViewModel mLoginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AdvisorApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        mLoginViewModel = ViewModelProviders
                .of((FragmentActivity) getActivity(), mViewModelFactory)
                .get(LoginViewModel.class);
        mAuthManager = mLoginViewModel.getAuthManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setRetainInstance(true);

        mIncorrectCredentialsView = (TextView) view.findViewById(R.id.login_incorrect_credentials);
        mPasswordReset = (TextView) view.findViewById(R.id.login_passwordreset);

        mEmailView = (EditText) view.findViewById(R.id.login_email);
        mPasswordView = (EditText) view.findViewById(R.id.login_password);

        mHelpButton = (ImageView) view.findViewById(R.id.login_help);

        mSubmitButton = (Button) view.findViewById(R.id.login_loginbutton);
        mSubmitButton.setOnClickListener((event) -> attemptLogin());

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return false;
                }
                return true;
            }
        });

        //hide incorrect login textview on touch
        View.OnClickListener hideIncorrectCredentials =
                (event) -> mIncorrectCredentialsView.setVisibility(View.INVISIBLE);

        mEmailView.setOnClickListener(hideIncorrectCredentials);
        mPasswordView.setOnClickListener(hideIncorrectCredentials);

        //Hide for later implementation
        mPasswordReset.setVisibility(View.GONE);
        mHelpButton.setVisibility(View.GONE);

        mPasswordReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: Implement password reset (set visible above when ready to implement)
            }
        });

        mHelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: Implement Help button (set visible above when ready to implement)
                //using this as a temporary login method
                //getActivity().finish();
            }
        });

        if (mAuthManager.hasRefreshToken()) {
            new RefreshTokenLoginTask(this).execute();
        }
        return view;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.login_fieldrequired));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.login_fieldrequired));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mAuthManager.getUser().setUsername(email);
            mAuthManager.getUser().setPassword(password);
            new PasswordLoginTask(this).execute();
        }
    }

    void launchMainActivity(Context context) {
        SyncJob.startPeriodic();
        Intent dashboard = new Intent(context, DashActivity.class);
        context.startActivity(dashboard);
        getActivity().finish();
    }
}
abstract class AbstractLoginTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "AbstractLoginTask";
    protected static final String AUTH_KEY = "Basic YmFyQ2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=";

    LoginFragment mLoginFragment;
    AuthenticationManager mAuthManager;

    AbstractLoginTask(LoginFragment loginFragment) {
        this.mLoginFragment = loginFragment;
        this.mAuthManager = mLoginFragment.mAuthManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mLoginFragment.mEmailView.setEnabled(false);
        mLoginFragment.mPasswordView.setEnabled(false);
        mLoginFragment.mSubmitButton.setEnabled(false);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            User user = mAuthManager.getUser();
            Response<LoginIr> response = login(user);

            if (!wasSuccessful(response)) {
                return false;
            }

            Login login = IrMapper.mapLogin(response.body());
            user.setLogin(login);
            user.setEnabled(true);
            mAuthManager.saveRefreshToken();
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: Couldn't attempt to log the user in!", e);
        }
        return true;
    }

    protected abstract Response<LoginIr> login(User user) throws IOException;

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mLoginFragment.mEmailView.setEnabled(true);
        mLoginFragment.mPasswordView.setEnabled(true);
        mLoginFragment.mSubmitButton.setEnabled(true);

        if (result) {
            Context context = mLoginFragment.getActivity();
            Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
            mLoginFragment.launchMainActivity(context);
        } else {
            onLoginFailure();
        }
    }

    protected abstract void onLoginFailure();

    private <T> boolean wasSuccessful(Response<T> response) {
        return response != null && response.isSuccessful() && response.body() != null;
    }
}

class PasswordLoginTask extends AbstractLoginTask {

    PasswordLoginTask(LoginFragment loginFragment) {
        super(loginFragment);
    }

    @Override
    protected Response<LoginIr> login(User user) throws IOException {
        return mAuthManager.getAuthService()
                .loginWithPassword(
                        AUTH_KEY,
                        user.getUsername(), user.getPassword()).execute();
    }

    @Override
    protected void onLoginFailure() {
        mLoginFragment.mIncorrectCredentialsView.setText(R.string.login_incorrectcredentials);
        mLoginFragment.mIncorrectCredentialsView.setVisibility(View.VISIBLE);
        mLoginFragment.mPasswordView.setText(""); //erase the password field if incorrect
    }
}

class RefreshTokenLoginTask extends AbstractLoginTask {

    RefreshTokenLoginTask(LoginFragment loginFragment) {
        super(loginFragment);
    }
    @Override
    protected Response<LoginIr> login(User user) throws IOException {
        return mAuthManager.getAuthService()
                .loginWithRefreshToken(
                        AUTH_KEY,
                        user.getLogin().getRefreshToken()).execute();
    }

    @Override
    protected void onLoginFailure() { }
}



