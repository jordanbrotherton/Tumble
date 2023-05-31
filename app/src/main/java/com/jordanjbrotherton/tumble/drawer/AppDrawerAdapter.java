package com.jordanjbrotherton.tumble.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jordanjbrotherton.tumble.R;

import java.util.ArrayList;
import java.util.List;

public class AppDrawerAdapter extends RecyclerView.Adapter<AppDrawerAdapter.ViewHolder> {
    private final Context context;
    public static List<ApplicationInfo> applications;
    public static List<ApplicationInfo> applicationsToRender;

    public AppDrawerAdapter(Context c){
        context = c;
        applications = new ArrayList<>();
        applicationsToRender = new ArrayList<>();
        new appQueryThread().execute();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView appDrawerLabel;
        public ImageView appDrawerIcon;

        public ViewHolder(final View itemView) {
            super(itemView);
            appDrawerLabel = itemView.findViewById(R.id.appDrawerLabel);
            appDrawerIcon = itemView.findViewById(R.id.appDrawerIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent appLaunch = context.getPackageManager().getLaunchIntentForPackage(applicationsToRender.get(pos).packageName.toString());
            context.startActivity(appLaunch);
        }
    }

    @NonNull
    @Override
    public AppDrawerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.app_drawer_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppDrawerAdapter.ViewHolder holder, int position) {
        String appName = applicationsToRender.get(position).label.toString();
        String packageName = applicationsToRender.get(position).packageName.toString();
        Drawable appIcon = applicationsToRender.get(position).icon;

        TextView appDrawerLabel = holder.appDrawerLabel;
        appDrawerLabel.setText(appName);

        ImageView appDrawerIcon = holder.appDrawerIcon;
        appDrawerIcon.setImageDrawable(appIcon);
    }

    @Override
    public int getItemCount() {
        return applicationsToRender.size();
    }

    public void filterList(ArrayList<ApplicationInfo> filteredList){
        applicationsToRender = filteredList;
        notifyDataSetChanged();
    }

    //This is the async thread which creates the app list.
    public class appQueryThread extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... Params){
            PackageManager packageManager = context.getPackageManager();
            applications = new ArrayList<>();
            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> fullApps = packageManager.queryIntentActivities(i, 0);
            for (ResolveInfo resolveInfo : fullApps){
                ApplicationInfo application = new ApplicationInfo();
                application.label = resolveInfo.loadLabel(packageManager);
                application.packageName = resolveInfo.activityInfo.packageName;
                application.icon = resolveInfo.activityInfo.loadIcon(packageManager);
                applications.add(application);
            }
            applications.sort((app1, app2) -> app1.label.toString().compareToIgnoreCase(app2.label.toString()));
            applicationsToRender = applications;
            return "Good";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateItems();
        }

        public void updateItems() {
            notifyItemInserted(getItemCount()-1);
        }
    }
}
