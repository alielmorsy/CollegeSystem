package aie.sss.Util;

import androidx.recyclerview.widget.RecyclerView;

import aie.sss.models.DataSubjects;
import aie.sss.models.Files;

public interface onItemClickListener {
    void onItemClick(DataSubjects subjects, int pos, RecyclerView.ViewHolder holder);
    void onItemClick(Files subjects, int pos, RecyclerView.ViewHolder holder);
}
