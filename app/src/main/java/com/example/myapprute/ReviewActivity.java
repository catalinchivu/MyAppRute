package com.example.myapprute;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class ReviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // obtin numele din intent
        String fullName = getIntent().getStringExtra("FULL_NAME");

        // caut TextView unde afisez mesajul
        TextView textViewMessage = findViewById(R.id.textViewMessage);

        String thankYouMessage = getString(R.string.thank_you_for_using_app);
        String rateAppMessage = getString(R.string.rate_app_message);
        String personalizedPart1 = getString(R.string.dear_user);
        String personalizedPart2 = getString(R.string.thank_you_for_using_app_personalized);
        String thankYouForRating = getString(R.string.thank_you_for_rating);
        String ratingBarNullError = getString(R.string.rating_bar_null);

        //daca exista un nume, setez mesajul personalizat si transfer string din activitate; alternativ, folosesc un mesaj implicit.
        String message = (fullName != null && !fullName.isEmpty())
                ? personalizedPart1 + fullName + personalizedPart2
                : thankYouMessage + "\n" + rateAppMessage;
        //        ? "Dragă " + fullName + ", îți mulțumim pentru utilizarea aplicației! Ne-ar fi de mare folos sa stim cat de mult ti-a placut aplicatia, dandu-ne un rating mai jos."
        //        : "Îți mulțumim pentru utilizarea aplicației!\nNe-ar fi de mare folos sa stim cat de mult ti-a placut aplicatia, dandu-ne un rating mai jos.";


        textViewMessage.setText(message);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        // set listener pt completare rating
        if (ratingBar != null) {
            ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        //Toast - mesaj dupa completare rating
                        //Toast.makeText(ReviewActivity.this, "Mulțumim pentru rating!\n La revedere!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReviewActivity.this, thankYouForRating, Toast.LENGTH_SHORT).show();

                        // inchid aplicația dupa 2 secunde (predefinit dupa preferinta)
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finishAffinity(); // inchid toate activitatile, ies din aplicatie
                            }
                        }, 2000); // 2000 milisec = 2 sec
                    }
                }
            });
        } else {
            // daca ratingBar este null, log sau arunc o excepție
            //Log.e("ReviewActivity", "ratingBar is null!");
            Log.e("ReviewActivity", ratingBarNullError);
        }
    }

}
