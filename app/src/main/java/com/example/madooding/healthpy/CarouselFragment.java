package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarouselFragment extends Fragment {
    private int imgResource;
    private String name;

    public CarouselFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.imgResource = getArguments().getInt("ImgResource");
        this.name = getArguments().getString("Name");
        ImageView imgView = (ImageView) getActivity().findViewById(R.id.carousel_image_view);
        Picasso.with(getContext()).load(getArguments().getString("ImgUrl")).into(imgView);

        TextView carouselTextView = (TextView) view.findViewById(R.id.carousel_text_view);
        carouselTextView.setText(this.name);
    }



    public static CarouselFragment newInstance(String imgUrl, String name){
        CarouselFragment carousel = new CarouselFragment();

        Bundle args = new Bundle();
        args.putString("ImgUrl", imgUrl);
        args.putString("Name", name);
        carousel.setArguments(args);

        return carousel;
    }

}
