package rpl2016_17.example.com.salesmanmake2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ProductViewHolder> {
    private Context mCtx;

    //we are storing all the products in a list
    private List<rpl2016_17.example.com.salesmanmake2.Item> productList;

    //getting the context and product list with constructor
    public Adapter(Context mCtx, List<rpl2016_17.example.com.salesmanmake2.Item> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_product, parent, false);
        final ProductViewHolder productViewHolder = new ProductViewHolder(view);
        productViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mCtx,DetailJobActivity.class);
                i.putExtra("shop_address",productList.get(productViewHolder.getAdapterPosition()).getShop_address());
                i.putExtra("shop_name",productList.get(productViewHolder.getAdapterPosition()).getShop_name());
                i.putExtra("shop_phone",productList.get(productViewHolder.getAdapterPosition()).getShop_phone());
                i.putExtra("shop_desc",productList.get(productViewHolder.getAdapterPosition()).getDescription());
                i.putExtra("shop_id",productList.get(productViewHolder.getAdapterPosition()).getId());

                mCtx.startActivity(i);

            }
        });

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        final Item item = productList.get(position);

        holder.address.setText(productList.get(position).getShop_address());
        holder.phone.setText(""+productList.get(position).getShop_phone());
        holder.description.setText(productList.get(position).getDescription());
        holder.name.setText(productList.get(position).getShop_name());
        holder.id.setText(""+productList.get(position).getId());



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setfilter(List<Item> listitem){
        productList = new ArrayList<>();
        productList.addAll(listitem);
        notifyDataSetChanged();

    }


    class ProductViewHolder extends RecyclerView.ViewHolder {


        CardView cardview;
        TextView id,description,name,phone,address;

        public ProductViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.job_id);
            description = (TextView) itemView.findViewById(R.id.job_description);
            name = (TextView) itemView.findViewById(R.id.shop_name);
            phone = (TextView) itemView.findViewById(R.id.shop_phone);
            address = (TextView) itemView.findViewById(R.id.shop_address);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
