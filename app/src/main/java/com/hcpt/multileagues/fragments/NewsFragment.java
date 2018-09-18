package com.hcpt.multileagues.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.adapters.NewsAdapter;
import com.hcpt.multileagues.json.utils.ParserUtils;
import com.hcpt.multileagues.modelmanager.ModelManager;
import com.hcpt.multileagues.modelmanager.ModelManagerListener;
import com.hcpt.multileagues.objects.FeedsObj;
import com.hcpt.multileagues.xml.utils.XMLParser;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private ListView mLvNews;
    private NewsAdapter mNewsRSSAdapter;
    private ArrayList<FeedsObj> mArrFeed;
    private View view;

    private String mNewsUrl = "";
    private TextView lbl_no_data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Allow display option menu in this fragment.
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_news, container, false);

        initControls();
        initNewsFromRSS();

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);

        // Hide filter menu.
        MenuItem item = menu.findItem(R.id.action_filter);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_save).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            initNewsFromRSS();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        mLvNews = (ListView) view.findViewById(R.id.lv_feeds);
        lbl_no_data = (TextView) view.findViewById(R.id.lbl_no_data);
        mLvNews.setOnItemClickListener(listener);

    }

    OnItemClickListener listener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // Redirect to news page.
            try {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mArrFeed.get(position).getmLinkNews()));

                startActivity(i);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "News is unavailable",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void setAdapter() {
        mNewsRSSAdapter = new NewsAdapter(getContext(), mArrFeed);
        mLvNews.setAdapter(mNewsRSSAdapter);
    }

    private void initNewsFromRSS() {
        // Get url.
        ModelManager.getFeeds(getActivity(), false, new ModelManagerListener() {

            @Override
            public void onSuccess(Object object) {
                String json = (String) object;
                mNewsUrl = ParserUtils.parserFeeds(json);

                ModelManager.getFeedContent(getActivity(), mNewsUrl, true,
                        new ModelManagerListener() {

                            @Override
                            public void onSuccess(Object object) {
                                String xml = (String) object;
                                mArrFeed = XMLParser.parserNews(xml);
                                setAdapter();
                                if(mArrFeed.size()==0){
                                    lbl_no_data.setVisibility(View.VISIBLE);
                                } else {
                                    lbl_no_data.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {
                                // TODO Auto-generated method stub
                                lbl_no_data.setVisibility(View.VISIBLE);
                            }
                        });
            }

            @Override
            public void onError() {
                // TODO Auto-generated method stub

            }
        });
    }
}
