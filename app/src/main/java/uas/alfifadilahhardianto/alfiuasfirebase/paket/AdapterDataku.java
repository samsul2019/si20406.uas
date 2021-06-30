package uas.alfifadilahhardianto.alfiuasfirebase.paket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uas.alfifadilahhardianto.alfiuasfirebase.R;

public class AdapterDataku extends RecyclerView.Adapter<AdapterDataku.ViewHolder> {
    Context context;
    List<Dataku> list;

    OncallBack oncallBack;

    public void setOncallBack(OncallBack oncallBack){
        this.oncallBack =oncallBack;
    }

    public AdapterDataku(Context context, List<Dataku> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_data_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_view_data.setText(list.get(position).getIsi());

        holder.tombol_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oncallBack.onTombolEdit(list.get(position));
            }
        });
        holder.tombol_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oncallBack.onTombolHapus(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_view_data;
        ImageButton tombol_edit, tombol_hapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_view_data = itemView.findViewById(R.id.txt_view_data);
            tombol_edit = itemView.findViewById(R.id.tombol_edit);
            tombol_hapus = itemView.findViewById(R.id.tombol_hapus);
        }
    }
    public interface OncallBack{
        void onTombolEdit(Dataku dataku);
        void onTombolHapus(Dataku dataku);
    }
}
