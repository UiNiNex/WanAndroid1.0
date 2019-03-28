package wanandroid.c03.jy.com.wanandroid.ui.mapNavigation;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import java.util.List;

import wanandroid.c03.jy.com.wanandroid.R;
import wanandroid.c03.jy.com.wanandroid.base.BaseActivity;

public class MapNavigations  extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_navigations);

        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();

    }

}
