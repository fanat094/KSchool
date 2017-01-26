package com.ks.fanat94.kschool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ks.fanat94.kschool.SQLDataBase.DBHelper;
import com.ks.fanat94.kschool.adapters.MyAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mehdi.sakout.dynamicbox.DynamicBox;

import static com.ks.fanat94.kschool.R.layout.recycler_view;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ReadRss readRss;
    ActionBar actionBar;
    public RecyclerView rrecyclerView;
    private SwipeRefreshLayout swipeContainer;
    public DBHelper dbHelper;
    final String LOG_TAG = "rssLogs";
    Cursor c;
    private DynamicBox boxContentView;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dbHelper = new DBHelper(getActivity());
        View mMainView = inflater.inflate(recycler_view, container, false);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        rrecyclerView = (RecyclerView) mMainView.findViewById(R.id.my_recycler_view);
        rrecyclerView.setItemAnimator(new DefaultItemAnimator());
        rrecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rrecyclerView.setLayoutManager(llm);
        setRetainInstance(true);
        swipeContainer = (SwipeRefreshLayout) mMainView.findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);

        /*if (isNetworkAvailable(getActivity())) {
            readRss = new ReadRss(getActivity(), rrecyclerView);
            readRss.execute();
            Toast.makeText(getActivity(),
                    "Internet YES!", Toast.LENGTH_SHORT).show();
            Log.d("itemNet", "11111111111111");
        } else {

        Toast.makeText(getActivity(),
        "Internet NO!", Toast.LENGTH_SHORT).show();
        Log.d("itemNet", "11111111111111");

        //
        SQLiteDatabase datb = dbHelper.getReadableDatabase();
        String hh = "select * from kstable;";
        c = datb.rawQuery(hh, null);

        ArrayList<FeedItem> books = new ArrayList<FeedItem>();

        // 3. go over each row, build book and add it to list
        FeedItem book = null;
        if (c.moveToFirst()) {
        do {
        book = new FeedItem();
        book.setTitle(c.getString(0));
        book.setDescription(c.getString(1));
            book.setPubDate(c.getString(2));

        // Add book to books
        books.add(book);
        } while (c.moveToNext());

        MyAdapter adapter = new MyAdapter(getContext(), books);
        rrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rrecyclerView.setAdapter(adapter);
        //swipeContainer.setRefreshing(false);
        // }
        }

        }*/

        return mMainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        boxContentView = new DynamicBox(getActivity(), view);

        if (isNetworkAvailable(getActivity())) {

            boxContentView.showLoadingLayout();
            readRss = new ReadRss(getActivity());
            readRss.execute();

        } else {

            boxContentView.setInternetOffTitle("OFFLINE");
            boxContentView.setInternetOffMessage("Try again...");
            boxContentView.showInternetOffLayout();

            SQLiteDatabase datb = dbHelper.getReadableDatabase();
            String hh = "select * from kstable;";
            c = datb.rawQuery(hh, null);

            ArrayList<FeedItem> books = new ArrayList<FeedItem>();

            // 3. go over each row, build book and add it to list
            FeedItem book = null;
            if (c.moveToFirst()) {
                do {
                    book = new FeedItem();
                    book.setTitle(c.getString(0));
                    book.setDescription(c.getString(1));
                    book.setPubDate(c.getString(2));

                    // Add book to books
                    books.add(book);
                } while (c.moveToNext());

                MyAdapter adapter = new MyAdapter(getContext(), books);
                rrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                rrecyclerView.setAdapter(adapter);
            }
        }

        boxContentView.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkAvailable(getActivity())) {

                    boxContentView.showLoadingLayout();

                    readRss = new ReadRss(getActivity());
                    readRss.execute();

                } else {

                    boxContentView.setInternetOffTitle("OFFLINE");
                    boxContentView.setInternetOffMessage("Try again...");
                    boxContentView.showInternetOffLayout();

                    SQLiteDatabase datb = dbHelper.getReadableDatabase();
                    String hh = "select * from kstable;";
                    c = datb.rawQuery(hh, null);

                    ArrayList<FeedItem> books = new ArrayList<FeedItem>();

                    // 3. go over each row, build book and add it to list
                    FeedItem book = null;
                    if (c.moveToFirst()) {
                        do {
                            book = new FeedItem();
                            book.setTitle(c.getString(0));
                            book.setDescription(c.getString(1));
                            book.setPubDate(c.getString(2));

                            // Add book to books
                            books.add(book);
                        } while (c.moveToNext());

                        MyAdapter adapter = new MyAdapter(getContext(), books);
                        rrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        rrecyclerView.setAdapter(adapter);
                    }
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

    @Override
    public void onRefresh() {

        if (isNetworkAvailable(getActivity())) {
            readRss = new ReadRss(getActivity());
            readRss.execute();
        }
        else {

            SQLiteDatabase datb = dbHelper.getReadableDatabase();
            String hh = "select * from kstable;";
            c = datb.rawQuery(hh, null);

            ArrayList<FeedItem> books = new ArrayList<FeedItem>();

            // 3. go over each row, build book and add it to list
            FeedItem book = null;
            if (c.moveToFirst()) {
                do {
                    book = new FeedItem();
                    book.setTitle(c.getString(0));
                    book.setDescription(c.getString(1));
                    book.setPubDate(c.getString(2));
                    // Add book to books
                    books.add(book);
                } while (c.moveToNext());

                MyAdapter adapter = new MyAdapter(getContext(), books);
                rrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                rrecyclerView.setAdapter(adapter);
                swipeContainer.setRefreshing(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boxContentView.hideAll();
        boxContentView.showChildNull();
    }

    public class ReadRss extends AsyncTask<Void, Void, Void> {
        Context context;
        String address = "http://kolybaivka-edu.at.ua/news/rss/";
        URL url;
        ArrayList<FeedItem> feedItems;

        //Constructure
        public ReadRss(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            boxContentView.showLoadingLayout();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MyAdapter adapter = new MyAdapter(context, feedItems);
            rrecyclerView.setLayoutManager(new LinearLayoutManager(context));
            rrecyclerView.setAdapter(adapter);
            swipeContainer.setRefreshing(false);
            boxContentView.hideAll();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Getdata() function will return xml doc which will be given for processing in processXml() mthode
            ProcessXml(Getdata());
            return null;
        }

        private void ProcessXml(Document data) {
            if (data != null) {
                feedItems = new ArrayList<>();
                Element root = data.getDocumentElement();
                Node channel = root.getChildNodes().item(1);
                NodeList items = channel.getChildNodes();
                for (int i = 0; i < items.getLength(); i++) {
                    Node cureentchild = items.item(i);
                    if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                        FeedItem item = new FeedItem();
                        NodeList itemchilds = cureentchild.getChildNodes();
                        for (int j = 0; j < itemchilds.getLength(); j++) {
                            Node cureent = itemchilds.item(j);
                            if (cureent.getNodeName().equalsIgnoreCase("title")) {
                                item.setTitle(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("description")) {
                                item.setDescription(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("pubDate")) {
                                item.setPubDate(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("link")) {
                                item.setLink(cureent.getTextContent());
                            } else if (cureent.getNodeName().equalsIgnoreCase("enclosure")) {
                                String url = cureent.getAttributes().item(0).getTextContent();
                                item.setThumbnailUrl(url);
                            }
                        }
                        feedItems.add(item);

                        Log.d("itemTitle", item.getTitle());
                        Log.d("itemDescription", item.getDescription());
                        Log.d("itemPubDate", item.getPubDate());
                        Log.d("itemLink", item.getLink());
                    }
                }

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("delete from kstable;");
                for (int index = 0; index < feedItems.size(); index++) {
                    FeedItem p = feedItems.get(index);
                    ContentValues cv = new ContentValues();
                    Log.d(LOG_TAG, "--- Insert in kstable: ---");

                    cv.put("titleitem", p.getTitle());
                    cv.put("descriptionitem", p.getDescription());
                    cv.put("dateitem", p.getPubDate());
                    // вставляем запись и получаем ее ID
                    long rowID = db.insert("kstable", null, cv);
                    Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                }
            }
        }

        public Document Getdata() {
            try {
                url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document xmlDoc = builder.parse(inputStream);
                return xmlDoc;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}