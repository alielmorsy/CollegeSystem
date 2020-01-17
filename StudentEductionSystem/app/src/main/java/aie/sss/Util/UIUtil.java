package aie.sss.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import aie.sss.R;
import aie.sss.activity.AbsentActivity;
import aie.sss.activity.CreditHoursActivity;
import aie.sss.activity.ExamScheduleActivity;
import aie.sss.activity.ResultActivity;
import aie.sss.activity.SubjectActivity;
import aie.sss.models.Subject;
import aie.sss.user.SessionManager;

public class UIUtil {


    public static void setupDraw(Activity activity, Toolbar bar) {
        SessionManager manager = new SessionManager(activity);
        AccountHeader accountHeader = new AccountHeaderBuilder().withOnlyMainProfileImageVisible(true).withHeaderBackground(R.drawable.background).withTextColor(Color.WHITE).withPaddingBelowHeader(true).withActivity(activity).withAlternativeProfileHeaderSwitching(false).withOnAccountHeaderListener((view, profile, current) -> {
            if (profile.getIdentifier() == 12) {
                activity.finish();
                Runtime.getRuntime().exit(0);
            }
            return true;
        }).build();

        accountHeader.addProfiles(new ProfileDrawerItem().withEmail(manager.getID() + "").withTextColor(Color.parseColor("#DEFFFFFF")).withTextColor(Color.WHITE).withIcon(R.drawable.background).withName(manager.getName()).withIdentifier(1),
                new ProfileSettingDrawerItem().withName("Log Out").withTextColor(Color.WHITE).withIdentifier(12));
        accountHeader.setActiveProfile(1);

        new DrawerBuilder().withActivity(activity)
                .withDrawerItems(items()).withToolbar(bar)
                .withAccountHeader(accountHeader)
                .withSliderBackgroundColor(Color.parseColor("#FF0F0F0F")).withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if ("explore".equals(drawerItem.getTag())) {
                        handleExplore(activity, drawerItem.getIdentifier());
                    } else if ("Subjects".equals(drawerItem.getTag())) {

                        Intent intent = new Intent(activity, SubjectActivity.class);
                        intent.putExtra("subject", ((SecondaryDrawerItem) drawerItem).getName().getText());
                        intent.putExtra("id", drawerItem.getIdentifier());
                        activity.startActivity(intent);
                    } else if ("about".equals(drawerItem.getTag())) {
                        AboutDialog(activity);

                    } else {

                    }
                    return true;
                }).build();
    }

    private static void handleExplore(Activity a, long id) {
        switch ((int) id) {
            case 100:
                a.startActivity(new Intent(a, ResultActivity.class));
                break;
            case 120:
                a.startActivity(new Intent(a, ExamScheduleActivity.class));
                break;
            case 140:
                break;
            case 160:
                break;
            case 180:
                a.startActivity(new Intent(a, AbsentActivity.class));
                break;
            case 240:
                a.startActivity(new Intent(a, CreditHoursActivity.class));
            default:
                Log.e("drawer", "error in identifier");
                break;
        }
    }

    public static AlertDialog LoadingDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        ProgressBar bar = new ProgressBar(activity);

        builder.setView(bar);
        builder.setCancelable(true);
        bar.setIndeterminate(true);
        bar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
        bar.setIndeterminateTintList(new ColorStateList(new int[][]{{}}, new int[]{activity.getColor(R.color.colorAccent)}));

        return builder.create();

    }

    private static List<IDrawerItem> items() {
        List<IDrawerItem> items = new ArrayList<>();
        final List<Subject> list = Constants.allSubjects;
        IDrawerItem[] subjects = new IDrawerItem[list.size()];
        for (int i = 0; i < list.size(); i++) {
            subjects[i] = new SecondaryDrawerItem().withIdentifier(i + 1).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedTextColor(Color.parseColor("#BB86FC")).withName(list.get(i).getSubjectName()).withTag("Subjects");

        }

        SecondaryDrawerItem results = new SecondaryDrawerItem().withIdentifier(100).withIcon(R.drawable.results).withTextColor(Color.parseColor("#DEFFFFFF")).withTag("explore").withSelectedTextColor(Color.parseColor("#BB86FC")).withName("my results").withTag("explore");
        SecondaryDrawerItem ExamSchedule = new SecondaryDrawerItem().withIcon(R.drawable.ic_exam).withIdentifier(120).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedTextColor(Color.parseColor("#BB86FC")).withTag("explore").withName("Exam Schedule");

        SecondaryDrawerItem StudentAffairs = new SecondaryDrawerItem().withIdentifier(160).withIcon(R.drawable.ic_man).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedTextColor(Color.parseColor("#BB86FC")).withTag("explore").withName("Student Affairs");
        SecondaryDrawerItem getAbsent = new SecondaryDrawerItem().withIdentifier(180).withIcon(R.drawable.ic_man).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedTextColor(Color.parseColor("#BB86FC")).withTag("explore").withName("get your absent");
        SecondaryDrawerItem creditHours = new SecondaryDrawerItem().withIdentifier(240).withIcon(R.drawable.ic_man).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedTextColor(Color.parseColor("#BB86FC")).withTag("explore").withName("Register Credit Hours");
        SecondaryDrawerItem publicChat = new SecondaryDrawerItem().withIdentifier(200).withTextColor(Color.parseColor("#DEFFFFFF")).withTag("explore").withSelectedTextColor(Color.parseColor("#BB86FC")).withName("PublicChat");

        SecondaryDrawerItem aboutUs = new SecondaryDrawerItem().withIdentifier(220).withIcon(R.drawable.ic_about_us).withTextColor(Color.parseColor("#DEFFFFFF")).withTag("about").withSelectedTextColor(Color.parseColor("#BB86FC")).withName("About Us");

        ExpandableBadgeDrawerItem subjectsMenu = new ExpandableBadgeDrawerItem().withIcon(R.drawable.subjects).withIconColor(Color.WHITE).withSubItems(subjects).withName("Subjects").withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedColor(Color.parseColor("#BB86FC")).withSelectedTextColor(Color.parseColor("#DEFFFFFF"));
        ExpandableBadgeDrawerItem exploreMenu = new ExpandableBadgeDrawerItem().withIcon(R.drawable.explore).withIconColor(Color.WHITE).withName("Explore").withSubItems(getAbsent, creditHours, results, ExamSchedule, StudentAffairs, publicChat).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedColor(Color.parseColor("#BB86FC"));
        ExpandableBadgeDrawerItem aboutUsMenu = new ExpandableBadgeDrawerItem().withIconColor(Color.WHITE).withName("AboutUs").withSubItems(aboutUs).withTextColor(Color.parseColor("#DEFFFFFF")).withSelectedColor(Color.parseColor("#BB86FC"));
        items.add(subjectsMenu);
        items.add(exploreMenu);
        items.add(aboutUsMenu);
        return items;
    }

    public static void AboutDialog(Activity activity) {
        AlertDialog.Builder bulider = new AlertDialog.Builder(activity);
        bulider.setView(R.layout.about_us_dialog);
        AlertDialog dialog = bulider.create();

        dialog.show();
    }
}
