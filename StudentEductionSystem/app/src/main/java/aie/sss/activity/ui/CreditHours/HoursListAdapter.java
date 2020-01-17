package aie.sss.activity.ui.CreditHours;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import aie.sss.R;
import aie.sss.models.Subject;

public class HoursListAdapter extends RecyclerView.Adapter<HoursListAdapter.ViewHolder> {
    private ArrayList<Subject> subjects = new ArrayList<>();
    private Context context;

    public HoursListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_hours, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.subjectName.setText(subject.getSubjectName());
        holder.hours.setText(String.format(context.getString(R.string.subject_hours), subject.getHours()));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }

    public void add(Subject subject) {
        subjects.add(subject);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, hours;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            hours = itemView.findViewById(R.id.hours);
        }
    }
}
