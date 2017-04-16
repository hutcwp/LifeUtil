package club.hutcwp.lifeutil.ui.girl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import club.hutcwp.lifeutil.R;

public class PicDetailFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_girl_pic, container, false);
        Log.d("url", PicDetailActivity.EXTRA_IMAGE_URL);
        Glide.with(getActivity()).load(PicDetailActivity.EXTRA_IMAGE_URL).into((ImageView) view.findViewById(R.id.image));
        return view;
    }


}
