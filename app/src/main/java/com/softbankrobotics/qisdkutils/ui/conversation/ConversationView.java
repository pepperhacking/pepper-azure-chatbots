package com.softbankrobotics.qisdkutils.ui.conversation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.softbankrobotics.chatbotdemo.R;

public class ConversationView extends RecyclerView {

    ConversationAdapter adapter = new ConversationAdapter();

    public ConversationView(Context context) {
        this(context, null);
    }

    public ConversationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConversationView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public void addLine(String text, ConversationItemType type) {
        adapter.addItem(text, type);
        scrollToPosition(adapter.getItemCount() - 1);
    }

    private void setup() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(adapter);

        Drawable drawable = getContext().getDrawable(R.drawable.empty_divider_big);
        if (drawable != null) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            dividerItemDecoration.setDrawable(drawable);
            this.addItemDecoration(dividerItemDecoration);
            Log.w("ConversationView", "Setup done.");
        } else {
            Log.w("ConversationView", "No drawable");
        }
    }
}
