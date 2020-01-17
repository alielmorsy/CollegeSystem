package aie.sss.activity.ui.Subject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import aie.sss.models.DataSubjects;

public abstract class DefaultListAdapter<T extends RecyclerView.ViewHolder,S extends DataSubjects> extends RecyclerView.Adapter<T> {
    protected Context context;
    private T holder;
    protected ArrayList<S> list=new ArrayList<>();
    public DefaultListAdapter(Context context) {
        this.context = context;
    }

    public void setViewHolder(T holder) {
        this.holder = holder;
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return holder;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<S> list) {
        this.list = list;
        notifyDataSetChanged();
        Log.e("size", String.valueOf(getItemCount()));
    }
    public interface onItemClickListener<S extends DataSubjects>{
        void onItemClick(S item, int position, View view);
    }
}
