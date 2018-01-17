package org.fundacionparaguaya.advisorapp.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import org.fundacionparaguaya.advisorapp.R;
import org.fundacionparaguaya.advisorapp.fragments.TabbedFrag;
import org.fundacionparaguaya.advisorapp.fragments.TestTabbedFragment;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        TabbedFrag frag = new TestTabbedFragment();

        frag.addBackNavRequiredHandler((event) -> {
            String text;

            if(event.isRequired()) text ="Is Required";
            else text = "Not required";

            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        });

        ft.add(R.id.content, frag).commit();
    }
}
