package com.adasoranina.aplikasisqlite.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adasoranina.aplikasisqlite.R;
import com.adasoranina.aplikasisqlite.helper.DatabaseHelper;
import com.adasoranina.aplikasisqlite.model.Data;

public class AddEditActivity extends BaseActivity {

    public static final String KEY_DATA = "DATA";
    private static final String KEY_MODE = "MODE";

    private TextView textId;
    private EditText inputName;
    private EditText inputAddress;

    private final DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_add_edit);

        textId = findViewById(R.id.text_id);
        inputName = findViewById(R.id.input_name);
        inputAddress = findViewById(R.id.input_address);
        Button buttonSubmit = findViewById(R.id.button_submit);
        Button buttonCancel = findViewById(R.id.button_cancel);

        buttonSubmit.setOnClickListener(v -> {
            try {
                submitData();
            } catch (Exception e) {
                e.printStackTrace();
                showMessage(R.string.error_parse_id);
            }
        });

        buttonCancel.setOnClickListener(v -> {
            resetView();
            finish();
        });

        setUpActionBar();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            resetView();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getActionBarTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private String getActionBarTitle() {
        Mode mode = (Mode) getIntent().getSerializableExtra(KEY_MODE);

        if (mode == Mode.EDIT) {
            return getString(R.string.title_edit_data);
        } else if (mode == Mode.ADD) {
            return getString(R.string.title_add_data);
        } else {
            throw new IllegalArgumentException(getString(R.string.error_mode_not_found));
        }
    }

    private void getData() {
        Mode mode = (Mode) getIntent().getSerializableExtra(KEY_MODE);

        if (mode == Mode.EDIT) {
            Bundle bundle = getIntent().getExtras();
            Data data = (Data) bundle.getSerializable(KEY_DATA);

            if (data != null) {
                textId.setText(data.getStringId());
                inputName.setText(data.getName());
                inputAddress.setText(data.getAddress());
            }
            return;
        }

        textId.setVisibility(View.GONE);
    }

    private void submitData() {
        Mode mode = (Mode) getIntent().getSerializableExtra(KEY_MODE);

        String name = inputName.getText().toString().trim();
        String address = inputAddress.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty()) {
            showMessage(R.string.alert_empty_name_address);
            return;
        }

        if (mode == Mode.EDIT) {
            int id = Integer.parseInt(textId.getText().toString().trim());
            updateData(id, name, address);
            return;
        }

        saveData(name, address);
    }

    private void updateData(int id, String name, String address) {
        if (id > 0) {
            dbHelper.update(id, name, address);
            resetView();
            onBackPressed();
        }
    }

    private void saveData(String name, String address) {
        dbHelper.insert(name, address);
        resetView();
        onBackPressed();
    }

    private void resetView() {
        inputName.requestFocus();
        inputName.setText(null);
        inputAddress.setText(null);
    }

    public enum Mode {
        ADD, EDIT;
    }

    public static void navigate(Context context, Mode mode, @Nullable Bundle bundle) {
        Intent intent = new Intent(context, AddEditActivity.class);
        intent.putExtra(KEY_MODE, mode);
        if (bundle != null) intent.putExtras(bundle);

        context.startActivity(intent);
    }

}
