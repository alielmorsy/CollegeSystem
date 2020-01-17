package aie.sss.activity.ui.result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import aie.sss.R;
import aie.sss.Util.Util;
import aie.sss.models.Result;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private Context context;
    private int i = 0;
    private ArrayList<Result> list = new ArrayList<>();

    public ResultAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_result, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = list.get(position);
        holder.subjectName.setText(result.getName());
        holder.degree.setText(String.valueOf(result.getDegree()));
        holder.level.setText(Util.toLevel(result.getLevel()));
        String hours = context.getString(R.string.result_hours);
        holder.hours.setText(String.format(hours, result.getHours()));
        int[] res = {R.drawable.cerclebackgroundgreen, R.drawable.cerclebackgroundpink, R.drawable.cerclebackgroundpurple, R.drawable.cerclebackgroundyello};

        if (i < res.length) {
            holder.degree.setBackgroundResource(res[i]);
            i++;
        }else{
            i=0;
            holder.degree.setBackgroundResource(res[i]);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<Result> list) {
        this.list = list;
        Collections.reverse(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, degree, level, hours;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            degree = itemView.findViewById(R.id.degree);
            level = itemView.findViewById(R.id.level);
            hours = itemView.findViewById(R.id.hours);
        }
    }
}
