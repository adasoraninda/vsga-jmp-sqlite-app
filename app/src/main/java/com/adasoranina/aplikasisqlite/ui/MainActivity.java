package com.adasoranina.aplikasisqlite.ui;

import static com.adasoranina.aplikasisqlite.ui.AddEditActivity.KEY_DATA;
import static com.adasoranina.aplikasisqlite.ui.AddEditActivity.Mode;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AlertDialog;

import com.adasoranina.aplikasisqlite.R;
import com.adasoranina.aplikasisqlite.adapter.MainAdapter;
import com.adasoranina.aplikasisqlite.helper.DatabaseHelper;
import com.adasoranina.aplikasisqlite.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends BaseActivity {
    private ListView listData;
    private MainAdapter mainAdapter;

    private final DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listData = findViewById(R.id.list_name);
        FloatingActionButton buttonAdd = findViewById(R.id.button_add);

        mainAdapter = new MainAdapter();

        setUpListData();
        getAllData();

        buttonAdd.setOnClickListener(v -> AddEditActivity.navigate(this, Mode.ADD, null));
    }

    @Override
    protected void onResume() {
        getAllData();
        super.onResume();
    }

    private void setUpListData() {
        listData.setAdapter(mainAdapter);

        listData.setOnItemClickListener((adapterView, view, i, l) -> {
            Data data = (Data) mainAdapter.getItem(i);

            editData(data);
        });

        listData.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Data data = (Data) mainAdapter.getItem(i);

            showItemAlertDialog(R.array.action_dialog,
                    (dialogInterface, which) -> {
                        if (which == 0) {
                            editData(data);
                        } else {
                            deleteData(data.getId());
                        }
                    });
            return true;
        });
    }


    private void updateListData(List<Data> listData) {
        mainAdapter.updateData(listData);
        mainAdapter.notifyDataSetChanged();
    }

    private void getAllData() {
        updateListData(dbHelper.getAllData());
    }

    private void editData(Data data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, data);
        AddEditActivity.navigate(this, Mode.EDIT, bundle);
    }

    private void deleteData(int id) {
        dbHelper.delete(id);
        getAllData();
    }

    private void showItemAlertDialog(
            @ArrayRes int arrayRes,
            DialogInterface.OnClickListener dialogInterface
    ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setItems(arrayRes, dialogInterface)
                .setCancelable(true)
                .show();
    }
}