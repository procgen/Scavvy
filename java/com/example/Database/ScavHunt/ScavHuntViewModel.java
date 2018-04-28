package com.example.Database.ScavHunt;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ScavHuntViewModel extends AndroidViewModel {
    private ScavHuntRepository shRepository;
    private LiveData<List<ScavHunt>> allScavHunts;

    public ScavHuntViewModel (Application application) {
        super(application);
        shRepository = new ScavHuntRepository(application);
        allScavHunts = shRepository.getAllHunts();
    }

    public LiveData<List<ScavHunt>> getAllScavHunts() { return allScavHunts; }

    public void insert(ScavHunt scavHunt) { shRepository.insert(scavHunt); }
}
