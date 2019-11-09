package com.lab.calorie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListMenuAdapter extends RecyclerView.Adapter<ListMenuAdapter.ListMenuViewHolder>{

    private List<Menu> menuList;
    private Context context;

    public ListMenuAdapter(Context context){
        this.context = context;
    }

    class ListMenuViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        CardView listMenuCardView;
        TextView textViewListMenuDate;

        ListMenuViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            listMenuCardView = mView.findViewById(R.id.list_menu_card_view);
            textViewListMenuDate = mView.findViewById(R.id.textViewListMenuDate);
        }

    }

    @Override
    public ListMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_menu_row, parent, false);
        return new ListMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListMenuViewHolder holder, final int position) {
        int greyColor = holder.mView.getResources().getColor(R.color.bmrGrey);
        int darkBlueColor = holder.mView.getResources().getColor(R.color.bmrDarkBlue);

        // String listmenuName = listmenuList.get(position).getName();
        // String listmenuNutrient = Integer.toString(listmenuList.get(position).getKcal());

        holder.textViewListMenuDate.setText(menuList.get(position).getCalendarInSlash());

        holder.mView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 System.out.println("position yang diklik di list menu adapter adalah " + position);
                 fragmentJump(menuList.get(position));
//                 notifyDataSetChanged();
             }
         });

//         if (selectedItems[position] == 1) {
//             holder.listmenuCardView.setCardBackgroundColor(darkBlueColor);
//         } else {
//             holder.listmenuCardView.setCardBackgroundColor(greyColor);
//         }
    }

    void setAllMenu(List<Menu> menuList){
        this.menuList = menuList;
        // this.selectedItems = new int[listmenuList.size()];
        // for (int i = 0; i < listmenuList.size(); i++) {
        //     selectedItems[i] = 0;
        // }
        System.out.println("size dari listmenuList di setAllFood adalah " + menuList.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (menuList != null) {
            return menuList.size();
        }
        return 0;
    }

    private void fragmentJump(Menu mItemSelected) {
        DetailMenuFragment fragment = new DetailMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item_selected_key", mItemSelected);
//        bundle.putParcelable("item_selected_key", mItemSelected);
        fragment.setArguments(bundle);
        switchContent(R.id.list_detail_menu_fragment, fragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof ListMenuActivity) {
            ListMenuActivity listMenuActivity = (ListMenuActivity) context;
            Fragment frag = fragment;
            listMenuActivity.switchContent(id, frag);
        }

    }

}