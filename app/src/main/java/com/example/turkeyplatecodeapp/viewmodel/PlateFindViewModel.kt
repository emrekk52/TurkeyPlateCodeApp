package com.example.cryptolistapp.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.turkeyplatecodeapp.CorrectPlay
import com.example.turkeyplatecodeapp.GameCompleted
import com.example.turkeyplatecodeapp.WrongPlay
import com.example.turkeyplatecodeapp.constants.Constants.oyunType
import com.example.turkeyplatecodeapp.dao.DaoRepository
import com.example.turkeyplatecodeapp.dao.DatabaseCreator
import com.example.turkeyplatecodeapp.dao.DatabaseDao
import com.example.turkeyplatecodeapp.model.Data
import com.example.turkeyplatecodeapp.model.QuestionModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import okhttp3.internal.wait
import timber.log.Timber


class PlateFindViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var questionData = mutableStateOf<List<Data>>(listOf())
    var questionList = mutableStateOf<List<QuestionModel>>(listOf())

    val PASS_QUESTION_TIME = 1500L

    var nowQuestion = mutableStateOf(QuestionModel())

    var questionIndex = mutableStateOf(0)
    var questionSize = mutableStateOf(10)

    var trueAnswerCount = 0
    var falseAnswerCount = 0

    val gameOver = MutableLiveData(false)
    val closeDialog = mutableStateOf(false)

    //her cevaptan sonra butonları pasif yapar
    var close = MutableLiveData(false)

    var db: FirebaseFirestore

    //cevap doğru ise konfeti patlatır
    var isTrue = MutableLiveData(false)

    private val daoRepository: DaoRepository
    private val dao: DatabaseDao

    init {
        dao = DatabaseCreator.getInstance(application).dao()
        daoRepository = DaoRepository(dao)
        questionData.value = daoRepository.getQuestionLimit10()
        questionSize.value = questionData.value.size
        questionIndex.value = questionData.value.size - 1
        convertToQuestion()
        db = FirebaseFirestore.getInstance()
    }


    fun retryLoadGame() {
        questionData.value = daoRepository.getQuestionLimit10()
        questionSize.value = questionData.value.size
        questionIndex.value = questionData.value.size - 1
        convertToQuestion()
    }


    fun convertToQuestion() {

        viewModelScope.launch {

            gameOver.value = false
            closeDialog.value = false
            trueAnswerCount = 0
            falseAnswerCount = 0

            val _list = arrayListOf<QuestionModel>()



            when (oyunType) {
                0, 3 ->
                    questionData.value.forEach {
                        val questionModel = QuestionModel()
                        questionModel.answer = it.il_adi
                        questionModel.plate = it.plaka_kodu
                        questionModel.id = it.id
                        questionModel.cityMapUrl = it.cityMapUrl
                        val answerList = daoRepository.getAnswerList(it.il_adi)
                        answerList.add(it.il_adi)
                        answerList.shuffle()
                        questionModel.answerList = answerList
                        _list.add(questionModel)
                    }

                2 -> {
                    questionData.value.forEach {
                        val questionModel = QuestionModel()
                        questionModel.answer = it.plaka_kodu
                        questionModel.plate = it.il_adi
                        questionModel.id = it.id
                        val answerList = arrayListOf<String>()
                        val cityList = daoRepository.getAnswerList(it.il_adi)
                        cityList.forEach { city ->
                            daoRepository.getIlceler(city).apply {

                                var ilce = ""
                                do {
                                    ilce =
                                        this.ilceler[(0 until this.ilceler.size).random()].ilce_adi
                                } while (ilce.trim().lowercase().equals("merkez"))
                                answerList.add(ilce)
                            }
                        }
                        daoRepository.getIlceler(it.il_adi).apply {
                            var dogruIlce = ""
                            do {
                                dogruIlce =
                                    this.ilceler[(0 until this.ilceler.size).random()].ilce_adi
                            } while (dogruIlce.trim().lowercase().equals("merkez"))
                            answerList.add(dogruIlce)
                        }
                        answerList.shuffle()
                        questionModel.answerList = answerList
                        _list.add(questionModel)
                    }


                }

                else ->
                    questionData.value.forEach {
                        val questionModel = QuestionModel()
                        questionModel.answer = it.plaka_kodu
                        questionModel.plate = it.il_adi
                        questionModel.id = it.id
                        val answerList = daoRepository.getPlateList(it.plaka_kodu)
                        answerList.add(it.plaka_kodu)
                        answerList.shuffle()
                        questionModel.answerList = answerList
                        _list.add(questionModel)
                    }
            }


            questionList.value = _list

            nowQuestion.value = _list[_list.size - 1]

        }


    }

    fun passQuestion(context: Context) {


        viewModelScope.launch {

            close.value = true

            val newQuestion = QuestionModel()

            newQuestion.answer = questionList.value[questionIndex.value].answer
            newQuestion.answerList = questionList.value[questionIndex.value].answerList
            newQuestion.plate = questionList.value[questionIndex.value].plate
            newQuestion.id = questionList.value[questionIndex.value].id
            newQuestion.cityMapUrl = questionList.value[questionIndex.value].cityMapUrl

            nowQuestion.value = newQuestion


            //her sorudan sonra 1.5 saniye bekleme süresi sağlar
            delay(PASS_QUESTION_TIME)

            if (questionIndex.value > 0) {
                --questionIndex.value
                nowQuestion.value = questionList.value[questionIndex.value]

            }

            // soru geçildikten sonra butonları aktif eder ve konfetiyi kapatır
            close.value = false
            isTrue.value = false


            if (falseAnswerCount + trueAnswerCount == questionSize.value)
                gameOver(context)


        }


    }

    fun isAnswerTrue(reqAnswer: String, context: Context): Boolean {


        var ft = false


        if (oyunType != 2)
            ft = questionList.value[questionIndex.value].answer.equals(reqAnswer)
        else {
            val t =
                daoRepository.getByIdToCity(questionList.value[questionIndex.value].id.toString()).ilceler.filter {
                    it.ilce_adi.equals(reqAnswer)
                }

            if (t.size > 0)
                ft = if (t.size > 0) true else false
        }

        isTrue.value = ft

        if (ft) {
            ++trueAnswerCount
            CorrectPlay(context = context)
        } else {
            ++falseAnswerCount
            WrongPlay(context = context)
        }


        return ft
    }


    private fun gameOver(context: Context) {
        Timber.i("Oyun bitti")
        GameCompleted(context)
        gameOver.value = true

    }


    fun submitQuestion() {

            db.collection("questions")
                .add(nowQuestion.value)
                .isSuccessful

    }


}

