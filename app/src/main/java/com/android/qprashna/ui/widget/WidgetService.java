package com.android.qprashna.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.qprashna.R;
import com.android.qprashna.api.FeedsResponse;
import com.android.qprashna.api.Item;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;
public class WidgetService extends RemoteViewsService {
    private DisposableObserver<FeedsResponse> mDisposable;
    private int mAppWidgetId;
    private FeedsResponse mQuestions;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            @Override
            public void onCreate() {
                mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                getRecentQuestion();
            }

            @Override
            public void onDataSetChanged() {
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.qprashna_app_widget_question_list);

                if (mQuestions != null && mQuestions.getItems().size() > 0) {

                    Item question = mQuestions.getItems().get(position);

                    String askedBy = String.format(getBaseContext().getResources().getString(R.string.asked_by_text), question.getFeedQuestionAskedByName(), question.getFeedQuestionAskedTOName());
                    String upVoteCount = String.format(getBaseContext().getResources().getString(R.string.up_votes), String.valueOf(question.getQuestionUpvoteCount()));

                    views.setTextViewText(R.id.appwidget_asked_by_text, askedBy);
                    views.setTextViewText(R.id.appwidget_question_text, question.getFeedQuestionText());
                    views.setTextViewText(R.id.appwidget_upvotes_text, upVoteCount);
                }

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    private void getRecentQuestion() {

        if (getUserIdFromSharedPreferences(getBaseContext()) <= 0) {
            return;
        }

        Observable<FeedsResponse> responseObservable = getApiService().getQuestionsAskedByMe(getJSessionIdInSharedPreferences(getBaseContext()), getUserIdFromSharedPreferences(getBaseContext()));
        mDisposable = responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FeedsResponse>() {
                    @Override
                    public void onNext(FeedsResponse feedsResponse) {
                        if (feedsResponse != null && feedsResponse.getItems().size() > 0) {
                            mQuestions = feedsResponse;

                            final ComponentName cn = new ComponentName(getBaseContext(),
                                    QprashnaAppWidgetProvider.class);
                            AppWidgetManager.getInstance(getBaseContext()).notifyAppWidgetViewDataChanged(
                                    AppWidgetManager.getInstance(getBaseContext()).getAppWidgetIds(cn),
                                    R.id.widget_list_question);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // show error message if api call throws an error
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
