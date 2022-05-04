package com.example.soundflows.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Adapter.SearchingAdapter;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Searching extends Fragment {
    private RecyclerView recyclerViewSearching;
    private TextView tvNoData;
    private SearchingAdapter searchingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searching, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbarSearching);
        recyclerViewSearching = view.findViewById(R.id.rvSearching);
        tvNoData = view.findViewById(R.id.tvNoSong);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searching_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_searching);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchingKeySong(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void SearchingKeySong(String word) {
        Dataservice dataservice = APIService.getService();
        Call<List<Song>> callback = dataservice.GetDataSearching(word);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                try {
                    ArrayList<Song> songArrayList = (ArrayList<Song>) response.body();

                    if (songArrayList.size() > 0) {
                        searchingAdapter = new SearchingAdapter(getActivity(), songArrayList);
                        LinearLayoutManager linearLayoutManager =
                                new LinearLayoutManager(getActivity());

                        recyclerViewSearching.setLayoutManager(linearLayoutManager);
                        recyclerViewSearching.setAdapter(searchingAdapter);
                        tvNoData.setVisibility(View.GONE);
                        recyclerViewSearching.setVisibility(View.VISIBLE);

                    } else {
                        recyclerViewSearching.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}
