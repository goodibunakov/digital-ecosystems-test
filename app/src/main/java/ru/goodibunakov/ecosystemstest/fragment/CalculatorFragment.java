package ru.goodibunakov.ecosystemstest.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import ru.goodibunakov.ecosystemstest.App;
import ru.goodibunakov.ecosystemstest.Calculator;
import ru.goodibunakov.ecosystemstest.R;
import ru.goodibunakov.ecosystemstest.databinding.FragmentCalculatorBinding;
import ru.goodibunakov.ecosystemstest.viewmodel.CalculatorFragmentViewModel;

public class CalculatorFragment extends Fragment {

    private String code1, code2;
    private double value1, value2;
    private Calculator calculator;
    private CalculatorFragmentViewModel viewModel;
    private FragmentCalculatorBinding binding;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        value1 = 1.0000;
        code1 = "RU";
        code2 = "USD";
        calculator = new Calculator(((App) Objects.requireNonNull(getActivity()).getApplication()).getList());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        viewModel = ViewModelProviders.of(this).get(CalculatorFragmentViewModel.class);
        binding.setViewModel(viewModel);

        if (savedInstanceState != null) {
            viewModel.liveDataValue1.setValue(savedInstanceState.getString("value1"));
            viewModel.code1.setValue(savedInstanceState.getString("code1"));
            viewModel.code2.setValue(savedInstanceState.getString("code2"));
        } else {
            viewModel.liveDataValue1.setValue(String.valueOf(value1));
            viewModel.code1.setValue(code1);
            viewModel.code2.setValue(code2);
        }
        viewModel.liveDataValue2.setValue(calculator.calculate(viewModel.code1.getValue(),
                viewModel.code2.getValue(), viewModel.liveDataValue1.getValue()));

        setListeners();

        viewModel.openValutes.observe(getViewLifecycleOwner(), integer -> {
            if (integer == null) return;

            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, ValutesFragment.newInstance(integer))
                    .addToBackStack(null)
                    .commit();
            viewModel.openValutes.setValue(null);
        });

        viewModel.code1.observe(getViewLifecycleOwner(), s -> {
            viewModel.liveDataValue1.removeObservers(getViewLifecycleOwner());
            viewModel.liveDataValue2.setValue(calculator.calculate(viewModel.code1.getValue(),
                    viewModel.code2.getValue(), viewModel.liveDataValue1.getValue()));
        });

        viewModel.code2.observe(getViewLifecycleOwner(), s -> {
            viewModel.liveDataValue2.removeObservers(getViewLifecycleOwner());
            viewModel.liveDataValue1.setValue(calculator.calculate(viewModel.code2.getValue(),
                    viewModel.code1.getValue(), viewModel.liveDataValue2.getValue()));
        });

        return binding.getRoot();
    }

    private void setListeners() {
        binding.sum1.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.sum1.clearFocus();
            }
            return false;
        });

        binding.sum2.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.sum2.clearFocus();
            }
            return false;
        });

        binding.sum1.setOnFocusChangeListener(onFocusChangeListener);
        binding.sum2.setOnFocusChangeListener(onFocusChangeListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(R.string.app_name);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel.liveDataValue1.getValue() != null)
            outState.putString("value1", viewModel.liveDataValue1.getValue());
        if (viewModel.code1.getValue() != null)
            outState.putString("code1", viewModel.code1.getValue());
        if (viewModel.code2.getValue() != null)
            outState.putString("code2", viewModel.code2.getValue());
    }

    private View.OnFocusChangeListener onFocusChangeListener = (v, hasFocus) -> {
        if (hasFocus && v instanceof EditText) {
            EditText et = (EditText) v;
            et.setText("");
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
            switch (v.getId()) {
                case R.id.sum1:
                    viewModel.liveDataValue2.removeObservers(getViewLifecycleOwner());
                    viewModel.liveDataValue1.observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            viewModel.liveDataValue2.setValue(calculator.calculate(viewModel.code1.getValue(),
                                    viewModel.code2.getValue(), viewModel.liveDataValue1.getValue()));
                        }
                    });
                    break;
                case R.id.sum2:
                    viewModel.liveDataValue1.removeObservers(getViewLifecycleOwner());
                    viewModel.liveDataValue2.observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            viewModel.liveDataValue1.setValue(calculator.calculate(viewModel.code2.getValue(),
                                    viewModel.code1.getValue(), viewModel.liveDataValue2.getValue()));
                        }
                    });
                    break;
            }
        }
    };

    public void setCode1(String code1) {
        viewModel.code1.setValue(code1);
    }

    public void setCode2(String code2) {
        viewModel.code2.setValue(code2);
    }
}