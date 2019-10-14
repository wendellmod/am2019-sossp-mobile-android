package br.com.sossp.sosspapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.sossp.sosspapp.AddressConverter;
import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.models.Occurrence;

public class OccurrencesAdapter extends RecyclerView.Adapter<OccurrencesAdapter.MyViewHolder> {

    private List<Occurrence> occurrences;
    private AddressConverter addressConverter;

    public OccurrencesAdapter(List<Occurrence> list) {
        this.occurrences = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_occurrences, parent, false);

        return new OccurrencesAdapter.MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        addressConverter = new AddressConverter();
        Occurrence occurrence = occurrences.get(position);

        double latitude = occurrence.getLatitude();
        double longitude = occurrence.getLongitude();
        String address = addressConverter.getCompleteAddressString(holder.itemView.getContext(), latitude, longitude);

        // --
        holder.tvType.setText(occurrence.getTypeOccurrence());

        if (occurrence.isStatus() == true) {
            holder.tvStatus.setText("Solucionado");
        } else {
            holder.tvStatus.setText("Não solucionado");
        }

        holder.tvAddress.setText(address);

    }

    @Override
    public int getItemCount() {
        return occurrences.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvType, tvStatus, tvAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tvAdapterType);
            tvStatus = itemView.findViewById(R.id.tvAdapterStatus);
            tvAddress = itemView.findViewById(R.id.tvAdapterAddress);

        }
    }

}
