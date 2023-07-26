package com.mohamed.ai_app.ui.textclassifier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.mediapipe.tasks.text.textclassifier.TextClassifierResult
import com.mohamed.ai_app.R
import com.mohamed.ai_app.databinding.FragmentTextClassifierBinding

class TextClassifierFragment : Fragment() {

    private var _textClassifierBinding: FragmentTextClassifierBinding? = null
    private val textClassifierBinding get() = _textClassifierBinding!!
    private lateinit var classifierHelper: TextClassifierHelper
    private val adapter by lazy {
        ResultsAdapter()
    }

    private val listener = object : TextClassifierHelper.TextResultsListener {
        override fun onResult(
            results: TextClassifierResult,
            inferenceTime: Long,
        ) {
//            runOnUiThread {
            textClassifierBinding.bottomSheetLayout.inferenceTimeVal.text =
                String.format("%d ms", inferenceTime)

            adapter.updateResult(
                results.classificationResult().classifications().first().categories()
                    .sortedByDescending {
                        it.score()
                    },
                classifierHelper.currentModel,
            )
//            }
        }

        override fun onError(error: String) {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _textClassifierBinding = FragmentTextClassifierBinding.inflate(inflater, container, false)

        // Create the classification helper that will do the heavy lifting
        classifierHelper = TextClassifierHelper(
            context = requireContext(),
            listener = listener,
        )

        // Classify the text in the TextEdit box (or the default if nothing is added)
        // on button click.
        textClassifierBinding.classifyBtn.setOnClickListener {
            if (textClassifierBinding.inputText.text.isNullOrEmpty()) {
                classifierHelper.classify(getString(R.string.default_edit_text))
            } else {
                classifierHelper.classify(textClassifierBinding.inputText.text.toString())
            }
        }

        textClassifierBinding.results.adapter = adapter
        initBottomSheetControls()
        return textClassifierBinding.root
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _textClassifierBinding = textClassifierBinding.inflate(layoutInflater)
//        setContentView(textClassifierBinding.root)
//
//        // Create the classification helper that will do the heavy lifting
//        classifierHelper = TextClassifierHelper(
//            context = this@TextClassifierFragment,
//            listener = listener
//        )
//
//        // Classify the text in the TextEdit box (or the default if nothing is added)
//        // on button click.
//        textClassifierBinding.classifyBtn.setOnClickListener {
//            if (textClassifierBinding.inputText.text.isNullOrEmpty()) {
//                classifierHelper.classify(getString(R.string.default_edit_text))
//            } else {
//                classifierHelper.classify(textClassifierBinding.inputText.text.toString())
//            }
//        }
//
//        textClassifierBinding.results.adapter = adapter
//        initBottomSheetControls()
//    }

    private fun initBottomSheetControls() {
        val behavior =
            BottomSheetBehavior.from(textClassifierBinding.bottomSheetLayout.bottomSheetLayout)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        // Allows the user to switch between the classification models that are available.
        textClassifierBinding.bottomSheetLayout.modelSelector.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.wordvec -> {
                    classifierHelper.currentModel = TextClassifierHelper.WORD_VEC
                    classifierHelper.initClassifier()
                }

                R.id.mobilebert -> {
                    classifierHelper.currentModel = TextClassifierHelper.MOBILEBERT
                    classifierHelper.initClassifier()
                }
            }
        }
    }

//    override fun onBackPressed() {
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
//            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
//            // (https://issuetracker.google.com/issues/139738913)
//            finishAfterTransition()
//        } else {
//            super.onBackPressed()
//        }
//    }
}
