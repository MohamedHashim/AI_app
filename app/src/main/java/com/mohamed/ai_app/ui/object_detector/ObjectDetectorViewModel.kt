package com.mohamed.ai_app.ui.object_detector

import androidx.lifecycle.ViewModel
import com.mohamed.ai_app.Constants.DELEGATE_CPU
import com.mohamed.ai_app.Constants.MAX_RESULTS_DEFAULT
import com.mohamed.ai_app.Constants.MODEL_EFFICIENTDETV0
import com.mohamed.ai_app.Constants.THRESHOLD_DEFAULT

/**
 *  This ViewModel is used to store object detector helper settings
 */
class ObjectDetectorViewModel : ViewModel() {
    private var _delegate: Int = DELEGATE_CPU
    private var _threshold: Float = THRESHOLD_DEFAULT
    private var _maxResults: Int = MAX_RESULTS_DEFAULT
    private var _model: Int = MODEL_EFFICIENTDETV0

    val currentDelegate: Int get() = _delegate
    val currentThreshold: Float get() = _threshold
    val currentMaxResults: Int get() = _maxResults
    val currentModel: Int get() = _model

    fun setDelegate(delegate: Int) {
        _delegate = delegate
    }

    fun setThreshold(threshold: Float) {
        _threshold = threshold
    }

    fun setMaxResults(maxResults: Int) {
        _maxResults = maxResults
    }

    fun setModel(model: Int) {
        _model = model
    }
}
