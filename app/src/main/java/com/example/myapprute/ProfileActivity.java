package com.example.myapprute;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private Spinner spinnerAge;
    private Button buttonSave;
    private Button buttonClear;
    private Button buttonExit;
    private Button buttonLogOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // initializare EditTexts, Spinner + setup
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerAge = findViewById(R.id.spinnerAge);

        setupEditTextListeners();

        setupSpinner();

        // initializare butoane
        buttonSave = findViewById(R.id.buttonSave);
        buttonClear = findViewById(R.id.buttonClear);
        //buttonExit = findViewById(R.id.buttonExit); //initial aveam buton exit si aici...
        // initializare butonul de log-off + review in noua activitate, cu preluare de nume in ReviewActivity, setare listener

        buttonLogOff = findViewById(R.id.buttonLogOff);
        buttonLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creare nou Intent pentru incepere ReviewActivity
                Intent intent = new Intent(ProfileActivity.this, ReviewActivity.class);

                // adaug numele ca extra in Intent
                String name = editTextName.getText().toString();
                intent.putExtra("FULL_NAME", name);

                // start activitate
                startActivity(intent);
            }
        });

        // set listener pentru butonul de salvare
        buttonSave.setOnClickListener(v -> saveData());

        // set listener pentru butonul de curatare date
        buttonClear.setOnClickListener(v -> clearData());

        // set listener pentru butonul de ieșire (initial), l-am scos
        //buttonExit.setOnClickListener(v -> exitApp());

        // set up bottom navigation
        setupBottomNavigation();
    }

    private void setupEditTextListeners() {
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // poate fi si necompletat
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // poate fi si necompletat
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 4) {
                    //editTextName.setError("Numele trebuie sa aiba cel putin 4 caractere.");
                    editTextName.setError(getString(R.string.profile_activity_alert_name_field));
                }
            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // poate fi si necompletat
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // poate fi si necompletat
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    //editTextEmail.setError("Introduceti o adresa de email valida.");
                    editTextEmail.setError(getString(R.string.profile_activity_alert_email_field));

                }
            }

        });
    }
    // dropdown cu doua inregistrari, daca are peste 18, sau sub 18 ani
    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.age_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapter);

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if("Nu, nu am 18 ani".equals(selectedItem)) {
                    showAlertForUnderage(); //ii afisez o alerta
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // temporar, daca nu selecteaza nimic, poate continua si fara
            }
        });
    }

    private void showAlertForUnderage() {
        new AlertDialog.Builder(ProfileActivity.this)
                //.setTitle("Restrictie de varsta")
                .setTitle(getString(R.string.profile_activity_underage_restriction_title))
                //.setMessage("Trebuie sa ai cel puțin 18 ani pentru a utiliza această aplicatie.")
                .setMessage(getString(R.string.profile_activity_underage_restriction_message))
                //.setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_profile) {
                //nu facem nimic, suntem aici deja

            } else if (itemId == R.id.bottom_navigation) {
                startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else if (itemId == R.id.bottom_start) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else if (itemId == R.id.bottom_route) {
                startActivity(new Intent(getApplicationContext(), RoutesActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            } else if (itemId == R.id.bottom_notifications) {
                startActivity(new Intent(getApplicationContext(), NotificationsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            return true;
        });
    }

    private void saveData() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String ageSelection = spinnerAge.getSelectedItem().toString();

        //String data = "Nume: " + name + "\nEmail: " + email + "\nVârstă: " + ageSelection;
        String data = getString(R.string.user_data_format, name, email, ageSelection);

        // salvare date in fișier text
        try {
            FileOutputStream fos = openFileOutput("userData.txt", MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();

            //Toast.makeText(this, "Datele au fost salvate.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, getString(R.string.data_saved), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(this, "Eroare la salvarea datelor.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, getString(R.string.data_save_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        // sterg continut EditText
        editTextName.setText("");
        editTextEmail.setText("");
        spinnerAge.setSelection(0); // implicit este selectat primul element din Spinner

        //Toast.makeText(this, "Datele au fost curatate.", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, getString(R.string.data_cleared), Toast.LENGTH_SHORT).show();
    }

    private void exitApp() {
        // inchid aplicația
        finishAffinity();
    }
}
//versiune 23.16 din 04.11.2023