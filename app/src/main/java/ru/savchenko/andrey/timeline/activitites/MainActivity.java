package ru.savchenko.andrey.timeline.activitites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.adapters.DragListAdapter;
import ru.savchenko.andrey.timeline.adapters.NotDragListAdapter;
import ru.savchenko.andrey.timeline.adapters.RecyclerListAdapter;
import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.helper.SimpleItemTouchHelperCallback;
import ru.savchenko.andrey.timeline.intefaces.Listener;
import ru.savchenko.andrey.timeline.managers.CardManager;

import static ru.savchenko.andrey.timeline.storage.Const.CORRECT_ANSWER;
import static ru.savchenko.andrey.timeline.storage.Const.MAIN_TITLE;
import static ru.savchenko.andrey.timeline.storage.Const.VICTORY;

public class MainActivity extends AppCompatActivity implements Listener, OnStartDragListener {
    public static final String TAG = "MainActivity";
    @BindView(R.id.rvTop)
    RecyclerView rvTop;
    @BindView(R.id.rvBottom)
    RecyclerView rvBottom;
    @BindView(R.id.tvEmptyListTop)
    TextView tvEmptyListTop;
    @BindView(R.id.tvEmptyListBottom)
    TextView tvEmptyListBottom;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.deck)
    ImageView ivDeck;
    @BindView(R.id.linContainer)
    LinearLayout linContainer;
    private CardManager cardManager;
    private NotDragListAdapter topListAdapter;
    private int counter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardManager = new CardManager();
        ButterKnife.bind(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(MAIN_TITLE);

        initTopRecyclerView();
        initBottomRecyclerView();

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    @Override
    public void onViewLocation(String text) {
        if (!text.equals("")) {
            tvLocation.setVisibility(View.VISIBLE);
        } else {
            tvLocation.setVisibility(View.GONE);
        }
    }

    @Override
    public int getRvTopPositionY() {
        return (int) rvTop.getY();
    }

    @Override
    public int getRvTopPositionX() {
        return (int) rvTop.getX();
    }

    @Override
    public int getResourceByString(String resName) {
        return getResources().getIdentifier(resName, "drawable", getPackageName());
    }

    private void initTopRecyclerView() {
        RecyclerListAdapter adapter = new RecyclerListAdapter(this, this, cardManager.getTopCards());
        rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rvTop.setHasFixedSize(true);
        rvTop.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvTop);

//        rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        topListAdapter = new NotDragListAdapter(cardManager.getTopCards(), this, cardManager, this);
//        rvTop.setAdapter(topListAdapter);
//        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
//        rvTop.setOnDragListener(topListAdapter.getDragInstance());
    }

    private void initBottomRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        DragListAdapter bottomListAdapter = new DragListAdapter(cardManager.getBottomCards(), this, cardManager, this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void correctDate() {
        counter += 1;
        Snackbar.make(linContainer, CORRECT_ANSWER, Snackbar.LENGTH_SHORT).show();
        setCardShirtImage();
        if (counter > 4) {
            new AlertDialog.Builder(this)
                    .setTitle(VICTORY)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(MainActivity.this, MainActivity.this.getClass()));
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public void errorDate(String text) {
        Snackbar.make(linContainer, text, Snackbar.LENGTH_SHORT).show();
    }

    private void setCardShirtImage() {
        ivDeck.setImageResource(getResources().getIdentifier("shirt_00" + counter, "drawable", getPackageName()));
    }

    @Override
    public void refreshAdapter(int position) {
        topListAdapter.notifyDataSetChanged();
        rvTop.smoothScrollToPosition(position);
    }

    @Override
    public int visibleCount() {
        return rvTop.getLayoutManager().getChildCount();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
        Log.i(TAG, "onStartDrag: ");
    }
}
