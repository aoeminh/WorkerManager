package minhnq.gvn.com.workermanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DoWorker(val context: Context, val workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        val a = 1
        val b = 2
        val c = a + b
        val data  = inputData.getString("data")
        Log.d("worker",data)

        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("worker","onStop")

    }
}