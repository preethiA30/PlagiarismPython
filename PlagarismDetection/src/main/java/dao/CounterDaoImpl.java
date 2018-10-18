package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Counter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Preethi Anbunathan
 * @project PlagarismDetection
 * @date 4/2/18
 * @email anbunathan.p@husky.neu.edu
 */
public class CounterDaoImpl implements CounterDao {


    /**
     * Connection to database
     */
    private MongoDatabase mongoDatabase;

    /**
     * Constructor to initialize database connection
     *
     * @param mongoDao:database connection
     */
    public CounterDaoImpl(MongoDao mongoDao) {
        this.mongoDatabase = mongoDao.getDBConnection();
    }


    /**
     * Adds the count of reports generated with the date of generation
     *
     * @param counter:         Number of times the Plagiarism tests have been done
     * @param numberOfResults: Number of plagiarism results generated
     */
    @Override
    public void counterUpdate(int counter, int numberOfResults) {
        try {
            LocalDate ld = LocalDate.now();
            MongoCollection<Document> collection = mongoDatabase.getCollection("Stats");
            MongoCollection<Counter> counterMongoCollection = mongoDatabase.getCollection("Stats", Counter.class);
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            Counter counterPresent = counterMongoCollection.find(eq("date", timeStamp)).first();

            if (counterPresent!=null&&counterPresent.getResultCount() != 0)
                numberOfResults = counterPresent.getResultCount() + numberOfResults;

            Document stats = new Document();
            stats.put("count", counter);
            stats.put("resultCount", numberOfResults);
            stats.put("date", DateTimeFormatter.ofPattern("yyy/MM/dd").format(ld));
            collection.deleteOne(eq("date", timeStamp));
            collection.insertOne(stats);
        }catch (Exception e){
            logger.error("Error in updating stats"+e);
        }
    }

    /**
     * Gets the current system statistics of the user.
     *
     * @return List of Counters having system results counts for past 10 days
     */
    @Override
    public List<Counter> getStatistics() {
        List<Counter> stats = new ArrayList<>();
        try {
            MongoCollection<Counter> collection = mongoDatabase.getCollection("Stats", Counter.class);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            DateTime dateTime1 = new DateTime(timeStamp);
            DateTime dateTime2 = dateTime1.minusDays(10);
            List<String> dates = getDateRange(dateTime2, dateTime1);

            for (String date : dates) {
                Counter counter = collection.find(eq("date", date)).first();
                if (counter != null) {
                    stats.add(counter);
                }
            }
        } catch (Exception e) {
            logger.error("Error " + e);
        }
        return stats;
    }

    /**
     * Gets the date range in format yyyy/MM/dd from start to end
     *
     * @param start: Start date
     * @param end:   End date
     * @return List of date ranges from start to end data in string format
     */
    static List<String> getDateRange(DateTime start, DateTime end) {

        List<String> ret = new ArrayList<String>();
        DateTime tmp = start;
        org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
        while (tmp.isBefore(end) || tmp.equals(end)) {
            String dateString = tmp.toString(fmt);
            ret.add(dateString);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }

    /**
     * Logger for the Counter Dao Impl
     */
    private static Logger logger = LogManager.getLogger();
}
