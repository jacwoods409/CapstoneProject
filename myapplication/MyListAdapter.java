package com.example.myapplication;



import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> implements Filterable {
    private LinkedList<Contact> listdata;
    private LinkedList<Contact> listdata_copy;
    private Context con;

    // RecyclerView recyclerView;
    public MyListAdapter(LinkedList listdata1, Context context) {
        this.listdata = listdata1;
        listdata_copy =listdata1;
        con=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Contact myListData = listdata.get(position);
        holder.textView.setText(myListData.getName());
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), viewContact.class);
                String id = String.valueOf(position+1);
               i.putExtra("id",id);
                view.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {  // holds each individual contact view
        public ImageButton show;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
           this.show = (ImageButton) itemView.findViewById(R.id.viewCon);

           this.textView = (TextView) itemView.findViewById(R.id.name);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
    @Override
    public Filter getFilter() { // filters text in the search view
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listdata = (LinkedList<Contact>) results.values;
                notifyDataSetChanged();
            }

            @Override  // filters results of search bar
            protected FilterResults performFiltering(CharSequence constraint) {
                LinkedList<Contact> filteredResults = null;
                if (constraint.equals("")) {
                    filteredResults = listdata;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    } //displays contacts in adapter if search is fruitless or is canceled
    protected void displayContacts()
    {
        listdata = listdata_copy;
        notifyDataSetChanged();
    }

    // retrieves the filtered search results
    protected LinkedList<Contact> getFilteredResults(String constraint) {
        LinkedList<Contact> results = new LinkedList<>();
        for (Contact item : listdata) {
            if (item.getName().toLowerCase().contains(constraint)||item.getPhoneNumber().contains(constraint)) {
                results.add(item);
            }
        }
        if(constraint.equals(""))
        {
            results=listdata;
        }
        return results;
    }
}
