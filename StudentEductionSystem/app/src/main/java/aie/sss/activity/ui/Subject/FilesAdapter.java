package aie.sss.activity.ui.Subject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aie.sss.R;
import aie.sss.Util.onItemClickListener;
import aie.sss.models.Files;
import aie.sss.view.ProgressImageView;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder> {
    private Context context;
    private List<Files> files = new ArrayList<>();
    private onItemClickListener listener;

    public FilesAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(context).inflate(R.layout.list_files, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        Files file = files.get(position);
        holder.name.setText(file.getName());
        holder.date.setText(file.getDate());
        holder.size.setText(file.getSize()+"");
        holder.type.setText(String.format(context.getString(R.string.file_type), file.getType()));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(file, position, holder));
        if (file.isDownloaded()){
            holder.icon.setImageDrawable(context.getDrawable(R.drawable.done));
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void setFiles(List<Files> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    class FileViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, size, type;
        ProgressImageView icon;
        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            size = itemView.findViewById(R.id.size);
            type = itemView.findViewById(R.id.level);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
