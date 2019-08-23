package rpl2016_17.example.com.salesmanmake2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rpl2016_17.example.com.salesmanmake2.R;
import rpl2016_17.example.com.salesmanmake2.data.Job;
import rpl2016_17.example.com.salesmanmake2.ui.DetailJobActivity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.JobsViewHolder> {
    private Context mCtx;
    private List<Job> jobList;

    public ListAdapter(Context mCtx, List<Job> jobList) {
        this.mCtx = mCtx;
        this.jobList = jobList;
    }

    @Override
    public JobsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_jobs, parent, false);
        return new JobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final JobsViewHolder holder, final int position) {
        final Job job = jobList.get(position);
        holder.name.setText(job.getShop_name());
        holder.address.setText(job.getShop_address());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class JobsViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        JobsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_shop_name);
            address = itemView.findViewById(R.id.tv_shop_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mCtx, DetailJobActivity.class);
                    i.putExtra("extra_job", jobList.get(getAdapterPosition()));
                    mCtx.startActivity(i);
                }
            });
        }
    }
}
