package kytallo.com.themangadocs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kytallo.com.themangadocs.utils.ImageUrlUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {
    private final List<Comic> comicList;
    private final OnComicClickListener listener;

    public ComicAdapter(List<Comic> comicList, OnComicClickListener listener) {
        this.comicList = comicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        Comic comic = comicList.get(position);
        String coverUrl = ImageUrlUtils.getFullCoverUrl(
                comic.getId(),
                comic.getAttributes().getCoverImage()
        );
        if (ImageUrlUtils.isValidUrl(coverUrl)) {
            Picasso.get()
                    .load(coverUrl)
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img)
                    .into(holder.imgCover);
        } else {
            holder.imgCover.setImageResource(R.drawable.img);
        }
        // Null checks for all nested objects
        if (comic.getAttributes() != null && comic.getAttributes().getTitle() != null) {
            String title = comic.getAttributes().getTitle().getEn();
            holder.tvTitle.setText(title != null ? title : "Không có tiêu đề");
        }

        if (comic.getAttributes() != null && comic.getAttributes().getDescription() != null) {
            String description = comic.getAttributes().getDescription().getEn();
            holder.tvDescription.setText(description != null ? description : "Không có mô tả");
        }

        // Load image with error handling
        if (comic.getAttributes() != null && comic.getAttributes().getCoverImage() != null) {
            String coverUrls = comic.getCoverImageUrl();
            if (coverUrls != null) {
                Log.d("ComicAdapter", "Loading cover image: " + coverUrl);
                Picasso.get()
                        .load(coverUrl)
                        .placeholder(R.drawable.img)
                        .error(R.drawable.img)
                        .into(holder.imgCover, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("ComicAdapter", "Image loaded successfully");
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("ComicAdapter", "Error loading image: " + e.getMessage());
                                holder.imgCover.setImageResource(R.drawable.img);
                            }
                        });
            } else {
                holder.imgCover.setImageResource(R.drawable.img);
            }

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onComicClick(comic);
                }
            });
        } else {
            holder.imgCover.setImageResource(R.drawable.img);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onComicClick(comic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList != null ? comicList.size() : 0;
    }

    public void updateData(List<Comic> newComics) {
        if (comicList != null) {
            comicList.clear();
            if (newComics != null) {
                comicList.addAll(newComics);
            }
            notifyDataSetChanged();
        }
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle, tvDescription;
        private final ImageView imgCover;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgCover = itemView.findViewById(R.id.imgCover);
        }
    }

    public interface OnComicClickListener {
        void onComicClick(Comic comic);
    }
}