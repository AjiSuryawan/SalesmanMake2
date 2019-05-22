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

public class adapterprofile extends RecyclerView.Adapter<adapterprofile.ProductViewHolder> {
    private Context mCtx;

    //we are storing all the products in a list
    private List<modelprofile> addAll;

    //getting the context and product list with constructor
    public adapterprofile(Context mCtx, List<rpl2016_17.example.com.salesmanmake2.modelprofile> adapterprofile) {
        this.mCtx = mCtx;
        this.addAll = adapterprofile;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.itemprofile, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        //getting the product of the specified position
        final modelprofile item = addAll.get(position);
        holder.textViewTitle.setText(item.getJudul());




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
        return addAll.size();
    }
    public void setfilter(List<modelprofile> list){
        addAll = new ArrayList<>();
        addAll.addAll(list);
        notifyDataSetChanged();

    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc ;
        CardView cardview;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
//            textViewDescribe = (TextView) itemView.findViewById(R.id.tex);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
