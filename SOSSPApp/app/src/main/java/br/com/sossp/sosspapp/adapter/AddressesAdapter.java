package br.com.sossp.sosspapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.sossp.sosspapp.R;
import br.com.sossp.sosspapp.models.Address;

/**
 * Created by wendellmod
 */
public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.MyViewHolder> {

    private List<Address> addresses;

    public AddressesAdapter(List<Address> list) {
        this.addresses = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_addresses, parent, false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Address address = addresses.get(position);
        holder.nicknameAddress.setText(address.getAddressName());

    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nicknameAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nicknameAddress = itemView.findViewById(R.id.tvNicknameAddress);

        }
    }

}
