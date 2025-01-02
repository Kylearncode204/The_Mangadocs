package kytallo.com.themangadocs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ReaderAdapter extends PagerAdapter {
    private List<PageItem> pages;
    private Context context;

    // Inner class để đại diện cho một trang
    public static class PageItem {
        private Integer resourceId; // Cho local resource
        private String url;         // Cho remote URL

        public PageItem(Integer resourceId) {
            this.resourceId = resourceId;
            this.url = null;
        }

        public PageItem(String url) {
            this.url = url;
            this.resourceId = null;
        }

        public boolean isLocalResource() {
            return resourceId != null;
        }

        public Integer getResourceId() {
            return resourceId;
        }

        public String getUrl() {
            return url;
        }
    }

    public ReaderAdapter(Context context) {
        this.context = context;
        this.pages = new ArrayList<>();
    }

    // Constructor cho local resources
    public static ReaderAdapter withLocalResources(Context context, List<Integer> resourceIds) {
        ReaderAdapter adapter = new ReaderAdapter(context);
        for (Integer resourceId : resourceIds) {
            adapter.pages.add(new PageItem(resourceId));
        }
        return adapter;
    }

    // Constructor cho remote URLs
    public static ReaderAdapter withRemoteUrls(Context context, List<String> urls) {
        ReaderAdapter adapter = new ReaderAdapter(context);
        for (String url : urls) {
            adapter.pages.add(new PageItem(url));
        }
        return adapter;
    }

    @Override
    public int getCount() {
        return pages != null ? pages.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.page_item, container, false);
        ImageView imageView = view.findViewById(R.id.imgPage);
        ProgressBar progressBar = view.findViewById(R.id.pageProgressBar);

        PageItem page = pages.get(position);
        progressBar.setVisibility(View.VISIBLE);

        if (page.isLocalResource()) {
            // Load local resource
            imageView.setImageResource(page.getResourceId());
            progressBar.setVisibility(View.GONE);
        } else {
            // Load remote URL
            Picasso.get()
                    .load(page.getUrl())
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progressBar.setVisibility(View.GONE);
                            imageView.setImageResource(R.drawable.img);
                        }
                    });
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    // Update với local resources
    public void updateWithLocalResources(List<Integer> resourceIds) {
        pages.clear();
        for (Integer resourceId : resourceIds) {
            pages.add(new PageItem(resourceId));
        }
        notifyDataSetChanged();
    }

    // Update với remote URLs
    public void updateWithRemoteUrls(List<String> urls) {
        pages.clear();
        for (String url : urls) {
            pages.add(new PageItem(url));
        }
        notifyDataSetChanged();
    }
}