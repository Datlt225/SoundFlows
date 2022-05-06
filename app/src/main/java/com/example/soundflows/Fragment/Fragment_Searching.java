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
    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewSearching;
    TextView txtNotFound;

    SearchingAdapter searchingAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_searching, container, false);
        toolbar = view.findViewById(R.id.toolbarSearching);
        recyclerViewSearching = view.findViewById(R.id.recyclerviewSearching);
        txtNotFound = view.findViewById(R.id.textviewNotFound);

        //định dạng lại activity
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Searching");
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

            /**
             * tìm kiếm khi đã nhập xong
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchingWord(query);
                return true;
            }

            /**
             * Tím kiếm khi text thay đổi
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
//                searchingWord(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchingWord (String word) {
        Dataservice dataservice = APIService.getService();
        Call<List<Song>> callback = dataservice.GetDataSearching(word);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                try {
                    ArrayList<Song> songArrayList = (ArrayList<Song>) response.body();
                    if (songArrayList.size() > 0) {
                        searchingAdapter = new SearchingAdapter(getActivity(), songArrayList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerViewSearching.setLayoutManager(linearLayoutManager);
                        recyclerViewSearching.setAdapter(searchingAdapter);
                        txtNotFound.setVisibility(View.GONE);
                        recyclerViewSearching.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewSearching.setVisibility(View.GONE);
                        txtNotFound.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}
