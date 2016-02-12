package com.example.max.methis;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.max.methis.dummy.CarsContent;

/**
 * A fragment representing a single Car Model detail screen.
 * This fragment is either contained in a {@link CarModelListActivity}
 * in two-pane mode (on tablets) or a {@link CarModelDetailActivity}
 * on handsets.
 * modified  and adapted by: Maxime Sabran
 */
public class CarModelDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The cars content this fragment is presenting.
     */
    private CarsContent.Cars mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarModelDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = CarsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.carmodel_detail, container, false);

        /* Show the Cars content as text in a TextView.
         * modified
         */
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.carmodel_detail)).setText("Color : " + mItem.color +"\n" +"Year of construction : " + mItem.year);
        }

        return rootView;
    }
}
