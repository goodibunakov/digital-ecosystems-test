package ru.goodibunakov.ecosystemstest.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import ru.goodibunakov.ecosystemstest.App;
import ru.goodibunakov.ecosystemstest.R;
import ru.goodibunakov.ecosystemstest.Utils;
import ru.goodibunakov.ecosystemstest.databinding.ActivitySplashBinding;
import ru.goodibunakov.ecosystemstest.model.Currency;
import ru.goodibunakov.ecosystemstest.viewmodel.SplashScreenViewModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_splash);
        binding.setLifecycleOwner(this);

        SplashScreenViewModel viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel.class);
        binding.setViewModel(viewModel);

        Animation animationRotateCenter = AnimationUtils.loadAnimation(
                this, R.anim.rotate_center);
        binding.logo.startAnimation(animationRotateCenter);

        viewModel.getMutableLiveData().observe(this, responseObject -> {
            if (responseObject == null) {
                binding.repeat.setVisibility(View.VISIBLE);
                Toast.makeText(SplashActivity.this, getResources().getString(R.string.failure), Toast.LENGTH_SHORT).show();
            } else {
                binding.repeat.setVisibility(View.INVISIBLE);
                List<Currency> list = responseObject.getList();
                for (Currency currency : list) {
                    currency.setValue(Utils.changeToDot(currency.getValue()));
                }
                ((App) getApplication()).setList(list);
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        final View decorView = getWindow().getDecorView();
        hideSystemUI(decorView);
    }

    private void hideSystemUI(View decorView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN); // hide status bar
        }
    }
}