package com.ks.fanat94.kschool.tabs_fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ks.fanat94.kschool.R;
import com.ks.fanat94.kschool.adapters.ScheduleAdapter;
import com.ks.fanat94.kschool.adapters.ScheduleItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import mehdi.sakout.dynamicbox.DynamicBox;

public class MondayFragment extends Fragment {
    private LinearLayoutManager lLayout;
    public RecyclerView recyclerView;

    Title readRss;
    private DynamicBox boxContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mMainView = inflater.inflate(R.layout.recycler_view_shedule, container, false);
        recyclerView = (RecyclerView) mMainView.findViewById(R.id.recycler_view_shcheduler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        setRetainInstance(true);
        return mMainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        boxContentView = new DynamicBox(getActivity(), view);


        boxContentView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable(getActivity())) {

                    boxContentView.showLoadingLayout();

                    readRss = new Title(getActivity());
                    readRss.execute();

                } else {

                    boxContentView.setInternetOffTitle("OFFLINE");
                    boxContentView.setInternetOffMessage("Try again...");
                    boxContentView.showInternetOffLayout();

                }
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String url = "http://kolybaivka-school.esy.es/schedule_page.html";
        Context context;
        Elements tds;
        ArrayList<ScheduleItem> shdlist = new ArrayList<>();;

        public Title(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            boxContentView.showLoadingLayout();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //shdlist = new ArrayList<>();
           // item = new ScheduleItem();
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Identify Table Class "basecont"
                for (Element table : document.select("div[class=basecont]")) {
                    // Identify all the table row's(tr)
                    for (Element row : table.select("tr:gt(0)")) {
                        ScheduleItem item = new ScheduleItem();

                        // Identify all the table cell's(td)
                        tds = row.select("td");
                        item.setName_Les(tds.get(1).text());
                        item.setLTime_Les(tds.get(0).text());
                        Log.d("TDDDDDDD1",tds.get(0).text());
                        shdlist.add(item);
                        Log.d("shdlist",item.getName_Les());

                    }

                }

                // Log.d("shdlist",shdlist.get(1).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ScheduleAdapter adapter = new ScheduleAdapter(context,shdlist);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            boxContentView.hideAll();
        }
    }
}

   /* private ArrayList<ScheduleItem> getDataSet() {
        ArrayList results = new ArrayList<ScheduleItem>();

        results.add(new ScheduleItem("Математика",
                    "08:30-09:15"));
        results.add(new ScheduleItem("Історія України",
                "9:25-10:10"));
        results.add(new ScheduleItem("Фізика",
                "10:40-11:25"));
        results.add(new ScheduleItem("Фізкультура",
                "11:35-12:20"));

        return results;
    }*/