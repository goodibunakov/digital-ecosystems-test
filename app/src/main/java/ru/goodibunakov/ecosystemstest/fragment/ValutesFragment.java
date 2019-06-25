package ru.goodibunakov.ecosystemstest.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.goodibunakov.ecosystemstest.R;
import ru.goodibunakov.ecosystemstest.activity.MainActivity;
import ru.goodibunakov.ecosystemstest.adapter.RecyclerAdapter;
import ru.goodibunakov.ecosystemstest.interfaces.OnBackPressedListener;

public class ValutesFragment extends Fragment implements OnBackPressedListener{

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private int id;
    private Unbinder unbinder;

    static ValutesFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        ValutesFragment fragment = new ValutesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public ValutesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_valutes, container, false);
        unbinder = ButterKnife.bind(this, view);

        Objects.requireNonNull(getActivity()).setTitle(R.string.valute_select);
        setHasOptionsMenu(true);

        initRecycler();
        return view;
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerAdapter adapter = new RecyclerAdapter((MainActivity)getActivity(), Objects.requireNonNull(getActivity()), id);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cancel) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Objects.requireNonNull(getActivity()).setTitle(R.string.app_name);
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
    }
}
