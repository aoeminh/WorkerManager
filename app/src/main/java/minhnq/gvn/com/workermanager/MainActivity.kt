package minhnq.gvn.com.workermanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var workerManager: WorkManager? = null
    var workRequest: OneTimeWorkRequest? = null
    var periodRequest: PeriodicWorkRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workerManager = WorkManager.getInstance()
        btn_dowork.setOnClickListener(this)
        btn_dowork_constrain.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        btn_cancel_all.setOnClickListener(this)
        val source = Data.Builder()
            .putString("data","test")
            .build()
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_dowork -> doWork()
            btn_dowork_constrain -> doWorkConstrain()
            btn_cancel -> cancel()
            btn_cancel_all -> cancelAll()
        }
    }

    private fun cancelAll() {
        workerManager?.cancelAllWork()
    }

    fun doWork() {

        val source = Data.Builder()
            .putString("data","test")
            .build()
        periodRequest = PeriodicWorkRequest.Builder(DoWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(source)
            .build()
        val workRequestId = periodRequest?.id
        Log.d("UUID", "$workRequestId")

        workerManager?.enqueue(periodRequest!!)

    }

    fun doWorkConstrain() {
        val constrain = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        workRequest = OneTimeWorkRequest.Builder(DoWorker::class.java)
            .setConstraints(constrain)
            .build()
        val workRequestId = workRequest?.id
        Log.d("UUID", "$workRequestId")
        workerManager?.enqueue(workRequest!!)
        workerManager?.enqueue(workRequest!!)
    }
    
    fun cancel() {
        val workRequestId = workRequest?.id
        val periodRequestId = periodRequest?.id
        workRequestId?.let {
            workerManager?.cancelWorkById(it)
            Log.d("UUID", "$it")
        }

        periodRequestId?.let {
            workerManager?.cancelWorkById(it)
            Log.d("UUID", "$it")
        }
    }
}
