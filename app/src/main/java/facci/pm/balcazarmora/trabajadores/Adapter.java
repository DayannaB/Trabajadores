package facci.pm.balcazarmora.trabajadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Horario> horarioArrayList;

    public Adapter(ArrayList<Horario> horarioArrayList) {
        this.horarioArrayList = horarioArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horario, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Horario horario = horarioArrayList.get(position);
        holder.hora.setText(horario.getHora());
        holder.fecha.setText(horario.getFecha());
    }

    @Override
    public int getItemCount() {
        return horarioArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fecha, hora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = (TextView) itemView.findViewById(R.id.ItemFecha);
            hora = (TextView) itemView.findViewById(R.id.ItemHora);
        }
    }

}
