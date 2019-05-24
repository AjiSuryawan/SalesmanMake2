package rpl2016_17.example.com.salesmanmake2;

import android.content.Context;
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
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        final Item item = productList.get(position);
        holder.textViewTitle.setText(item.getAgenda());




//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(mCtx,"makan"+position,Toast.LENGTH_SHORT).show();
//                //intent plus passing data ke activity baru
//                Intent intent = new Intent(mCtx.getApplicationContext(), DetailActivity.class);
////                intent.putExtra("foto", product.getImage());
//                intent.putExtra("des", product.getDescribe());
//                intent.putExtra("Title", product.getTitle());
//                intent.putExtra("Describe", product.getShortdesc());
//                intent.putExtra("image", product.getImage());
//                mCtx.startActivity(intent);
//            }
//        });
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

        TextView textViewTitle ;
        CardView cardview;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
