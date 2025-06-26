package com.sise.app_gestion_de_pacientes.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.app_gestion_de_pacientes.entities.Paciente;
import com.sise.app_gestion_de_pacientes.repositories.PacienteRepository;
import com.sise.app_gestion_de_pacientes.shared.Callback;
import com.sise.app_gestion_de_pacientes.shared.LiveDataResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class PacienteViewModel extends ViewModel {

    private final MutableLiveData<LiveDataResponse<Boolean>> insertarPacienteLiveData = new MutableLiveData<>();
    private final PacienteRepository pacienteRepository = new PacienteRepository();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LiveData<LiveDataResponse<Boolean>> getInsertarPacienteStatus() {
        return insertarPacienteLiveData;
    }

    public void insertarPaciente(Paciente paciente) {
        executorService.execute(() -> {
            pacienteRepository.insertarPaciente(paciente, new Callback<Paciente>() {
                @Override
                public void onSuccess(Paciente result) {
                    insertarPacienteLiveData.postValue(LiveDataResponse.success(true));
                }
                @Override
                public void onFailure() {
                    insertarPacienteLiveData.postValue(LiveDataResponse.error());
                }
            });
        });
    }
}
