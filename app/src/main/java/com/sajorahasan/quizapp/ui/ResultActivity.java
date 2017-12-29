package com.sajorahasan.quizapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.sajorahasan.quizapp.R;
import com.sajorahasan.quizapp.adapter.QuizAdapter;
import com.sajorahasan.quizapp.model.QuizEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";

    @BindView(R.id.resultPieChart)
    PieChart pieChart;
    @BindView(R.id.quizRecyclerView)
    RecyclerView recyclerView;
    private QuizAdapter adapter;
    private int correctAns, incorrectAns, totalQueNo;
    private List<QuizEvent> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        //Initializing Views
        initViews();

        updateUI();
    }

    private void initViews() {
        quizList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            quizList = bundle.getParcelableArrayList("quizData");
            correctAns = bundle.getInt("correctAns");
            incorrectAns = bundle.getInt("incorrectAns");
        }

        // Clearing Pie chart legend description text
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight h) {
                Description description = new Description();
                description.setText(((int) entry.getY()) + " Answers");
                pieChart.setDescription(description);
            }

            @Override
            public void onNothingSelected() {
                Description description = new Description();
                description.setText("");
                pieChart.setDescription(description);
            }
        });

        // SettingUp RecyclerView
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateUI() {
        if (quizList != null && quizList.size() != 0) {
            totalQueNo = quizList.size();

            initPie();

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(correctAns, "Correct Answer"));
            entries.add(new PieEntry(incorrectAns, "Incorrect Answer"));

            //PieDataSet set = new PieDataSet(entries, "Total Tiffins : " + tiffinList.size());
            pieChart.setCenterText(quizList.size() + " Questions");

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(3f);

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(getResources().getColor(android.R.color.holo_green_light));
            colors.add(getResources().getColor(android.R.color.holo_red_light));
            dataSet.setColors(colors);

            PieData pieData = new PieData(dataSet);
            pieData.setValueTextSize(11f);
            pieData.setValueTextColor(Color.WHITE);
            pieChart.setData(pieData);
            pieChart.highlightValues(null);
            pieChart.invalidate(); // refresh

            // SettingUp recyclerView
            adapter = new QuizAdapter(quizList);
            recyclerView.setAdapter(adapter);

        } else {
            pieChart.clear();
            pieChart.invalidate();
        }
    }

    private void initPie() {
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }
}
