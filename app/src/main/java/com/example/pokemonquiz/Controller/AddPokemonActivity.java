package com.example.pokemonquiz.Controller;

import static android.graphics.Bitmap.createScaledBitmap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.R;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddPokemonActivity extends AppCompatActivity {
    Bitmap picture;
    EditText answerText;
    private ImageView imageView;

    public PokemonViewModel pokemonViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);

        imageView = findViewById(R.id.image_view);

        Button existingPhotoButton = (Button) findViewById(R.id.existing_photo_button);
        existingPhotoButton.setOnClickListener(view -> existingPhoto());

        answerText = (EditText) findViewById(R.id.answerEntry);

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> submitEntry());
        exitButton();
    }

    public void exitButton() {
        Button btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(AddPokemonActivity.this, new MainActivity().getClass());
            startActivity(intent);
            finish();
        });
    }

        ActivityResultLauncher<Intent> getPhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null && data.getData() != null) {
                            Uri uri = data.getData();
                            System.out.println("Data got");
                            try {
                                picture = BitmapFactory.decodeStream(
                                        getContentResolver().openInputStream(uri)
                                );
                                imageView.setImageBitmap(picture);

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });


        private void existingPhoto() {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            getPhoto.launch(i);
        }


    void submitEntry() {
        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        String answer = answerText.getText().toString();
        System.out.println("Added image with answer " + answer);
        if (picture != null && !answer.equals("")) {
            Pokemon pokemon = new Pokemon(answer,createScaledBitmap(picture,1000,1000,true));
            pokemonViewModel.insertPokemon(pokemon);
        }
        finish();
    }

}