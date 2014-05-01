package org.mbmg;

import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rpomeroy on 4/26/14.
 */
@org.springframework.stereotype.Repository
public class SimpleDBRepository implements DashboardRepository {

    @Autowired
    private DashboardRepository repository;
    /**
     * Convert a record to a ReplaceableItem
     *
     * @param line
     * @return
     */
    private static ReplaceableItem toItem(String line) {
        /**
         * Extract record metadata
         * 2:#STD:123456,511
         */
        String[] columns = line.split(";");
        String[] headers = columns[0].split(":");
        /**
         * [0]      2
         * [1]      #STD
         * [2]      123456
         * [3]      511
         */
        String stationID = headers[2].split(",")[0];
        /**
         * We use copyOfRange to truncate the last column from all the data columns until we know
         * we need it (it's not splittable with ":" so it's not a labeled value)
         */
        return new ReplaceableItem(stationID).
                withAttributes(
                        new ReplaceableAttribute().withName("RecordNumber").withValue(headers[0]),
                        new ReplaceableAttribute().withName("HeaderType").withValue(headers[1])
                ).
                withAttributes(dataAttributes(Arrays.copyOfRange(columns, 1, columns.length - 1)));
    }

    /**
     * Helper method to create a Collection of ReplaceableAttribute for the data elements
     *
     * @param labeledValues each looks like "LABEL:VALUE"
     * @return
     */
    private static Collection<ReplaceableAttribute> dataAttributes(String[] labeledValues) {
        return Stream.of(labeledValues).
                map(s -> new ReplaceableAttribute().withName(s.split(":")[0]).withValue(s.split(":")[1])).
                collect(Collectors.toList());
    }

    /**
     * Load the data from the file, transform into SimpleDB objects and save
     *
     * @throws Exception
     */
//    public List<ReplaceableItem> parse(Path aPath) throws Exception {
//        return Files.lines(aPath).
//                filter(s -> !s.isEmpty()).
//                map(this::toItem).
//                collect(Collectors.toList());
//    }
    @Override
    public Iterable<Record> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Record> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Record> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Record> Iterable<S> save(Iterable<S> entities) {
        /**
         * Transform the Iterable<Records> into SimpleDB API objects (ReplacableItem
         * with ReplaceableAttributes) and do a batch save to SimpleDB
         */
        return null;
    }

    @Override
    public Record findOne(String s) {
        return null;
    }

    @Override
    public boolean exists(String s) {
        return false;
    }

    @Override
    public Iterable<Record> findAll() {
        return null;
    }

    @Override
    public Iterable<Record> findAll(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(Record entity) {

    }

    @Override
    public void delete(Iterable<? extends Record> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
