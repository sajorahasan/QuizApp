package com.sajorahasan.quizapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sajorahasan.quizapp.R;
import com.sajorahasan.quizapp.model.QuizEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sajora on 27-12-2017.
 */

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder> {

    private List<QuizEvent> quizList;
    private static OnItemClickListener mListener;

    // Define the mListener interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public QuizAdapter(List<QuizEvent> quizList) {
        this.quizList = quizList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        QuizEvent quiz = quizList.get(position);

        holder.tvQueNo.setText(String.format("Question %s", String.valueOf(quiz.getQuestionNumber())));
        holder.tvQuizQuestion.setText(quiz.getQuestion());
        holder.tvCorrectAns.setText(String.valueOf(quiz.getCorrectAnswer()));

        if (quiz.getIncorrectAns() != null) {
            holder.tvIncorrectAns.setVisibility(View.VISIBLE);
            holder.tvIncorrectAns.setText(String.valueOf(quiz.getIncorrectAns()));
        }
    }

    public QuizEvent getItem(int position) {
        return quizList.get(position);
    }

    @Override
    public int getItemCount() {
        return quizList == null ? 0 : quizList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tvQueNo)
        TextView tvQueNo;
        @BindView(R.id.item_quizQuestion)
        TextView tvQuizQuestion;
        @BindView(R.id.item_correctAns)
        TextView tvCorrectAns;
        @BindView(R.id.item_incorrectAns)
        TextView tvIncorrectAns;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (mListener != null) mListener.onItemClick(v, getAdapterPosition());
            });
        }
    }
}
