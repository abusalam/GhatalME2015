package com.github.abusalam.ghatalme2015;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Handbook extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handbook);
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<Page> pageList = new ArrayList<>();

        ArrayList<Integer> imgList = new ArrayList<>();
        imgList.add(R.mipmap.p01);
        imgList.add(R.mipmap.p02);
        imgList.add(R.mipmap.p03);
        imgList.add(R.mipmap.p04);
        imgList.add(R.mipmap.p05);
        imgList.add(R.mipmap.p06);
        imgList.add(R.mipmap.p07);
        imgList.add(R.mipmap.p08);
        imgList.add(R.mipmap.p09);
        imgList.add(R.mipmap.p10);
        imgList.add(R.mipmap.p11);
        imgList.add(R.mipmap.p12);
        imgList.add(R.mipmap.p13);
        imgList.add(R.mipmap.p14);
        imgList.add(R.mipmap.p15);
        imgList.add(R.mipmap.p16);
        imgList.add(R.mipmap.p17);
        imgList.add(R.mipmap.p18);
        imgList.add(R.mipmap.p19);

        for (int anImgList : imgList) {
            Page mPage = new Page(anImgList);
            pageList.add(mPage);
        }

        listView.setAdapter(new PageAdapter(Handbook.this, R.layout.page_view, pageList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_handbook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PageAdapter extends ArrayAdapter<Page> {
        int mResource;

        public PageAdapter(Context context, int resource, List<Page> Pages) {
            super(context, resource, Pages);
            this.mResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout pageView;

            Page mPage = getItem(position);

            if (convertView == null) {
                pageView = new LinearLayout(getContext());

                LayoutInflater li = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                li.inflate(mResource, pageView, true);

            } else {
                pageView = (LinearLayout) convertView;
            }

            ImageView imgView = (ImageView) pageView.findViewById(R.id.imageView);
            imgView.setImageResource(mPage.getImage());

            return pageView;
        }
    }
}
