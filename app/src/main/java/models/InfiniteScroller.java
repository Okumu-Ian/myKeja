package models;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by The Architect on 5/28/2018.
 */

public abstract class InfiniteScroller extends RecyclerView.OnScrollListener {

    private int lastTotal = 0;
    private boolean isLoading = true;
    private int visibleItems = 15;
    private int firstSeen, seenItemCount, totalItems;
    private int currentPage = 1;
    private GridLayoutManager manager;
    private RecyclerView.LayoutManager layoutManager;

    private InfiniteScroller()
    {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //getting items that are initially Visible
        seenItemCount = recyclerView.getChildCount();

        //getting actual number of items
        totalItems = recyclerView.getLayoutManager().getChildCount();

        //initialise the layout manager
        layoutManager = recyclerView.getLayoutManager();

        firstSeen = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

        int lastSeen = ((GridLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

        if (isLoading){
            if(totalItems > lastTotal)
            {
                isLoading = false;
                lastTotal = totalItems;
            }
        }

        if (!isLoading
                && (totalItems - visibleItems) <= (firstSeen+visibleItems))
        {
            currentPage++;
            onLoadMore(currentPage);
            isLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
