package com.example.tony.gymnasieschema;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by Tony on 2015-05-09.
 */
public class SchoolSearchActivity extends Activity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolsearch);

        final String[] schools = getResources().getStringArray(R.array.school);
        final String[] schoolsID = getResources().getStringArray(R.array.schoolId);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, schools);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        searchView = (SearchView)findViewById(R.id.searchView);

        listView.setTextFilterEnabled(true);
        setupSearchView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String alloy = (String) listView.getItemAtPosition(position);

                for(int i = 0; i < schools.length; i++) {
                    if (alloy == schools[i]) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        sharedPreferences.edit().putString("SchoolKey", schoolsID[i].toString()).commit();

                        Toast.makeText(getApplicationContext(), schools[i], Toast.LENGTH_LONG).show();
                    }
                }


                finish();
            }
        });

    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getString(R.string.search_hint));
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}
