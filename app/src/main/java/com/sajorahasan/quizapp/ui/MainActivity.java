package com.sajorahasan.quizapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.sajorahasan.quizapp.QuizApp;
import com.sajorahasan.quizapp.R;
import com.sajorahasan.quizapp.api.API;
import com.sajorahasan.quizapp.api.RestAdapter;
import com.sajorahasan.quizapp.api.callback.CallbackResults;
import com.sajorahasan.quizapp.api.callback.Result;
import com.sajorahasan.quizapp.model.QuizEvent;
import com.sajorahasan.quizapp.utils.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.progressLayout)
    RelativeLayout progressLayout;
    @BindView(R.id.queNoEditText)
    EditText etQueNo;
    @BindView(R.id.categorySpinner)
    Spinner catSpinner;
    @BindView(R.id.difficultySpinner)
    Spinner diffSpinner;
    @BindView(R.id.typeSpinner)
    Spinner typeSpinner;
    @BindView(R.id.btnStartQuiz)
    Button btnStartQuiz;
    @BindArray(R.array.category_array)
    String[] categories;

    private int queNumber;
    private int catValue;
    private String typeValue;
    private String category, difficulty, type;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Initializing Views
        initViews();

    }

    private void initViews() {
        //Setting up category spinner
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);

        //Setting up difficulty spinner
        ArrayAdapter<CharSequence> diffAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diffSpinner.setAdapter(diffAdapter);

        //Setting up difficulty spinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        // Ripple Effect
        Tools.rippleWhite(btnStartQuiz);

        disposable = new CompositeDisposable();

        btnStartQuiz.setOnClickListener(this);
        catSpinner.setOnItemSelectedListener(this);
        diffSpinner.setOnItemSelectedListener(this);
        typeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        switch (parent.getId()) {
            case R.id.categorySpinner:
                category = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.difficultySpinner:
                difficulty = parent.getItemAtPosition(pos).toString().toLowerCase();
                break;
            case R.id.typeSpinner:
                type = parent.getItemAtPosition(pos).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(etQueNo.getText().toString())) {
            queNumber = Integer.parseInt(etQueNo.getText().toString().trim());
        } else {
            Tools.alertError(MainActivity.this, "Question number should not be empty");
        }

        if (queNumber != 0 && !category.equalsIgnoreCase(getString(R.string.AnyCategory))
                && !difficulty.equalsIgnoreCase(getString(R.string.AnyDifficulty))
                && !type.equalsIgnoreCase(getString(R.string.AnyType))) {
            if (type.equalsIgnoreCase("True / False")) {
                typeValue = "boolean";
            } else {
                typeValue = "multiple";
            }
            catValue = Tools.getCategoryValue(category);
            getDataByCatDiffAndType();
        } else if (queNumber != 0 && !category.equalsIgnoreCase(getString(R.string.AnyCategory))
                && !difficulty.equalsIgnoreCase(getString(R.string.AnyDifficulty))) {
            catValue = Tools.getCategoryValue(category);
            getDataByCatAndDiff();
        } else if (queNumber != 0 && !category.equalsIgnoreCase(getString(R.string.AnyCategory))
                && !type.equalsIgnoreCase(getString(R.string.AnyType))) {
            if (type.equalsIgnoreCase("True / False")) {
                typeValue = "boolean";
            } else {
                typeValue = "multiple";
            }
            catValue = Tools.getCategoryValue(category);
            getDataByCatAndType();
        } else if (queNumber != 0 && !difficulty.equalsIgnoreCase(getString(R.string.AnyDifficulty))
                && !type.equalsIgnoreCase(getString(R.string.AnyType))) {
            if (type.equalsIgnoreCase("True / False")) {
                typeValue = "boolean";
            } else {
                typeValue = "multiple";
            }
            getDataByDiffAndType();
        } else if (queNumber != 0 && !category.equalsIgnoreCase(getString(R.string.AnyCategory))) {
            catValue = Tools.getCategoryValue(category);
            getDataByCat();
        } else if (queNumber != 0 && !difficulty.equalsIgnoreCase(getString(R.string.AnyDifficulty))) {
            getDataByDiff();
        } else if (queNumber != 0 && !type.equalsIgnoreCase(getString(R.string.AnyType))) {
            if (type.equalsIgnoreCase("True / False")) {
                typeValue = "boolean";
            } else {
                typeValue = "multiple";
            }
            getDataByType();
        } else {
            if (queNumber != 0 && category != null && difficulty != null && type != null) {
                getData();
            }
        }

    }

    private void getData() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByQueNo(queNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByCat() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByCategory(queNumber, catValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByDiff() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByDifficulty(queNumber, difficulty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByType() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByType(queNumber, typeValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByCatAndDiff() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByCatAndDiff(queNumber, catValue, difficulty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByCatAndType() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByCatAndType(queNumber, catValue, typeValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByDiffAndType() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByDiffAndType(queNumber, difficulty, typeValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void getDataByCatDiffAndType() {
        progressLayout.setVisibility(View.VISIBLE);
        API api = RestAdapter.createAPI();
        disposable.add(api.getQuizByCatDiffAndType(queNumber, catValue, difficulty, typeValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(CallbackResults data) {
        progressLayout.setVisibility(View.GONE);
        if (data != null && data.getResponseCode() == 0) {
            ArrayList<QuizEvent> quizEventList = new ArrayList<>();
            for (int j = 0; j < data.getResults().size(); j++) {
                Result result = data.getResults().get(j);
                QuizEvent event = new QuizEvent();

                List<String> ansList = new ArrayList<>();
                ansList.add(result.getCorrectAnswer());
                ansList.addAll(result.getIncorrectAnswers());
                Collections.shuffle(ansList, new Random());

                event.setQuestionNumber(j + 1);
                event.setQuestion(result.getQuestion());
                event.setCorrectAnswer(result.getCorrectAnswer());
                event.setAnswer(ansList);
                quizEventList.add(event);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("quizData", quizEventList);
            Intent i = new Intent(MainActivity.this, QuizActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        } else if (data != null && data.getResponseCode() == 1) {
            Tools.alert(MainActivity.this, "No Quiz Found", "Please try different option");
        } else {
            onFailRequest();
        }
    }

    private void handleError(Throwable t) {
        try {
            progressLayout.setVisibility(View.GONE);
            Tools.alert(MainActivity.this, "Error", t.getLocalizedMessage());
            onFailRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFailRequest() {
        if (!QuizApp.hasNetwork()) {
            Tools.alert(MainActivity.this, "No Internet", "Can't retrieve data at a moment");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.clear();
        }
    }

}
