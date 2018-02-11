package com.juborajsarker.passwordmanager.fragments.categories;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juborajsarker.passwordmanager.R;
import com.juborajsarker.passwordmanager.adapters.BankAdapter;
import com.juborajsarker.passwordmanager.java_class.BankPassword;

import java.util.ArrayList;
import java.util.List;


public class BankFragment extends Fragment {

    View view;

    private RecyclerView recyclerView;
    private BankAdapter adapter;
    private List<BankPassword> bankList;


    public BankFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bank, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        bankList = new ArrayList<>();
        adapter = new BankAdapter(getContext(), bankList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepare();

        return view;
    }

    private void prepare() {

        String title = "Facebook";
        BankPassword password = new BankPassword(title, "juboraj12345", "www.facebook.com", title.toUpperCase().charAt(0));
        bankList.add(password);


        title = "Yahoo";
        password = new BankPassword(title, "juboraj12345", "www.facebook.com", title.toUpperCase().charAt(0));
        bankList.add(password);


        title = "Twitter";
        password = new BankPassword(title, "juboraj12345", "www.facebook.com", title.toUpperCase().charAt(0));
        bankList.add(password);


        title = "Gmail";
        password = new BankPassword(title, "juboraj12345", "www.facebook.com", title.toUpperCase().charAt(0));
        bankList.add(password);

        title = "Linkedin";
        password = new BankPassword(title, "juboraj12345", "www.facebook.com", title.toUpperCase().charAt(0));
        bankList.add(password);


        title = "Github";
        password = new BankPassword(title, "juboraj12345", "www.facebook.com", title.toUpperCase().charAt(0));
        bankList.add(password);


        adapter.notifyDataSetChanged();


    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {


        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


