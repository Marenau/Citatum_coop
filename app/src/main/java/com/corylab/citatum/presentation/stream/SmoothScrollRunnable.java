package com.corylab.citatum.presentation.stream;

import androidx.recyclerview.widget.RecyclerView;

public class SmoothScrollRunnable implements Runnable {
    private final RecyclerView recyclerView;
    private final int delayMillis = 100;
    private boolean scrollToEnd = true;

    public SmoothScrollRunnable(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void run() {
        int scrollAmount = 5;
        if (scrollToEnd) {
            recyclerView.smoothScrollBy(scrollAmount, 0);
            if (!recyclerView.canScrollHorizontally(1)) {
                scrollToEnd = false;
                recyclerView.postDelayed(this, 2000);
                return;
            }
        } else {
            recyclerView.smoothScrollBy(-scrollAmount, 0);
            if (!recyclerView.canScrollHorizontally(-1)) {
                scrollToEnd = true;
                recyclerView.postDelayed(this, 2000);
                return;
            }
        }
        recyclerView.postDelayed(this, delayMillis);
    }

    public void start() {
        recyclerView.postDelayed(this, delayMillis);
    }

    public void stop() {
        recyclerView.removeCallbacks(this);
    }
}