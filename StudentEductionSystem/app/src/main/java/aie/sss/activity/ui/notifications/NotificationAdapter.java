package aie.sss.activity.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aie.sss.R;

import aie.sss.database.notification.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> list=new ArrayList<>();
    private Context context;
    public NotificationAdapter(Context context) {
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.notifcation_list,null,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification n=list.get(position);
        holder.message.setText(n.getMessage());
        holder.time.setText(String.valueOf(n.getTime()));
        holder.title.setText(n.getTitle());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Notification> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         TextView title, message, time,type;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);

        }
    }
}
