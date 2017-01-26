package com.ks.fanat94.kschool;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ks.fanat94.kschool.adapters.ScheduleItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    // URL Address
    String url = "http://kolybaivka-school.esy.es/schedule_page.html";
    ProgressDialog mProgressDialog;
    TextView txttitle;
    Title readRss;
    Elements tds;
    ArrayList<ScheduleItem> shdlist;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = inflater.inflate(R.layout.recycler_view, container, false);
        Button titlebutton = (Button) mMainView.findViewById(R.id.titlebutton);
        txttitle = (TextView) mMainView.findViewById(R.id.titletxt);
        readRss = new Title();
        readRss.execute();
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    // Title AsyncTask
   private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            shdlist = new ArrayList<>();
            ScheduleItem item = new ScheduleItem();
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Identify Table Class "basecont"
                for (Element table : document.select("div[class=basecont]")) {

                    // Identify all the table row's(tr)
                    for (Element row : table.select("tr:gt(0)")) {
                        // Identify all the table cell's(td)
                         tds = row.select("td");
                        item.setLTime_Les(tds.get(0).toString());
                        item.setName_Les(tds.get(0).toString());
                        Log.d("TDDDDDDD",item.toString());
                    }
                }
                shdlist.add(item);
             // Log.d("shdlist",shdlist.get(1).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            //txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

}
