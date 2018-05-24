package com.android.qprashna.ui.feeds;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.qprashna.R;
import com.android.qprashna.api.FeedsResponse;
import com.firebase.jobdispatcher.JobService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.android.qprashna.api.ApiUtils.getApiService;
import static com.android.qprashna.ui.common.ViewUtils.getJSessionIdInSharedPreferences;
import static com.android.qprashna.ui.common.ViewUtils.getUserIdFromSharedPreferences;

public class QprashnaJobService extends JobService {

    private static final String TAG = "QprashnaJobService";
    private static final String UNANSWERED_QUESTIONS_REMINDER_NOTIFICATION_CHANNEL = "unanswered_questions";
    private static final int UNANSWERED_QUESTIONS_REMINDER_NOTIFICATION_ID = 1217;
    private DisposableObserver<FeedsResponse> mDisposable;
    private static int questionsCount;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {

        Observable<FeedsResponse> responseObservable = getApiService().getUnAnsweredByMe(getJSessionIdInSharedPreferences(getBaseContext()), getUserIdFromSharedPreferences(getBaseContext()));

        mDisposable = responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FeedsResponse>() {
                    @Override
                    public void onNext(FeedsResponse feedsResponse) {
                        Log.d(QprashnaJobService.TAG, "getting unanswered questions response in QprashnaJobService");
                        if (feedsResponse != null && feedsResponse.getItems() != null && feedsResponse.getItems().size() > 0) {
                            Log.d(QprashnaJobService.TAG, "Previously asked questions count"+questionsCount);
                            Log.d(QprashnaJobService.TAG, "Current existing questions count"+feedsResponse.getItems().size());
                            if (questionsCount < feedsResponse.getItems().size()) {
                                questionsCount = feedsResponse.getItems().size();
                                NotificationManager
                                        notificationManager = (NotificationManager)
                                        getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                if (notificationManager != null) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel mChannel = new NotificationChannel(
                                                UNANSWERED_QUESTIONS_REMINDER_NOTIFICATION_CHANNEL,
                                                getBaseContext().getString(R.string.remind_unanswered_questions_channel_name),
                                                NotificationManager.IMPORTANCE_HIGH);
                                        notificationManager.createNotificationChannel(mChannel);
                                    }

                                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext(), UNANSWERED_QUESTIONS_REMINDER_NOTIFICATION_CHANNEL)
                                            .setColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                                            .setSmallIcon(R.drawable.ic_help_black_24dp)
                                            .setContentTitle(getBaseContext().getString(R.string.unanswered_question_reminder_notification_title))
                                            .setContentText(getBaseContext().getString(R.string.unanswered_question_reminder_notification_body))
                                            .setStyle(new NotificationCompat.BigTextStyle().bigText(
                                                    getBaseContext().getString(R.string.unanswered_question_reminder_notification_body)))
                                            .setDefaults(Notification.DEFAULT_VIBRATE)
                                            .setContentIntent(contentIntent(getBaseContext()))
                                            .setAutoCancel(true);

                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                                        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                    }

                                    notificationManager.notify(UNANSWERED_QUESTIONS_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
                                }
                            }
                        }
                        jobFinished(job, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        jobFinished(job, true);
                    }

                    @Override
                    public void onComplete() {
                        jobFinished(job, true);
                    }
                });
        return true;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        startActivityIntent.putExtra(FeedsFragment.FEED_TYPE, FragmentTypes.QUESTIONS_UNANSWERED.toString());
        return PendingIntent.getActivity(
                context,
                UNANSWERED_QUESTIONS_REMINDER_NOTIFICATION_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        Log.d(TAG, "Job cancelled!");
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        return false;
    }
}
