package kytallo.com.themangadocs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {
    private List<FavoriteItem> favoriteItems;

    public FavoritesAdapter(List<FavoriteItem> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteItem item = favoriteItems.get(position);
        holder.tvFavoriteTitle.setText(item.getTitle());
        holder.tvFavoriteSubtitle.setText(item.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvFavoriteTitle, tvFavoriteSubtitle;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFavoriteTitle = itemView.findViewById(R.id.tvFavoriteTitle);
            tvFavoriteSubtitle = itemView.findViewById(R.id.tvFavoriteSubtitle);
        }
    }
}
