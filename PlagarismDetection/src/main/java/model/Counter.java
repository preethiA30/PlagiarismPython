package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

public class Counter {

    /**
     * Converts the field values to string
     * @return Counter with id, date, count and result count
     */
    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", count=" + count +
                ", resultCount=" + resultCount +
                '}';
    }

    /**
     * ObjectId of
     */
    @JsonIgnore
    private ObjectId id;

    /**
     * Date of the result generated
     */
    private String date;

    /**
     * Count of the number of times the result generation is done
     */
    private int count;

    private int resultCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    /**
     * Default Constructor
     */
    public Counter() {

    }
}
