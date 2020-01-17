package aie.sss.activity.ui.CreditHours;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import aie.sss.R;
import aie.sss.models.Subject;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {
    private Context context;
    private List<Subject> subjects = new ArrayList<>();
    private OnItemClickListener listener;

    public DialogAdapter(Context context) {
        this.context = context;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_subjects, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.subjectName.setText(subject.getSubjectName());
        holder.hours.setText(String.format(Locale.getDefault(), context.getString(R.string.subject_hours), subject.getHours()));
        holder.itemView.setOnClickListener((v) -> listener.onClick(v, subject, subjects));
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void remove(Subject subject) {
        subjects.remove(subject);
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(View v, Subject subject, List<Subject> list);
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
