package kytallo.com.themangadocs;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckinDialog extends Dialog {
    private Context context;
    private Button btnCheckin;
    private TextView tvCoins;
    private GridLayout gridCheckin;

    public CheckinDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_checkin);

        btnCheckin = findViewById(R.id.btnCheckin);
        tvCoins = findViewById(R.id.tvCoins);
        gridCheckin = findViewById(R.id.gridCheckin);

        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int coins = prefs.getInt("coins", 0);
        tvCoins.setText("Bạn có: " + coins + " xu");

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String lastCheckin = prefs.getString("lastCheckinDate", "");

        if (today.equals(lastCheckin)) {
            btnCheckin.setEnabled(false);
            btnCheckin.setText("Đã điểm danh hôm nay");
        }

        btnCheckin.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("coins", coins + 10);
            editor.putString("lastCheckinDate", today);
            editor.apply();

            btnCheckin.setEnabled(false);
            btnCheckin.setText("Đã điểm danh hôm nay");
            tvCoins.setText("Bạn có: " + (coins + 10) + " xu");
            Toast.makeText(context, "Bạn đã nhận 10 xu!", Toast.LENGTH_SHORT).show();

            updateCheckinGrid();
        });

        updateCheckinGrid();
    }

    private void updateCheckinGrid() {
        gridCheckin.removeAllViews();
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int checkinDays = prefs.getInt("checkinDays", 0);

        for (int i = 0; i < 14; i++) {
            TextView dayView = new TextView(context);
            dayView.setText((i + 1) + "");
            dayView.setTextSize(18);
            dayView.setPadding(16, 16, 16, 16);
            dayView.setGravity(View.TEXT_ALIGNMENT_CENTER);

            GradientDrawable bg = new GradientDrawable();
            bg.setShape(GradientDrawable.OVAL);
            bg.setColor(i < checkinDays ? Color.GREEN : Color.GRAY);
            bg.setStroke(3, Color.WHITE);
            dayView.setBackground(bg);

            dayView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.flip_in));

            gridCheckin.addView(dayView);
        }
    }
}
