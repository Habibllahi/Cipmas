package ng.com.codetrik.cipmas.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ng.com.codetrik.cipmas.R;
import ng.com.codetrik.cipmas.model.Quote;

public class QuoteRecyclerAdapter extends RecyclerView.Adapter<QuoteRecyclerAdapter.QuoteViewHolder> {

    private final Context context;
    private final List<Quote> data;
    private final Intent receivedIntent;
    private LayoutInflater inflater = null;

    public QuoteRecyclerAdapter(Context context, List<Quote> data, Intent receivedIntent){
        this.context = context;
        this.data = data;
        this.receivedIntent = receivedIntent;
        LayoutInflater inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuoteViewHolder(inflater.inflate(R.layout.quote_object_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        Quote currentData = data.get(position);
        holder.quote_material.setText(currentData.getMaterial().getProductName());
        holder.quote_quantity.setText(Double.toString(currentData.getQuantity()));
        holder.quote_price.setText(Double.toString(currentData.getPrice()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class QuoteViewHolder extends RecyclerView.ViewHolder {

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(
                    (item)->{

                    }
            );
        }
        final TextView quote_material = itemView.findViewById(R.id.quote_material);
        final TextView quote_quantity = itemView.findViewById(R.id.quote_quantity);
        final TextView quote_price = itemView.findViewById(R.id.quote_price);

    }
}
