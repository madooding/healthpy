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
    private ImageView imgView;

    private String imgUrl;
    private String name;
    public CarouselFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUrl = getArguments().getString("ImgUrl");
        name = getArguments().getString("Name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carousel, container,
                false);
        imgView = (ImageView)view.findViewById(R.id.carousel_image_view);
        Picasso.with(getContext()).load(imgUrl).into(imgView);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView carouselTextView = (TextView) view.findViewById(R.id.carousel_text_view);
        carouselTextView.setText(this.name);
    }

    @Override
    public void onResume() {
        super.onResume();
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
