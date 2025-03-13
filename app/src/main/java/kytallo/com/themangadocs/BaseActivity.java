package kytallo.com.themangadocs;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void setupViews();
    protected abstract void setupListeners();
}
