package com.example.realweather.repository.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;


/**
 * This class is used to setup and run a periodic job every
 * 3 hours to update the data from the server. It uses the Firebase
 * Job dispatcher class
 */
public class WeatherDataJobService extends JobService {


    @Override
    public boolean onStartJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
//        if(AppUtils.getCurrentPosition(getBaseContext())[0].equals(NO_LATITUDE)) {
//            String[] selectedPlace = AppUtils.getSelectedPosition(getBaseContext());
//            RemoteRepository.getsInstance().getForecastLatLon(selectedPlace[1],selectedPlace[2],getBaseContext()); }
//        else {
//            String[] currentPlace = AppUtils.getCurrentPosition(getBaseContext());
//            RemoteRepository.getsInstance().getForecastLatLon(currentPlace[0],currentPlace[1],getBaseContext());
//        }
        return false;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }

    public static void setUpJob(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = dispatcher.newJobBuilder()
                .setService(WeatherDataJobService.class)
                .setTag("my-update-weather-data")
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(60*60*3, (60*60*3)+30))
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        dispatcher.mustSchedule(myJob);
    }
}
