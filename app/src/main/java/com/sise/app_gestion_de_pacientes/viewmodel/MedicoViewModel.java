package com.sise.app_gestion_de_pacientes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.app_gestion_de_pacientes.entities.Medico;
import com.sise.app_gestion_de_pacientes.repositories.MedicoRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.shared.LiveDataResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MedicoViewModel extends ViewModel {

    private final MutableLiveData<LiveDataResponse<Boolean>> insertarMedicoLiveData = new MutableLiveData<>();
    private final MedicoRepository medicoRepository = new MedicoRepository();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<LiveDataResponse<Boolean>> getInsertarMedicoStatus() {
        return insertarMedicoLiveData;
    }

    public void insertarMedico(Medico medico) {
        executorService.execute(() -> {
            medicoRepository.insertarMedico(medico, new Callback<Medico>() {
                @Override
                public void onSuccess(Medico result) {
                    insertarMedicoLiveData.postValue(LiveDataResponse.success(true));
                }

                @Override
                public void onFailure() {
                    insertarMedicoLiveData.postValue(LiveDataResponse.error());
                }
            });
        });
    }
}
