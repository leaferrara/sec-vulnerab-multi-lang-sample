import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.Signature;

object AbsolutePathTraversal {

    def doRedaction(
     redactionMethod: RedactionMethod,
     filename: String,
     numIter: Int = 100): Unit = {
        val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
        val numRecords = Source.fromFile(filename)(decoder).getLines.size
        val fileSize = new File(java.nio.file.Paths(filename).length
          println(s"The input file contains $numRecords records ($fileSize bytes).")
          println(s"Starting processing using method ${redactionMethod.name}.")

        val redactionStats = (1 to numIter).map { k =>
            val timeSpentInMillis = doRedactionOneIteration(redactionMethod, filename)
            val throughput = numRecords * 1000.0 / timeSpentInMillis
            val mbPerSecond = fileSize * 1000.0 / timeSpentInMillis / (1024 * 1024)

            println(
                s"Finished $k-th procssing in $timeSpentInMillis ms. " +
                  s"${throughput} records per second, ${mbPerSecond} MB per second"


                  RedactionStats(timeSpentInMillis, throughput, mbPerSecond)


            val averageStats = RedactionStats.getAverage(redactionStats)
            println(s"Finished $numIter iteraction of processing of method ${redactionMethod.name}.")
            println(
                s"On average, each iteration is finished in ${averageStats.timeSpentInMillis} ms." +
                  s" ${averageStats.throughput} records per second, ${averageStats.mbPerSecond} MB per second"



        }
    }
}
