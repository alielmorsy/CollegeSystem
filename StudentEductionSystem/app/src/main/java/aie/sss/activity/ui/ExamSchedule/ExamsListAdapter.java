package aie.sss.activity.ui.ExamSchedule;

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
import aie.sss.models.ExamSubject;

public class ExamsListAdapter extends RecyclerView.Adapter<ExamsListAdapter.ViewHolder> {
    private ArrayList<ExamSubject> arrayList=new ArrayList<>();
    private int i = 0;
    private Context context;

    public ExamsListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_exam, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int[] res = {R.drawable.cerclebackgroundgreen, R.drawable.cerclebackgroundpink, R.drawable.cerclebackgroundpurple, R.drawable.cerclebackgroundyello};
        ExamSubject exam=arrayList.get(position);
        holder.subjectName.setText(exam.getSubjectName());
        String time=context.getString(R.string.time);
        time=String.format(time,exam.getStartTime(),exam.getEndTime());
        holder.time.setText(time);
        holder.day.setText(exam.getDay());
        holder.date.setText(exam.getDate());
        if (i < res.length) {
            holder.icon.setBackgroundResource(res[i]);
            i++;
        } else {
            i = 0;
            holder.icon.setBackgroundResource(res[0]);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayList(ArrayList<ExamSubject> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView subjectName, day, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);

            icon = itemView.findViewById(R.id.icon);
        }
    }
}
