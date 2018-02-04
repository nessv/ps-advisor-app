package org.fundacionparaguaya.advisorapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.WindowManager;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.LoginFragment;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_FRAG_TAG = "LOGIN_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       /* //only supported after Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.fp_green, this.getTheme()));
        }*/

        FragmentManager manager = getSupportFragmentManager();
        LoginFragment loginFragment = (LoginFragment) manager.findFragmentByTag(LOGIN_FRAG_TAG);

        if (loginFragment == null)
            loginFragment = new LoginFragment();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.login_root, loginFragment, LOGIN_FRAG_TAG);
        transaction.commit();
    }
}

