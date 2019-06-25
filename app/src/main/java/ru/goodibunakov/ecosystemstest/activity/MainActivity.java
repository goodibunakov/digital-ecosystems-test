package ru.goodibunakov.ecosystemstest.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import ru.goodibunakov.ecosystemstest.R;
import ru.goodibunakov.ecosystemstest.databinding.ActivityMainBinding;
import ru.goodibunakov.ecosystemstest.fragment.CalculatorFragment;
import ru.goodibunakov.ecosystemstest.interfaces.ItemClickListener;
import ru.goodibunakov.ecosystemstest.interfaces.OnBackPressedListener;
import ru.goodibunakov.ecosystemstest.model.Currency;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_fragment_container, new CalculatorFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else if (fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void listClicked(Currency item, int id) {
        List<Fragment> list = fm.getFragments();
        Fragment currentFragment = null;
        for (Fragment fragment : list) {
            if (fragment instanceof CalculatorFragment) {
                currentFragment = fragment;
            }
        }
        switch (id) {
            case R.id.button1:
                String code1 = item.getCharCode();
                if (currentFragment != null) ((CalculatorFragment)currentFragment).setCode1(code1);
                break;
            case R.id.button2:
                String code2 = item.getCharCode();
                if (currentFragment != null) ((CalculatorFragment)currentFragment).setCode2(code2);
                break;
        }
        fm.popBackStack();
    }
}
