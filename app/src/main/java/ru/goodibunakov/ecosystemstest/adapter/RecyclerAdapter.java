package ru.goodibunakov.ecosystemstest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.goodibunakov.ecosystemstest.App;
import ru.goodibunakov.ecosystemstest.R;
import ru.goodibunakov.ecosystemstest.interfaces.ItemClickListener;
import ru.goodibunakov.ecosystemstest.model.Currency;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Currency> items;
    private int lastPosition = -1;
    private int id;
    private final ItemClickListener clickListener;

    public RecyclerAdapter(ItemClickListener clickListener, Context context, int id) {
        this.context = context;
        this.clickListener = clickListener;
        this.id = id;
        items = ((App) context.getApplicationContext()).getList();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.item_recycler, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Currency item = items.get(position);
        ((RecyclerViewHolder) holder).bind(item, clickListener);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        } else {
            return items.size();
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.card)
        CardView card;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Currency item, ItemClickListener clickListener) {
            title.setText(item.getName());
            code.setText(item.getCharCode());
            card.setOnClickListener(v -> clickListener.listClicked(item, id));
        }
    }
}
