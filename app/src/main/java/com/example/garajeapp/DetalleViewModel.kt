package com.example.garajeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class DetalleUiState {
    object Loading : DetalleUiState()
    data class Success(val marcaInfo: MarcaInfo, val esOffline: Boolean) : DetalleUiState()
    data class Error(val mensaje: String) : DetalleUiState()
}

class DetalleViewModel(
    private val repository: WikipediaRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<DetalleUiState>()
    val uiState: LiveData<DetalleUiState> = _uiState

    fun cargarInfoMarca(marca: String) {
        _uiState.value = DetalleUiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val resultado = repository.obtenerInfoMarca(marca)
            if (resultado.marcaInfo != null) {
                _uiState.postValue(
                    DetalleUiState.Success(
                        marcaInfo = resultado.marcaInfo,
                        esOffline = resultado.esOffline
                    )
                )
            } else {
                _uiState.postValue(
                    DetalleUiState.Error(
                        resultado.mensajeError ?: "No se pudo obtener información de la marca"
                    )
                )
            }
        }
    }
}
