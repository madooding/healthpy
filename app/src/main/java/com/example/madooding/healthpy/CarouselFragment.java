package com.example.madooding.healthpy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarouselFragment extends Fragment {
    private int imgResource;

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
        ImageView imgView = (ImageView) getActivity().findViewById(R.id.carousel_image_view);
        imgView.setImageResource(this.imgResource);
    }



    public static CarouselFragment newInstance(int imgResource){
        CarouselFragment carousel = new CarouselFragment();

        Bundle args = new Bundle();
        args.putInt("ImgResource", imgResource);
        carousel.setArguments(args);

        return carousel;
    }

}
