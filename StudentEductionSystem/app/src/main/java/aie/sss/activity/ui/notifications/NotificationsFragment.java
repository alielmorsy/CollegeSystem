package aie.sss.activity.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import aie.sss.R;
import aie.sss.database.notification.ViewModel;

public class NotificationsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.NotificationList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        NotificationAdapter adapter = new NotificationAdapter(getContext());
        recyclerView.setAdapter(adapter);
        ViewModel model = ViewModelProviders.of(this).get(ViewModel.class);
        model.getNotifications().observe(getActivity(), adapter::setList);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}