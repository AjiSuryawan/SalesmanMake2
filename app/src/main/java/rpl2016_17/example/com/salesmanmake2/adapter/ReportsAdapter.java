package rpl2016_17.example.com.salesmanmake2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rpl2016_17.example.com.salesmanmake2.ui.DetailReportsActivity;
import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.data.Job;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ReportsViewHolder> {

    private Context mCtx;

    //we are storing all the products in a list
    private List<Job> jobList;
    public ReportsAdapter(Context mCtx, List<Job> jobList) {
        this.mCtx = mCtx;
        this.jobList = jobList;
    }
    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_jobs, viewGroup, false);
        return new ReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsViewHolder holder, final int position) {
        final Job job = jobList.get(position);
        holder.name.setText(job.getShop_name());
        holder.location.setText(job.getLocation());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder {
        TextView name, location;

        ReportsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_shop_name2);
            location = itemView.findViewById(R.id.tv_location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mCtx, DetailReportsActivity.class);
                    i.putExtra("extra_job", jobList.get(getAdapterPosition()));

                    mCtx.startActivity(i);

                }
            });
        }
    }
}
