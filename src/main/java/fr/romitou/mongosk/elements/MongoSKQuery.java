package fr.romitou.mongosk.elements;

import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoSKQuery {

    private MongoSKCollection mongoSKCollection;
    private MongoSKFilter mongoSKFilter;
    private MongoSKSort mongoSKSort;
    private Boolean diskUsage;
    private String comment;
    private Integer limit;
    private Integer skip;

    public MongoSKQuery() {
    }

    public MongoSKCollection getMongoSKCollection() {
        return mongoSKCollection;
    }

    public void setMongoSKCollection(MongoSKCollection mongoSKCollection) {
        this.mongoSKCollection = mongoSKCollection;
    }

    public MongoSKFilter getMongoSKFilter() {
        return mongoSKFilter;
    }

    public void setMongoSKFilter(MongoSKFilter mongoSKFilter) {
        this.mongoSKFilter = mongoSKFilter;
    }

    public MongoSKSort getMongoSKSort() {
        return mongoSKSort;
    }

    public Boolean getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Boolean diskUsage) {
        this.diskUsage = diskUsage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public void setMongoSKSort(MongoSKSort mongoSKSort) {
        this.mongoSKSort = mongoSKSort;
    }

    public FindPublisher<Document> buildIterable() {
        MongoCollection<Document> mongoCollection = getMongoSKCollection().getMongoCollection();
        FindPublisher<Document> findPublisher;
        if (getMongoSKFilter() == null)
            findPublisher = mongoCollection.find();
        else
            findPublisher = mongoCollection.find(getMongoSKFilter().getFilter());
        if (getMongoSKSort() == null)
            return findPublisher;
        if (getLimit() != null)
            findPublisher = findPublisher.limit(getLimit());
        if (getSkip() != null)
            findPublisher = findPublisher.skip(getSkip());
        if (getDiskUsage() != null)
            findPublisher = findPublisher.allowDiskUse(getDiskUsage());
        if (getComment() != null)
            findPublisher = findPublisher.comment(getComment());
        return findPublisher.sort(getMongoSKSort().getSort());
    }

    public String getDisplay() {
        List<String> stringList = new ArrayList<>();
        stringList.add("mongo query");
        if (mongoSKCollection != null)
            stringList.add("of " + mongoSKCollection.getMongoCollection().getNamespace().getCollectionName() + " collection");
        if (mongoSKFilter != null)
            stringList.add("with " + mongoSKFilter.getDisplay());
        if (mongoSKSort != null)
            stringList.add("sorted by " + mongoSKSort.getDisplay());
        if (getComment() != null)
            stringList.add("with comment \"" + getComment() + "\"");
        if (getDiskUsage() != null && !getDiskUsage())
            stringList.add("without disk usage");
        if (limit != null)
            stringList.add("with limit of " + limit + " document(s)");
        if (skip != null)
            stringList.add("with skip of " + limit + " document(s)");
        return String.join(" ", stringList);
    }

    @Override
    public String toString() {
        return "MongoSKQuery{" +
            "mongoSKCollection=" + mongoSKCollection +
            ", mongoSKFilter=" + mongoSKFilter +
            ", mongoSKSort=" + mongoSKSort +
            '}';
    }
}
