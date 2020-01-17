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

import aie.sss.R;
import aie.sss.Util.onItemClickListener;
import aie.sss.models.Assignments;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentViewHolder> {


    private aie.sss.Util.onItemClickListener onItemClickListener;
    private ArrayList<Assignments> list = new ArrayList<>();
    private Context context;
    AssignmentsAdapter(Context context) {
        this.context = context;

    }

    public void setList(ArrayList<Assignments> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssignmentViewHolder(LayoutInflater.from(context).inflate(R.layout.list_assignments, null));

    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignments assignments = list.get(position);
        holder.time.setText(assignments.getDate());
        holder.title.setText(assignments.getName());

        if (assignments.isClosed()) {
            holder.closed.setVisibility(View.VISIBLE);
        }
        if (assignments.isSolved()) {
            holder.solved.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, closed, date;
        ImageView icon, solved;

        AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);

            closed = itemView.findViewById(R.id.closed);

            icon = itemView.findViewById(R.id.image);
            solved = itemView.findViewById(R.id.solveImage);

        }
    }
}
